package com.example.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.repository.AccountRepository;
import com.example.entity.Account;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    //A private method to check if account exists by username
    private Account findAccountByUserName(String userName){
        if(userName != null && userName.trim().length() > 0){
            List<Account> allAccounts = accountRepository.findAll();
            for(Account account: allAccounts){
                if(account.getUsername().equals(userName))
                    return account;
            }
        }
        return null;
    }

    public Account registerAccount(Account account){
        String userName = account.getUsername();
        String password = account.getPassword();
        //First use case: User Registration
        boolean isUserBlank = true;
        if(userName != null && userName.trim().length() > 0)
            isUserBlank = false;
        Account existingAccount = findAccountByUserName(userName);
        if(!isUserBlank && password.trim().length() >= 4 && existingAccount != null )
            return accountRepository.save(account);
        return null;
    }

    public Account loginAccount(Account account){
        //Second use case: Login Account
        Account existingAccount = findAccountByUserName(account.getUsername());
        if(existingAccount != null && existingAccount.getPassword().equals(account.getPassword()))
            return existingAccount;
        return null;
    } 
}
