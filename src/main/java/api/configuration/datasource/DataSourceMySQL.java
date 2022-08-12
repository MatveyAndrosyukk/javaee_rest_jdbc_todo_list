package api.configuration.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataSourceMySQL {
    final static String USERNAME = "root";
    final static String PASSWORD = "root";
    final static String URL = "jdbc:mysql://localhost:3308/task_service_db?useSSL=false";

    private final static HikariConfig config = new HikariConfig();
    private final static HikariDataSource dataSource;

    static {
        config.setJdbcUrl(URL);
        config.setUsername(USERNAME);
        config.setPassword(PASSWORD);
        config.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        config.setConnectionTimeout(15000);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(600000);
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("restServiceDbPool");
        config.setRegisterMbeans(true);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private DataSourceMySQL(){
    }

    public static DataSource getDataSource(){
        return dataSource;
    }
}
