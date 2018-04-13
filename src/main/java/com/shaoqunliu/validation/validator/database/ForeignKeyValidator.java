package com.shaoqunliu.validation.validator.database;

import com.shaoqunliu.validation.annotation.DatabaseColumnReference;
import com.shaoqunliu.validation.exception.ValidationInternalException;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

/**
 * To validate with database as a like-foreign-key function
 *
 * @author Shaoqun Liu
 * @since 1.8
 */
public class ForeignKeyValidator extends AbstractDatabaseValidator {

    public ForeignKeyValidator(DataSource dataSource) throws ValidationInternalException {
        super(dataSource);
        classFunctionMap.put(DatabaseColumnReference.class, this::databaseColumnReference);
    }

    /*
     * This function for checking annotation @DatabaseColumnReference
     * Check the value with database like a foreign key
     */
    private Boolean databaseColumnReference(Object object, Function<String, Object> getMember) {
        if (object == null) {
            return true;
        }
        String value = object.toString();
        String table = getMember.apply("table").toString();
        String column = getMember.apply("column").toString();
        String sql = String.format("%s FROM %s WHERE %s=?", getBasicSql(), table, column);
        try {
            PreparedStatement statement = getDataSource().getConnection().prepareStatement(sql);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            /*
             * empty result set considered to be invalid
             */
            if (!resultSet.next() || resultSet.getInt(1) == 0) {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
