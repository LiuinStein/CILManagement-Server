package com.shaoqunliu.validation.DBValidation;

import cn.opencil.validation.group.database.DatabaseUserValidation;
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
     * @see com.shaoqunliu.validation.ValidationAdapter
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
            boolean needToCheck = false;
            if (groups.length == 0 && databaseColumnReference.groups().length == 0) {
                needToCheck = true;
            } else {
                for (Class clazz : groups) {
                    for (Class test : databaseColumnReference.groups()) {
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
                    String value = reflection.getValue(field.getName()).toString();
                    validateWithDatabase(databaseColumnReference.table(), databaseColumnReference.column(), value);
                } catch (NullPointerException ignored) {
                    /*
                     *  reflection.getValue() may return a null object
                     *  then, the following .toString() will produce a NullPointerException
                     *  but, the null value was considered to be valid
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
