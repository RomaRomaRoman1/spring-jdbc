package org.example.hibernate;

import org.example.contact.Contact;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContactDaoHibernateImpl implements ContactDaoHibernate {
    private final SessionFactory sessionFactory;
    @Autowired
    public ContactDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Contact> getAllContacts() {
        try(var session = sessionFactory.openSession()) {
            var query = session.createQuery("FROM Contact", Contact.class);
            //"FROM Contact" — это HQL-запрос, который означает "выбрать все объекты типа Contact".
            //Contact.class указывает тип возвращаемых объектов.
            return query.list();
            //query.list() выполняет запрос и возвращает результаты в виде списка (List<Contact>).
        }
    }

    @Override
    public Contact getContact(long contactId) {
        try (var session = sessionFactory.openSession()){
            Contact contact = session.get(Contact.class, contactId);
            if (contact!=null) {
                return contact;
            }
            else {
                throw new RuntimeException(String.format("Contact with id: %s wasn't to find", contactId));
            }
        }
    }

    @Override
    public long addContact(Contact contact) {
        try (var session = sessionFactory.openSession()){
             var transaction = session.beginTransaction();
             var contactId = (long) session.save(contact);
             transaction.commit();
             return contactId;
        }
    }

    @Override
    public void updatePhoneNumber(long contactId, String phoneNumber) {
    try(var session =sessionFactory.openSession()){
        var transaction = session.beginTransaction();
        var changeContact = session.get(Contact.class, contactId);//чтобы изменения фиксировались важно запрашивать объект в рамках текущей сессии
        if (changeContact!=null) {//если не найден контакт по id, то session.get вернет null, тут мы его и обработаем
            changeContact.setPhone(phoneNumber);
        }
        else {
            throw new RuntimeException(String.format("Contact with id: %s wasn't to find", contactId));
        }
        transaction.commit();
    }
    }

    @Override
    public void updateEmail(long contactId, String email) {
        try(var session =sessionFactory.openSession()){
            var transaction = session.beginTransaction();
            var changeContact = session.get(Contact.class, contactId);//чтобы изменения фиксировались важно запрашивать объект в рамках текущей сессии
            if (changeContact!=null) {//если не найден контакт по id, то session.get вернет null, тут мы его и обработаем
                changeContact.setEmail(email);
            }
            else {
                throw new RuntimeException(String.format("Contact with id: %s wasn't to find", contactId));
            }
            transaction.commit();
        }
    }

    @Override
    public void deleteContact(long contactId) {
        try (var session = sessionFactory.openSession()){
            var transaction = session.beginTransaction();
            Contact contact = session.get(Contact.class, contactId);
            if (contact!=null) {
                session.delete(contact);
            }
            else {
                throw new RuntimeException(String.format("Contact with id: %s wasn't to find", contactId));
            }
            transaction.commit();
        }
    }
}
