package com.shaoqunliu.validation.validator.database;

import com.shaoqunliu.validation.exception.ValidationInternalException;
import com.shaoqunliu.validation.validator.AbstractValidator;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.Objects;

/**
 * @author Shaoqun Liu
 * @since 1.8
 */
public abstract class AbstractDatabaseValidator extends AbstractValidator {

    /**
     * A connection pool or other data source
     */
    private static DataSource dataSource;

    /**
     * The basic sql statement used to query if the row exists
     */
    private static String basicSql = "SELECT COUNT(*)";

    /**
     * Construct an object with a specific data source
     *
     * @param dataSource the data source
     */
    public AbstractDatabaseValidator(DataSource dataSource) throws ValidationInternalException {
        setDataSource(dataSource);
    }

    /**
     * Do not make it private or package-private due to it may be used at applicationContext.xml for the xml tag <property>
     */
    public void setDataSource(DataSource dataSource) {
        Objects.requireNonNull(dataSource);
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            /*
             * for Spring Jdbc transaction aware data source
             * @see https://docs.spring.io/autorepo/docs/spring-framework/5.0.5.RELEASE/javadoc-api/org/springframework/jdbc/datasource/TransactionAwareDataSourceProxy.html
             */
            AbstractDatabaseValidator.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            AbstractDatabaseValidator.dataSource = dataSource;
        }
    }

    public void setBasicSql(String basicSql) {
        /*
         * Do not try to do it AGAIN!
         *
         * Regular expressions can match languages only a finite state automaton can parse,
         * which is very limited, whereas SQL is a syntax.
         * It can be demonstrated you can't validate SQL with a regex.
         * So, you can stop trying.
         *
         * If you think you CAN do it, NO you can't, believe me.
         * If you have tried to do it and then failed, add this counter to WARN the next
         *
         * Time wasted here = 1.5 hours
         */
//        Contracts.assertMatchPattern(basicSql.toUpperCase(), "(SELECT)((?<=^SELECT)).*", "Invalid SQL statement");
        AbstractDatabaseValidator.basicSql = basicSql;
    }

    static DataSource getDataSource() {
        return dataSource;
    }

    static String getBasicSql() {
        return basicSql;
    }
}
