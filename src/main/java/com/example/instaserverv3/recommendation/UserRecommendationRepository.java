package com.example.instaserverv3.recommendation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, UUID> {

    Optional<List<UserRecommendation>> findUserRecommendationByUserIdAndPostId(UUID userId, UUID postId);

    Optional<List<UserRecommendation>> findUserRecommendationByUserIdOrderBySimilarityDesc(UUID userId);

    void deleteByUserId(UUID userId);
}
