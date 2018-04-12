package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.validation.AbstractValidation;
import com.shaoqunliu.validation.Contracts;
import com.shaoqunliu.validation.exception.ValidationException;
import com.shaoqunliu.validation.exception.ValidationInternalException;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

/**
 * @author Shaoqun Liu
 */
public abstract class AbstractDatabaseValidation extends AbstractValidation {

    /**
     * A connection pool or other data source
     */
    private DataSource dataSource;

    /**
     * The basic sql statement used to query if the row exists
     */
    private String basicSql = "SELECT COUNT(*)";

    public AbstractDatabaseValidation(DataSource dataSource) throws ValidationInternalException {
        setDataSource(dataSource);
    }

    /**
     * Validate the value with database
     *
     * @param table  the table name
     * @param column the column name
     * @param value  the value need to be check
     * @throws ValidationException when SQLException occurred
     */
    protected abstract void validateWithDatabase(String table, String column, String value) throws ValidationException;

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter#validate(Object, Class[])
     */
    @Override
    public abstract <T> T validate(T object, Class<?>... groups) throws ValidationException;

    /**
     * Do not make it private or package-private due to it may be used at applicationContext.xml for the xml tag <property>
     */
    public void setDataSource(DataSource dataSource) throws ValidationInternalException {
        Contracts.assertNotNull(dataSource, "Null data source");
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            /*
             * for Spring Jdbc transaction aware data source
             * @see https://docs.spring.io/autorepo/docs/spring-framework/5.0.5.RELEASE/javadoc-api/org/springframework/jdbc/datasource/TransactionAwareDataSourceProxy.html
             */
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
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
        this.basicSql = basicSql;
    }

    DataSource getDataSource() {
        return dataSource;
    }

    String getBasicSql() {
        return basicSql;
    }
}
