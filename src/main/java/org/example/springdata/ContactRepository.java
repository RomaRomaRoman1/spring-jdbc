package org.example.springdata;

import org.example.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository//Указывает, что этот интерфейс является репозиторием Spring Data JPA. Репозиторий отвечает за взаимодействие с базой данных.
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Modifying//Эта аннотация указывает, что аннотированный метод выполняет модифицирующую операцию (например, update или delete).
    // Она нужна для методов, которые изменяют данные.
    @Transactional//Обеспечивает, что метод выполняется в рамках транзакции. Если метод выбросит исключение, транзакция будет откатана.
    @Query("update Contact c set c.phone = :phone where c.id = :id")//Используется для указания пользовательского JPQL (Java Persistence Query Language) запроса.
        // В данном случае, запрос обновляет поле phone у сущности Contact, где id равно переданному параметру.
    void updatePhone(@Param("id") Long id, @Param("phone") String phone);// Связывает параметры метода с параметрами запроса JPQL.
    @Modifying
    @Transactional
    @Query("update Contact c set c.email = :mail where c.id = :id")
    void updateEmail(@Param("id") Long contactId,@Param("mail") String newEmail);
    // В данном случае, параметры id и phone используются в запросе для подстановки значений.

}
