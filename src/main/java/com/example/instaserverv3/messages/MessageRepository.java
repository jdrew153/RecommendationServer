package com.example.instaserverv3.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

     Optional<List<Message>> findMessageByUserIdA(UUID userIdA);

//     @Query(name = "SELECT * FROM messages WHERE user_id_a = :userIdA GROUP BY user_id_b, message, time_stamp, message_type, post_id, message_id, user_id_a, acknowledged order by time_stamp desc", nativeQuery = true)
//     List<Message> searchConversationsForUser(@Param("user_id_a") UUID userIdA);

     List<Message> findMessageByUserIdAOrderByUserIdB(UUID userIdA);
     List<Message> findMessageByUserIdB(UUID userIdB);
}
