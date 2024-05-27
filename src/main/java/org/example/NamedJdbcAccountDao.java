package org.example;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Объявление класса как Spring компонента для доступа к данным
@Primary // Обозначение этого компонента как первичного (если есть другие реализации)
public class NamedJdbcAccountDao implements AccountDao {

    private final NamedParameterJdbcTemplate namedJdbcTemplate; // Поле для работы с базой данных

    // Конструктор класса, принимающий NamedParameterJdbcTemplate
    public NamedJdbcAccountDao(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate; // Инициализация поля
    }

    // Добавление счета с указанным идентификатором и суммой
    @Override
    public Account addAccount(long id, long amount) {
        namedJdbcTemplate.update( // Выполнение SQL-запроса для вставки записи в таблицу
                "INSERT INTO ACCOUNT(ID, AMOUNT) VALUES(:id, :amount)", // SQL-запрос с параметрами
                new MapSqlParameterSource() // Параметры запроса
                        .addValue("id", id) // Установка значения параметра id
                        .addValue("amount", amount) // Установка значения параметра amount
        );
        return new Account(id, amount); // Возвращение созданного объекта Account
    }

    // Добавление счета с автоматически сгенерированным идентификатором и указанной суммой
    @Override
    public Account addAccount(long amount) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // Объект для хранения сгенерированного идентификатора

        namedJdbcTemplate.update( // Выполнение SQL-запроса для вставки записи в таблицу с автогенерацией ключа
                "INSERT INTO ACCOUNT(AMOUNT) VALUES(:amount)", // SQL-запрос с параметром
                new MapSqlParameterSource("amount", amount), // Параметры запроса
                keyHolder, // Объект для хранения сгенерированного ключа, получает значение, указанного в массиве строк после него в MapSqlParameterSource
                new String[] { "id" } // Имена генерируемых ключей, должны совпадать с именами в sql таблице
        );

        var accountId = keyHolder.getKey().longValue(); // Получение сгенерированного идентификатора
        return new Account(accountId, amount); // Возвращение созданного объекта Account
    }

    // Получение счета по идентификатору
    @Override
    public Account getAccount(long accountId) {
        return namedJdbcTemplate.queryForObject( // Выполнение SQL-запроса для получения одной записи
                "SELECT ID, AMOUNT FROM ACCOUNT WHERE ID = :id", // SQL-запрос с параметром
                new MapSqlParameterSource("id", accountId), // Параметры запроса
                (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT")) // лямбда-выражение для преобразования строки результата запроса в объект Account.
        );
    }

    // Обновление суммы на счете по идентификатору
    @Override
    public void setAmount(long accountId, long amount) {
        namedJdbcTemplate.update( // Выполнение SQL-запроса для обновления записи
                "UPDATE ACCOUNT SET AMOUNT = :amount WHERE ID = :id", // SQL-запрос с параметрами
                new MapSqlParameterSource() // Параметры запроса
                        .addValue("id", accountId) // Установка значения параметра id
                        .addValue("amount", amount) // Установка значения параметра amount
        );
    }

    // Получение списка всех счетов
    @Override
    public List<Account> getAllAccounts() {
        return namedJdbcTemplate.query( // Выполнение SQL-запроса для получения списка записей
                "SELECT ID, AMOUNT FROM ACCOUNT", // SQL-запрос
                (rs, i) -> new Account(rs.getLong("ID"), rs.getLong("AMOUNT")) // Преобразование результатов запроса в объекты Account
        );
    }
}