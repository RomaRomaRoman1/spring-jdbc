package org.example;

import org.example.contact.Contact;
import org.example.contact.ContactConfiguration;
import org.example.hibernate.ContactDaoHibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ContactConfiguration.class)
@Sql("classpath:contact.sql")
public class ContactDaoHibernateTest {
    @Autowired
    ContactDaoHibernate contactDaoHibernate;
    private static final Contact IVAN = new Contact(
            1000L, "Ivan", "Ivanov", "iivanov@gmail.com", "1234567"
    );

    private static final Contact MARIA = new Contact(
            2000L, "Maria", "Ivanova", "mivanova@gmail.com", "7654321"
    );
    private static final List<Contact> PERSISTED_CONTACTS = List.of(IVAN, MARIA);

    @Test
    void addContact() {
        var contact = new Contact("Jackie", "Chan", "jchan@gmail.com", "1234567890");
        var contactId = contactDaoHibernate.addContact(contact);
        contact.setId(contactId);

        var contactInDb = contactDaoHibernate.getContact(contactId);


        assertThat(contactInDb).isEqualTo(contact);
    }
    @Test
    void getContact() {
        var contact = contactDaoHibernate.getContact(IVAN.getId());

        assertThat(contact).isEqualTo(IVAN);
    }
    @Test
    void getAllContacts() {
        var contacts = contactDaoHibernate.getAllContacts();

        assertThat(contacts).containsAll(PERSISTED_CONTACTS);
    }
    @Test
    void updatePhoneNumber() {
        var contact = new Contact("Jekyll", "Hide", "jhide@gmail.com", "");
        var contactId = contactDaoHibernate.addContact(contact);

        var newPhone = "777-77-77";
        contactDaoHibernate.updatePhoneNumber(contactId, newPhone);

        var updatedContact = contactDaoHibernate.getContact(contactId);
        assertThat(updatedContact.getPhone()).isEqualTo(newPhone);
    }
    @Test
    void updateEmail() {
        var contact = new Contact("Captain", "America", "", "");
        var contactId = contactDaoHibernate.addContact(contact);

        var newEmail = "cap@gmail.com";
        contactDaoHibernate.updateEmail(contactId, newEmail);

        var updatedContact = contactDaoHibernate.getContact(contactId);
        assertThat(updatedContact.getEmail()).isEqualTo(newEmail);
    }
    @Test
    void deleteContact() {
        var contact = new Contact("To be", "Deleted", "", "");
        var contactId = contactDaoHibernate.addContact(contact);

        contactDaoHibernate.deleteContact(contactId);

        assertThatThrownBy(()->contactDaoHibernate.getContact(contactId)).isInstanceOf(RuntimeException.class);

    }
}
