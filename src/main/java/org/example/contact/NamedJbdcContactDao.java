package org.example.contact;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@Primary
public class NamedJbdcContactDao implements ContactDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    public NamedJbdcContactDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
    @Override
    public List<Contact> getAllContacts() {
        return namedJdbcTemplate.query(
                "SELECT id, name, surname, email, phone_number FROM contact",
                ((rs, i) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("surname"), rs.getString("email"),
                        rs.getString("phone_number")))
        );
    }

    @Override
    public Contact getContact(long contactId) {
       return namedJdbcTemplate.queryForObject(
               "SELECT id, name, surname, email, phone_number FROM contact WHERE id=:id",
               new MapSqlParameterSource("id", contactId),
               (rs, i) -> new Contact(rs.getLong("id"), rs.getString("name"), rs.getString("surname"),
                       rs.getString("email"),rs.getString("phone_number"))
       );
    }

    @Override
    public long addContact(Contact contact) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
      namedJdbcTemplate.update(
              "INSERT INTO CONTACT(name, surname, email, phone_number) VALUES (:name, :surname, :email, :phone_number)",
              new MapSqlParameterSource()
                      .addValue("name", contact.getName())
                      .addValue("surname", contact.getSurname())
                      .addValue("email", contact.getEmail())
                      .addValue("phone_number", contact.getPhone()),
              keyHolder,
              new String[] {"id"});
      long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
      contact.setId(generatedId);
      return generatedId;
    }

    @Override
    public void updatePhoneNumber(long contactId, String phoneNumber) {
    namedJdbcTemplate.update("UPDATE CONTACT SET phone_number = :phone WHERE id =:id",
            new MapSqlParameterSource()
                    .addValue("id", contactId)
                    .addValue("phone", phoneNumber)
    );
    }

    @Override
    public void updateEmail(long contactId, String email) {
        namedJdbcTemplate.update("UPDATE CONTACT SET email = :mail WHERE id =:id",
                new MapSqlParameterSource()
                        .addValue("id", contactId)
                        .addValue("mail", email)
        );
    }

    @Override
    public void deleteContact(long contactId) {
        namedJdbcTemplate.update("DELETE FROM contact WHERE id=:idContact",
                new MapSqlParameterSource()
                        .addValue("idContact", contactId)
        );
    }
}
