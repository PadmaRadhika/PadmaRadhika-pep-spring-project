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
    public Account findAccountByUserName(String userName){
        if(userName != null && userName.trim().length() > 0){            
            return accountRepository.findByUsername(userName);
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
        System.out.println("##existing user::"+existingAccount);       
        if(!isUserBlank && password.trim().length() >= 4 && existingAccount == null )
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
