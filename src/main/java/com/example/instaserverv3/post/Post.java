package com.example.instaserverv3.post;


import com.example.instaserverv3.like.LikeRelationship;
import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Post {
    @Id
    @GeneratedValue
    private UUID postId;
    @Column(name = "poster_id")
    private UUID posterId;
    private String url;
    private String caption;
    @GeneratedValue
    private Timestamp postedAt;
    private Integer likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poster_id", insertable = false, updatable = false)
    @JsonBackReference
    public User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<PostAttribute> postAttributes;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<LikeRelationship> likeRelationshipList;
}
