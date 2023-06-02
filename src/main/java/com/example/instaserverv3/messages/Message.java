package com.example.instaserverv3.messages;


import com.example.instaserverv3.conversations.Conversation;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @Column(name = "message_id")
    UUID messageId;
    @Column(name = "conversation_id")
    UUID conversationId;
    @Column(name = "user_id_a")
    UUID userIdA;
    @Column(name = "user_id_b")
    UUID userIdB;
    UUID fromUser;
    String message;
    @Column(name = "post_id")
    UUID postId;
    @Column(name = "post_src_url")
    String postSrcUrl;
    @Column(name = "time_stamp")
    Timestamp timeStamp;
    Boolean acknowledged;
    @Column(name = "message_type")
    MessageType messageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", insertable = false, updatable = false)
    @JsonBackReference
    public Conversation conversation;
}
