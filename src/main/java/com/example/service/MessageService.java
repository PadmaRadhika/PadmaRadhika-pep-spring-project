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
    public MessageService(MessageRepository MessageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    //Use case: 3 Create New Message
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

    //Use Case:4, Get All Messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    //Use Case: 5, Get One Message, given Message id
    public Message getMessageById(int id){
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent())
            return optionalMessage.get();
        return null;
    }

    //Use Case: 6, Delete a message, given Message id
    public Message deleteMessage(int messageId){
        Message existingMessage = getMessageById(messageId);
        if(existingMessage != null){
            accountRepository.deleteById(existingMessage.getPostedBy());
            messageRepository.deleteById(messageId);
        }
        if(getMessageById(messageId) == null)
            return existingMessage;
        return null;
    }

    //Use Case: 7, Update Message given messageid
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
}
