package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){  
        Account existingAccount =  accountService.findAccountByUserName(account.getUsername()) ; 
        if(existingAccount == null) {
            Account addedAccount = accountService.registerAccount(account);        
            if(addedAccount != null)
                return ResponseEntity.status(200).body(addedAccount);
            else    
                return ResponseEntity.status(400).body(null);
        }
        return ResponseEntity.status(409).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginToAccount(@RequestBody Account account){
        Account loggedInAccount = accountService.loginAccount(account);
        if(loggedInAccount != null)
            return ResponseEntity.status(200).body(loggedInAccount);
        else    
            return ResponseEntity.status(401).body(null);
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
        
        Message addedMessage = messageService.addMessage(message);
        System.out.println("**Create new message calling added message::"+addedMessage);
        if(addedMessage != null){
            return ResponseEntity.status(200).body(addedMessage);
        }
        else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id){
        Message deletedMessage = messageService.deleteMessage(message_id);
        if(deletedMessage != null){
            return ResponseEntity.status(200).body(1);
        }
        else {
            return ResponseEntity.status(200).body(null);
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesPostedBy(@PathVariable int account_id){
        return ResponseEntity.status(200).body(messageService.getMessagesPostedBy(account_id));
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage != null)
            return ResponseEntity.status(200).body(1);
        else 
            return ResponseEntity.status(400).body(null);        
    }   


}
