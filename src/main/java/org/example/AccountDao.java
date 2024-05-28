package org.example;

import java.util.List;

public interface AccountDao {
    Account getAccount(long  accountId);
    void setAmount(long accountId, long amount);
    List<Account> getAllAccounts();
    Account addAccountWithAmount(long id, long amount);
    Account addAccount(long amount);
}
