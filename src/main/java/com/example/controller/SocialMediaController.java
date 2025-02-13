package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
   public AccountService accountService;
   @Autowired
   public MessageService messageService;

   @PostMapping(value = "/register")
   public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account){
        if(account.getUsername().length() >= 1){
            if(account.getPassword().length() >= 4){
                if(accountService.getAccountByUsername(account.getUsername()) == null){
                    accountService.addAccount(account);
                    return ResponseEntity.status(200).body(account);
                }
                return ResponseEntity.status(409).body(new Account());
            }
        }
        return ResponseEntity.status(400).body(new Account());
   }

   @PostMapping(value = "/login")
   public @ResponseBody ResponseEntity<Account> login(@RequestBody Account account){
        Account hold = accountService.getAccountByUsername(account.getUsername());
        if(hold != null){
            if(hold.getPassword().equals(account.getPassword())){
                return ResponseEntity.status(200).body(hold);
            }
        }
        return ResponseEntity.status(401).body(new Account());
   }

   @PostMapping(value = "/messages")
   public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message){
        if(message.getMessageText() != null && message.getMessageText()!=""){
            if(message.getMessageText().length() < 256){
                if(accountService.getAccountById(message.getPostedBy())!= null){
                    Message m = messageService.addMessage(message);
                    return ResponseEntity.status(200).body(m);
                }
            }
        }
        return ResponseEntity.status(400).body(new Message());
   }

   @GetMapping("/messages")
   public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messages = new ArrayList<Message>();
        messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
   }

   @GetMapping("/accounts/{accountId}/messages")
   public ResponseEntity<List<Message>> getAllMessagesByUser(@PathVariable int accountId){
        return ResponseEntity.status(200).body(messageService.getAllMessagesByAccountId(accountId));
   }
   

   @GetMapping(value = "/messages/{messageId}")
   public ResponseEntity<Message> getMessageById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(messageService.getMessageById(messageId));
   }
   
   @DeleteMapping(value = "/messages/{messageId}")
   public ResponseEntity<Integer> deleteMessageById(@PathVariable int messageId){
    int deleted = messageService.deleteMessageById(messageId);
    if(deleted == 0){
        return ResponseEntity.status(200).build();
    }else{
        return ResponseEntity.status(200).body(deleted);
    }
   }

   @PatchMapping(value = "/messages/{messageId}")
   public @ResponseBody ResponseEntity<Integer> updateMessageById(@PathVariable int messageId, @RequestBody Message messageText){
    
    if(!messageText.getMessageText().isEmpty()){
        if(messageService.getMessageById(messageId) != null){
            if(messageText.getMessageText().length() > 0){
                 if(messageText.getMessageText().length() < 256){
                    return ResponseEntity.status(200).body(messageService.updateMessageById(messageId, messageText.getMessageText()));
                }
            }
        }
    }
    
    return ResponseEntity.status(400).body(0);
   }
}
