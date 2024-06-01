package org.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:jdbc.properties")
@ComponentScan({"org.example.contact", "org.example.hibernate"})
public class ApplicationConfiguration {
    @Value("${jdbc.url}")
    private String jdbcUrl;
    @Value("${jdbc.driverClassName}")
    private String driverClass;
    @Value("${jdbc.initialSize}")
    private int initialSize;
    @Value("${jdbc.maxActive}")
    private int maxActive;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setInitialSize(initialSize);
        dataSource.setUrl(jdbcUrl);
        dataSource.setMaxTotal(maxActive);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource());
        createTableIfNotExists(namedParameterJdbcTemplate);
        return namedParameterJdbcTemplate;
    }
    private void createTableIfNotExists(NamedParameterJdbcTemplate jdbcTemplate) {
        String sql = "CREATE TABLE IF NOT EXISTS contact (ID serial primary key , NAME varchar(255), SURNAME varchar(255), EMAIL varchar (255), PHONE_NUMBER varchar (255))";
        jdbcTemplate.getJdbcTemplate().execute(sql);
    }

}
