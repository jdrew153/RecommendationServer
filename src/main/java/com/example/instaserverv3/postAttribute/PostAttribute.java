package com.example.instaserverv3.postAttribute;

import com.example.instaserverv3.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "post_attribute")
public class PostAttribute {
    @Id
    @GeneratedValue
    @Column(name = "post_attribute_id")
    private UUID postAttributeId;
    @Column(name = "post_id")
    private UUID postId;
    @Column(name = "attribute")
    private String attribute;

    private Integer ranking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    @JsonBackReference
    private Post post;
}
