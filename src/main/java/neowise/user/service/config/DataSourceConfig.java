package neowise.user.service.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DataSourceConfig {

    private String RDS_HOSTNAME = Env.get("RDS_HOSTNAME");
    private String RDS_PORT = Env.get("RDS_PORT");
    private String RDS_DB_NAME = Env.get("RDS_DB_NAME");
    private String RDS_USERNAME = Env.get("RDS_USERNAME");
    private String RDS_PASSWORD = Env.get("RDS_PASSWORD");
    private String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private String DRIVER = "postgresql";
    private String JDBC_URL = "jdbc:" + DRIVER + "://" + RDS_HOSTNAME + ":" + RDS_PORT + "/" + RDS_DB_NAME;

    @Bean
    public DataSource getDataSource() {
        // "jdbc:driver://hostname:port/dbName?user=userName&password=password";
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER_CLASS_NAME);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(RDS_USERNAME);
        dataSource.setPassword(RDS_PASSWORD);
        return dataSource;
    }

}
