package org.example;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcAccountDao implements AccountDao {

    @Override
    public Account addAccount(long id, long amount) {
        return null;
    }

    @Override
    public Account addAccount(long amount) {
        return null;
    }

    @Override
    public Account getAccount(long accountId) {
        return null;
    }

    @Override
    public void setAmount(long accountId, long amount) {

    }

    @Override
    public List<Account> getAllAccounts() {
        return List.of();
    }
}
