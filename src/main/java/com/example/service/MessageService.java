package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import java.util.List;

import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message addMessage(Message message){
        return messageRepository.save(message);
    }

    public Message getMessageById(int messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }

    public int deleteMessageById(int messageId){
        return messageRepository.deleteMessageByMessageId(messageId);
    }

    public int updateMessageById(int messageId, String messageText){
        return messageRepository.updateMessageByMessageId(messageText, messageId);
    }
    
    public List<Message> getAllMessagesByAccountId(int accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }
}
