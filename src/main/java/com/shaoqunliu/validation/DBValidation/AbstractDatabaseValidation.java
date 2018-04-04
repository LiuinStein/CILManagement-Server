package com.shaoqunliu.validation.DBValidation;

import com.shaoqunliu.validation.AbstractValidation;
import com.shaoqunliu.validation.ValidationException;
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

    public AbstractDatabaseValidation(DataSource dataSource) {
        setDataSource(dataSource);
    }

    /**
     * Validate the value with database
     *
     * @param table  the table name
     * @param column the column name
     * @param value  the value need to be check
     * @return empty string when the value is valid, otherwise, the digest of invalidation message will be returned
     * @throws ValidationException when SQLException occurred
     */
    protected abstract String validateWithDatabase(String table, String column, String value) throws ValidationException;

    /**
     * @see com.shaoqunliu.validation.ValidationAdapter
     */
    @Override
    public abstract <T> T validate(T object, Class... groups) throws ValidationException;

    /**
     * Do not make it private or package-private due to it may be used at applicationContext.xml for the xml tag <property>
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = ((TransactionAwareDataSourceProxy) dataSource).getTargetDataSource();
        } else {
            this.dataSource = dataSource;
        }
    }

    public void setBasicSql(String basicSql) {
        this.basicSql = basicSql;
    }

    DataSource getDataSource() {
        return dataSource;
    }

    String getBasicSql() {
        return basicSql;
    }
}
