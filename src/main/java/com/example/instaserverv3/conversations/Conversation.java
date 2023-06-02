package com.example.instaserverv3.conversations;

import com.example.instaserverv3.messages.Message;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "conversations")
public class Conversation {
    @Id
    @Column(name = "conversation_id")
    UUID conversationId;

    @Column(name = "user_id_a")
    UUID userIdA;

    @Column(name = "user_id_b")
    UUID userIdB;


    @OneToMany(mappedBy = "conversation", fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Message> messages;
}
