package com.example.instaserverv3.conversations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationService {

    private final ConversationRepository conversationRepository;

    public UUID startNewConversation(UUID userIdA, UUID userIdB) {
        Optional<Conversation> existingConvo = conversationRepository.findConversationByUserIdAAndUserIdB(userIdA, userIdB);
        Optional<Conversation> existingConvo2 = conversationRepository.findConversationByUserIdAAndUserIdB(userIdB, userIdA);

        if (existingConvo.isEmpty() && existingConvo2.isEmpty()) {
            Conversation newConvo = Conversation.builder()
                    .conversationId(UUID.randomUUID())
                    .userIdA(userIdA)
                    .userIdB(userIdB)
                    .build();
            conversationRepository.save(newConvo);

            return newConvo.getConversationId();
        }

        if (existingConvo.isEmpty()) {
            return existingConvo2.get().conversationId;
        } else {
            return existingConvo.get().conversationId;
        }
    }


    public List<Conversation> getConversationsForUser(UUID userId) {
        List<Conversation> convoList1 = conversationRepository.findConversationByUserIdA(userId);
        List<Conversation> convoList2 = conversationRepository.findConversationByUserIdB(userId);

        if (convoList1.isEmpty()) {
            return convoList2;
        } else {
            return convoList1;
        }
    }
}
