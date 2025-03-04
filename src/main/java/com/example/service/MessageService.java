package com.example.service;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    /**
     * Use case: 3 Create New Message
     * @param message
     * @return Message object
     */
    public Message addMessage(Message message){        
        String messageText = message.getMessageText();
        int postedBy = message.getPostedBy();        
        boolean isMessageBlank = true;        
        if(messageText != null && messageText.length() > 0 && messageText.length() <= 255)
            isMessageBlank = false;        
        Optional<Account> optionalAccount = accountRepository.findById(postedBy);        
        if(optionalAccount.isPresent() && !isMessageBlank){            
            return messageRepository.save(message);
        }
        return null;        
    }
    
    /**
     * Use Case:4, Get All Messages
     * @return List of Message objects
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Use Case: 5, Get One Message, given Message id
     * @param id
     * @return Message object
     */
    public Message getMessageById(int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent())
            return optionalMessage.get();
        return null;
    }

    /**
     * Use Case: 6, Delete a message, given Message id
     * @param messageId
     * @return deleted message object
     */
    public Message deleteMessage(int messageId){
        Message existingMessage = getMessageById(messageId);        
        if(existingMessage != null){            
            messageRepository.deleteById(messageId);
        }
        if(getMessageById(messageId) == null)
            return existingMessage;
        return null;
    }

    /**
     * Use Case: 7, Update Message given messageid
     * @param messageId
     * @param message
     * @return updated Message object
     */
    public Message updateMessage(int messageId, Message message){
        String messageText = message.getMessageText();        
        boolean isMessageBlank = true;        
        if(messageText != null && messageText.length() > 0 && messageText.length() <= 255)
            isMessageBlank = false;
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if(optionalMessage.isPresent() && !isMessageBlank){
            Message toBeUpdatedMessage = optionalMessage.get();
            toBeUpdatedMessage.setMessageText(messageText);
            return messageRepository.save(toBeUpdatedMessage);
        }        
        return null;
    }
    /**
     * Use case 8: Get All Messages From User Given Account Id
     * @param postedBy
     * @return List of Message objects
     */
    public List<Message> getMessagesPostedBy(int postedBy){ 
        return messageRepository.findByPostedBy(postedBy);
    }

}
