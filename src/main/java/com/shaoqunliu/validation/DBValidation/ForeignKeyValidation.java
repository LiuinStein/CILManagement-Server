package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.reflection.POJOReflection;
import com.shaoqunliu.validation.Contracts;
import com.shaoqunliu.validation.exception.ValidationException;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;
import com.shaoqunliu.validation.exception.ValidationInternalException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * To validate with database as a like-foreign-key function
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class ForeignKeyValidation extends AbstractDatabaseValidation {

    public ForeignKeyValidation(DataSource dataSource) throws ValidationInternalException {
        super(dataSource);
    }

    /**
     * @see com.shaoqunliu.validation.DBValidation.AbstractDatabaseValidation#validateWithDatabase(String, String, String)
     */
    @Override
    protected void validateWithDatabase(String table, String column, String value) throws ValidationException {
        if (value == null || value.length() == 0) {
            /*
             * Null or empty string considered to be valid.
             */
            return;
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
                throw new ValidationException("Value '" + value + "' for column " + column + " is invalid!");
            }
        } catch (SQLException e) {
            throw new ValidationInternalException(e.getMessage());
        }
    }

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public <T> T validate(T object, Class<?>... groups) throws ValidationException {
        Contracts.assertNotNull(object, "Null object was given");
        sanityCheckGroups(groups);
        StringBuilder result = new StringBuilder(128);
        POJOReflection reflection = new POJOReflection(object);
        reflection.forEachAnnotationByFieldByType((field, databaseColumnReference) -> {
            if (isFailFast() && result.length() != 0) {
                return;
            }
            boolean needToCheck;
            if (groups.length == 0 && databaseColumnReference.groups().length == 0) {
                needToCheck = true;
            } else {
                /*
                 * the Arrays.asList create a fake ArrayList (java.util.Arrays.ArrayList),
                 * the real ArrayList is in the package java.util not java.util.Arrays.ArrayList
                 * the fake ArrayList can NOT add or remove elements, its Read-Only, only used for traverse
                 */
                needToCheck = Arrays.stream(databaseColumnReference.groups())
                        .anyMatch(Arrays.asList(groups)::contains);
            }
            if (needToCheck) {
                try {
                    String value = reflection.getValue(field.getName()).toString();
                    validateWithDatabase(databaseColumnReference.table(), databaseColumnReference.column(), value);
                } catch (NullPointerException ignored) {
                    /*
                     * reflection.getValue() may return a null object
                     * then, the following .toString() will produce a NullPointerException
                     * but, the null value was considered to be valid
                     */
                } catch (ValidationException e) {
                    result.append(databaseColumnReference.message().length() == 0 ? e.getMessage() : databaseColumnReference.message()).append("; ");
                } catch (Exception e) {
                    result.append(e.getMessage()).append("; ");
                }
            }
        }, DatabaseColumnReference.class);
        if (result.length() != 0) {
            throw new ValidationException(result.toString());
        }
        return object;
    }
}
