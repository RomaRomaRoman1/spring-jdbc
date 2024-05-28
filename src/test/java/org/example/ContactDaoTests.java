package org.example;

import org.example.contact.Contact;
import org.example.contact.ContactConfiguration;
import org.example.contact.ContactDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ContactConfiguration.class)
@Sql("classpath:contact.sql")
public class ContactDaoTests {
    @Autowired ContactDao contactDao;

    private static final Contact IVAN = new Contact(
            1000L, "Ivan", "Ivanov", "iivanov@gmail.com", "1234567"
    );

    private static final Contact MARIA = new Contact(
            2000L, "Maria", "Ivanova", "mivanova@gmail.com", "7654321"
    );

    /**
     * There are two contacts inserted in the database in contact.sql.
     */
    private static final List<Contact> PERSISTED_CONTACTS = List.of(IVAN, MARIA);

    @Test
    void addContact() {
        var contact = new Contact("Jackie", "Chan", "jchan@gmail.com", "1234567890");
        var contactId = contactDao.addContact(contact);
        contact.setId(contactId);

        var contactInDb = contactDao.getContact(contactId);


        assertThat(contactInDb).isEqualTo(contact);
    }

    @Test
    void getContact() {
        var contact = contactDao.getContact(IVAN.getId());

        assertThat(contact).isEqualTo(IVAN);
    }

    @Test
    void getAllContacts() {
        var contacts = contactDao.getAllContacts();

        assertThat(contacts).containsAll(PERSISTED_CONTACTS);
    }

    @Test
    void updatePhoneNumber() {
        var contact = new Contact("Jekyll", "Hide", "jhide@gmail.com", "");
        var contactId = contactDao.addContact(contact);

        var newPhone = "777-77-77";
        contactDao.updatePhoneNumber(contactId, newPhone);

        var updatedContact = contactDao.getContact(contactId);
        assertThat(updatedContact.getPhone()).isEqualTo(newPhone);
    }

    @Test
    void updateEmail() {
        var contact = new Contact("Captain", "America", "", "");
        var contactId = contactDao.addContact(contact);

        var newEmail = "cap@gmail.com";
        contactDao.updateEmail(contactId, newEmail);

        var updatedContact = contactDao.getContact(contactId);
        assertThat(updatedContact.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void deleteContact() {
        var contact = new Contact("To be", "Deleted", "", "");
        var contactId = contactDao.addContact(contact);

        contactDao.deleteContact(contactId);

        assertThatThrownBy(() -> contactDao.getContact(contactId))
                .isInstanceOf(EmptyResultDataAccessException.class);

    }
    @Test
    void addSomeContacts() {
        contactDao.deleteContact(IVAN.getId());
        contactDao.deleteContact(MARIA.getId());
        List <Contact> contactsWithCsv = contactDao.addSomeContacts("contacts.csv");
        List<Contact> allContacts = contactDao.getAllContacts();
        assertThat(contactsWithCsv.size()).isEqualTo(allContacts.size());

        for(Contact contact:allContacts) {
            assertThat(contact.getId()).isNotNull();
        }

    }
}
