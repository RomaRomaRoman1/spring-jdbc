package org.example.hibernate;

import org.example.contact.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory sessionFactory() {
        var configuration = new Configuration()
                .addAnnotatedClass(Contact.class)
                .configure()
                .buildSessionFactory();
        return configuration;
    }
}
