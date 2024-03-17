package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@SuppressWarnings("MultipleStringLiterals")
public class JdbsClientConfig {
    @Bean
    public DataSource dataSource() {
        return new DriverManagerDataSource(
            "jdbc:postgresql://localhost:5432/scrapper",
            "postgres",
            "postgres"
        );
    }

    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }
}
