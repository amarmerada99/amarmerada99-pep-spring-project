package com.example.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{
    Message findMessageByMessageId(int messageId);
    List<Message> findAllByPostedBy(int accountId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Message m where m.messageId = ?1")
    int deleteMessageByMessageId(int messageId);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = ?1 WHERE m.messageId = ?2")
    int updateMessageByMessageId(String messageText, int messageId);
    
}
