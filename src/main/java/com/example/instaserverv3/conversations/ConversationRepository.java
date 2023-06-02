package com.example.instaserverv3.conversations;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    Optional<Conversation> findConversationByUserIdAAndUserIdB(UUID userIdA, UUID userIdB);

    List<Conversation> findConversationByUserIdA(UUID userIdA);

    List<Conversation> findConversationByUserIdB(UUID userIdB);
}
