package com.example.instaserverv3.comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "comments")
public class Comment {
    @Id
    private UUID commentId;

    private UUID userId;
    private UUID postId;
    @Column(name = "comment")
    private String comment;

    @Column(name = "profile_pic_url")
    private String profilePicUrl;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

}
