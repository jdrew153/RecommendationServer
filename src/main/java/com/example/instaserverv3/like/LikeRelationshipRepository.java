package com.example.instaserverv3.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LikeRelationshipRepository extends JpaRepository<LikeRelationship, UUID> {

    Optional<List<LikeRelationship>> findLikeRelationshipByUserId(UUID userId);
}
