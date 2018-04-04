package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.validation.ValidationException;
import com.shaoqunliu.validation.annotation.DBColumnReference;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shaoqun Liu
 */
public class ForeignKeyValidation extends AbstractDatabaseValidation {

    public ForeignKeyValidation(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * @see com.shaoqunliu.validation.DBValidation.AbstractDatabaseValidation
     */
    @Override
    protected String validateWithDatabase(String table, String column, String value) throws ValidationException {
        if (value == null || value.equals("")) {
            /*
             * Null or empty string consider to be valid.
             */
            return "";
        }
        StringBuilder sql = new StringBuilder(getBasicSql());
        sql.append(" FROM ").append(table)
                .append(" WHERE ").append(column).append("=?");
        try {
            PreparedStatement statement = getDataSource().getConnection().prepareStatement(sql.toString());
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.getInt(1) == 0) {
                return "Value '" + value + "' for column " + column + " is invalid!";
            }
        } catch (SQLException e) {
            throw new ValidationException(e.getMessage());
        }
        return "";
    }

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter
     */
    @Override
    public <T> T validate(T object, Class... groups) throws ValidationException {
        Field[] fields = object.getClass().getFields();
        StringBuilder result = new StringBuilder();
        for (Field field : fields) {
            DBColumnReference columnReference = field.getAnnotation(DBColumnReference.class);
            if (columnReference != null) {
                try {
                    String value = field.get(object).toString();
                    String resultFromDB = validateWithDatabase(columnReference.table(), columnReference.column(), value);
                    if (isFailFast() && !resultFromDB.equals("")) {
                        throw new ValidationException(resultFromDB);
                    }
                    result.append(resultFromDB);
                } catch (IllegalAccessException e) {
                    throw new ValidationException(e.getMessage());
                }
            }
        }
        if (result.length() != 0) {
            throw new ValidationException(result.toString());
        }
        return object;
    }
}
