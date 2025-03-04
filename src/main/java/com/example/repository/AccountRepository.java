package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    /**
     * Property Expression method to get Account by passing username
     * @param userName
     * @return Account object
     */
    Account findByUsername(String userName);
}
