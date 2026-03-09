package com.social.application.Services;

import com.social.application.Models.Message;
import com.social.application.Models.User;
import com.social.application.Repositories.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageService {
    private final MessageRepository messageRepository;


    @PersistenceContext
    EntityManager entityManager;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;

    }

    public Message insertMessage(Message message){
        long senderId = message.getSender().getId();
        long receiverId = message.getReceiver().getId();

        User senderRef = entityManager.getReference(User.class,senderId);
        User receiverRef = entityManager.getReference(User.class,receiverId);

        message.setSender(senderRef);
        message.setReceiver(receiverRef);
        return messageRepository.save(message);
    }

    public List<Message> fetchAllMessage(){
        return messageRepository.findAll();
    }

    public Message fetchMessageById(Long id){
        return messageRepository.findById(id).orElse(null);
    }
}
