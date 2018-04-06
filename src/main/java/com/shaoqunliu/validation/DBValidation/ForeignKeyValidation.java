package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.reflection.POJOReflection;
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
        if (value == null || value.length() == 0) {
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
            /*
             * empty result set considered to be invalid
             */
            if (!resultSet.next() || resultSet.getInt(1) == 0) {
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
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        Contracts.assertNotNull(object, "Null object was given");
        sanityCheckGroups(groups);
        StringBuilder result = new StringBuilder(128);
        for (Field field : object.getClass().getDeclaredFields()) {
            for (DatabaseColumnReference columnReference :
                    field.getAnnotationsByType(DatabaseColumnReference.class)) {
                boolean needToCheck = false;
                if (groups.length == 0 && columnReference.groups().length == 0) {
                    needToCheck = true;
                } else {
                    for (Class clazz : groups) {
                        for (Class test : columnReference.groups()) {
                            if (clazz.equals(test)) {
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
                        POJOReflection reflection = new POJOReflection(object);
                        String value = reflection.getValue(field.getName()).toString();
                        String resultFromDB = validateWithDatabase(columnReference.table(), columnReference.column(), value);
                        if (isFailFast() && resultFromDB.length() != 0) {
                            throw new ValidationException(columnReference.message().length() == 0 ? resultFromDB : columnReference.message());
                        }
                        String message = columnReference.message().length() == 0 ? resultFromDB : columnReference.message();
                        if (message.length() > 0) {
                            result.append(message).append("; ");
                        }
                    } catch (NullPointerException e) {
                        /*
                         *  reflection.getValue() may return a null object
                         *  then, the following toString() will produce a NullPointerException
                         *  but, the null value was considered to be valid
                         */
                        return object;
                    } catch (Exception e) {
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
