package com.example.instaserverv3.like;


import com.example.instaserverv3.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "like_relationship")
public class LikeRelationship {
    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "post_id")
    private UUID postId;

    @ManyToOne
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    @JsonBackReference
    public Post post;

}
