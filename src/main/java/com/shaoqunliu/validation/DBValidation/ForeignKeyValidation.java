package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.validation.Contracts;
import com.shaoqunliu.validation.exception.ValidationException;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;
import com.shaoqunliu.validation.exception.ValidationInternalException;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Shaoqun Liu
 * @since 1.8
 */
public class ForeignKeyValidation extends AbstractDatabaseValidation {

    public ForeignKeyValidation(DataSource dataSource) throws ValidationInternalException {
        super(dataSource);
    }

    /**
     * @see com.shaoqunliu.validation.DBValidation.AbstractDatabaseValidation
     */
    @Override
    protected String validateWithDatabase(String table, String column, String value) throws ValidationException {
        if (value == null || value.equals("")) {
            /*
             * Null or empty string considered to be valid.
             */
            return "";
        }
        Contracts.assertNotEmpty(table, "invalid table name");
        Contracts.assertNotEmpty(column, "invalid column name");
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
        Contracts.assertNotNull(object, "Null object was given");
        StringBuilder result = new StringBuilder();
        for (Field field : object.getClass().getFields()) {
            for (DatabaseColumnReference columnReference :
                    field.getAnnotationsByType(DatabaseColumnReference.class)) {
                boolean needToCheck = false;
                if (groups.length == 0 && columnReference.groups().length == 0) {
                    needToCheck = true;
                } else {
                    for (Class clazz : groups) {
                        if (!clazz.isInterface()) {
                            throw new ValidationException("the class " + clazz.getName() + " is not an interface!");
                        }
                        for (Class test : columnReference.groups()) {
                            if (clazz.getName().equals(test.getName())) {
                                needToCheck = true;
                                break;
                            }
                        }
                        if (needToCheck) {
                            break;
                        }
                    }
                }
                if (needToCheck) {
                    try {
                        String value = field.get(object).toString();
                        String resultFromDB = validateWithDatabase(columnReference.table(), columnReference.column(), value);
                        if (isFailFast() && !resultFromDB.equals("")) {
                            throw new ValidationException(columnReference.message().length() == 0 ? resultFromDB : columnReference.message());
                        }
                        result.append(columnReference.message().length() == 0 ? resultFromDB : columnReference.message()).append("; ");
                    } catch (IllegalAccessException e) {
                        throw new ValidationException(e.getMessage());
                    }
                }
            }
        }
        if (result.length() != 0) {
            throw new ValidationException(result.toString());
        }
        return object;
    }
}
