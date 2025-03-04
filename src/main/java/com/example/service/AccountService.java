package com.example.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.repository.AccountRepository;
import com.example.entity.Account;
import com.example.exception.DuplicateUserFoundException;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    /**
     * Use case 1: User Registration
     * @param account
     * @return Registered Account object
     * If an account already exists with the username sent by Request Body, it throws DuplicateUserFoundException, which is
     * a custom exception.
     */
    public Account registerAccount(Account account){        
        String userName = account.getUsername();
        String password = account.getPassword();
        boolean isUserBlank = true;
        if(userName != null && userName.trim().length() > 0)
            isUserBlank = false;
        Account existingAccount = accountRepository.findByUsername(userName);
        if(existingAccount != null)
            throw new DuplicateUserFoundException("Duplicate Username found, Account already exists with this Username");        
        if(!isUserBlank && password.trim().length() >= 4 && existingAccount == null )
            return accountRepository.save(account);
        return null;
    }

    /**
     * Use case 2: Login Account
     * @param account
     * @return Logged in user's Account object
     */
    public Account loginAccount(Account account){        
        Account existingAccount = accountRepository.findByUsername(account.getUsername());
        if(existingAccount != null && existingAccount.getPassword().equals(account.getPassword()))
            return existingAccount;
        return null;
    } 
}
