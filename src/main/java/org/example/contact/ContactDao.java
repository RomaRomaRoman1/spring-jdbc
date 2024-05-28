package org.example.contact;

import java.util.List;

public interface ContactDao {
    List<Contact> getAllContacts();
    Contact getContact(long contactId);
    long addContact(Contact contact);
    void updatePhoneNumber(long contactId, String phoneNumber);
    void updateEmail(long contactId, String email);
    void deleteContact(long contactId);
    List<Contact> addSomeContacts(String filePath);
}
