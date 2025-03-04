package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import java.util.List;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.exception.DuplicateUserFoundException;;

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
    /**
     * Post Mapping method for Use Case 1: User Registration
     * @param account
     * @return Response Entity with Account object created and sets the appropriate http status codes
     */
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        Account addedAccount = accountService.registerAccount(account);        
        if(addedAccount != null)
            return ResponseEntity.status(200).body(addedAccount);
        else    
            return ResponseEntity.status(400).body(null);        
    }
    /**
     * Exception handler method for handling Duplicate user found exception
     * @param ex
     * @return Duplicate user found exception message
     */
    @ExceptionHandler({RuntimeException.class, DuplicateUserFoundException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleDuplicateUserException(DuplicateUserFoundException ex) {
        return ex.getMessage();
    }

    /**
     * Post mapping method for Use case 2: Login
     * @param account
     * @return Response Entity with Account object logged in and sets the appropriate http status codes
     */
    @PostMapping("/login")
    public ResponseEntity<Account> loginToAccount(@RequestBody Account account){
        Account loggedInAccount = accountService.loginAccount(account);
        if(loggedInAccount != null)
            return ResponseEntity.status(200).body(loggedInAccount);
        else    
            return ResponseEntity.status(401).body(null);
    }

    /**
     * Post mapping method for Use Case 3: Create New Message
     * @param message
     * @return Response Entity with Message object created and sets the appropriate http status codes
     */
    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage != null)
            return ResponseEntity.status(200).body(addedMessage);        
        else
            return ResponseEntity.status(400).body(null);
    }

    /**
     * Get mapping method for Use Case 4: Get All Messages
     * @return Response Entity with List of all Message objects
     */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

   /**
    * Get mapping method for Use Case 5: Get One Message Given Message Id
    * @param message_id
    * @return Response Entity with Message object 
    */
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id){
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }

    /**
     * Delete mapping method for Use Case 6: Delete a Message Given Message Id
     * @param message_id
     * @return Number of deleted records and sets the http status code appropriately
     */
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id){       
        Message deletedMessage = messageService.deleteMessage(message_id);
        if(deletedMessage != null)
            return ResponseEntity.status(200).body(1);        
        else 
            return ResponseEntity.status(200).body(null);        
    }
    /**
     * Get Mapping method for Use Case 8: Get All Messages From User Given Account Id
     * @param account_id
     * @return List of Message objects
     */
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesPostedBy(@PathVariable int account_id){
        return ResponseEntity.status(200).body(messageService.getMessagesPostedBy(account_id));
    }
    /**
     * Patch mapping method for Use Case 7: Update Message Given Message Id
     * @param message_id
     * @param message
     * @return number of updated records and sets the appropriate http status codes.
     */
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        Message updatedMessage = messageService.updateMessage(message_id, message);
        if(updatedMessage != null)
            return ResponseEntity.status(200).body(1);
        else 
            return ResponseEntity.status(400).body(null);        
    }   


}
