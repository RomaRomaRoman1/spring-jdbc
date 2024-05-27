package org.example;

import org.example.config.ApplicationConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //Контекст приложения Spring с использованием аннотации AnnotationConfigApplicationContext и конфигурации
        //ApplicationConfiguration с указанием @ComponentScan
        var applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        //Получение экземпляра бина AccountDao, тк NamedJdbcAccountDao отмечен primary, будет использована эта ре
        var accountDao = applicationContext.getBean(AccountDao.class);
        var account = accountDao.getAccount(3L);

        System.out.println(account);

        accountDao.setAmount(3L, 7000L);
        account = accountDao.getAccount(3L);
        System.out.println(account);

        var newAccount = accountDao.getAccount(11L);
        System.out.println(newAccount);

        var accounts = accountDao.getAllAccounts();
        System.out.println(accounts);
    }
}
