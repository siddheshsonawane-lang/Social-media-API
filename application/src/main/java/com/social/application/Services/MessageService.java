package com.social.application.Services;

import com.social.application.DTOs.MessageDTO;
import com.social.application.Models.Message;
import com.social.application.Models.User;
import com.social.application.Repositories.MessageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.Comparator;
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

    public Message fetchMessageById(long id) {
        return messageRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Message not found with id: " + id
                        )
                );
    }


    public List<MessageDTO> getChat(long user1, long user2) {

        List<Message> aToB =
                messageRepository.findBySender_IdAndReceiver_IdOrderByCreatedAtAsc(user1, user2);

        List<Message> bToA =
                messageRepository.findBySender_IdAndReceiver_IdOrderByCreatedAtAsc(user2, user1);

        List<Message> all = new ArrayList<>();
        all.addAll(aToB);
        all.addAll(bToA);

        all.sort(Comparator.comparing(Message::getCreatedAt));

        return all.stream()
                .map(m -> new MessageDTO(
                        m.getSender().getUsername(),
                        m.getContent()
                ))
                .toList();
    }
}
