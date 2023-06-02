package com.example.instaserverv3.userAttributeRanking;

import com.example.instaserverv3.user.User;
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
@Table(name = "user_attribute_ranking")
public class UserAttributeRanking {
    @Id
    @Column(name = "user_attribute_ranking_id")
    private UUID userAttributeRankingId;
    private UUID userId;
    private String attribute;
    private Integer ranking;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @JsonBackReference
    public User user;
}
