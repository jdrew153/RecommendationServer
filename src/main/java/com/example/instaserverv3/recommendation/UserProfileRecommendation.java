package com.example.instaserverv3.recommendation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "user_profile_recommendation")
public class UserProfileRecommendation {
    @Id
    private UUID id;
    private UUID userId;
    @Column(name = "user_id_b")
    private UUID userIdB;
    private Float similarity;
}
