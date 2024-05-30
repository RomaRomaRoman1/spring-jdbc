package org.example.hibernate;

import org.example.contact.Contact;

import java.util.List;

public interface ContactDaoHibernate {
    List<Contact> getAllContacts();
    Contact getContact(long contactId);
    long addContact(Contact contact);
    void updatePhoneNumber(long contactId, String phoneNumber);
    void updateEmail(long contactId, String email);
    void deleteContact(long contactId);
}
