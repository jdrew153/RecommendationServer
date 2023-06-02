package com.example.instaserverv3.recommendation;

import com.example.instaserverv3.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_recommendation")
public class UserRecommendation {
    @Id
    private UUID userRecommendationId;
    private UUID userId;
    private UUID postId;
    private double similarity;



}
