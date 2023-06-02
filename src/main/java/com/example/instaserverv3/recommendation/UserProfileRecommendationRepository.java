package com.example.instaserverv3.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserProfileRecommendationRepository extends JpaRepository<UserProfileRecommendation, UUID> {
}
