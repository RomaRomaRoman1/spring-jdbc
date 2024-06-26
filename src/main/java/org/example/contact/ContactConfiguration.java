package org.example.contact;

import org.example.config.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@Import({ApplicationConfiguration.class})
public class ContactConfiguration {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ContactService contactService;
    @Autowired
    public ContactConfiguration(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ContactService contactService) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.contactService = contactService;
    }

    @Bean
    public ContactDao contactDao() {
        return new NamedJbdcContactDao(namedParameterJdbcTemplate, contactService);
    }
}
