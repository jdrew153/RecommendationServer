package com.example.instaserverv3.userAttributeRanking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAttributeRankingRepository extends JpaRepository<UserAttributeRanking, UUID> {

    Optional<List<UserAttributeRanking>> findUserAttributeRankingByUserId(UUID userId);

    @Modifying
    @Query(value = "UPDATE user_attribute_ranking SET ranking = ranking + :value WHERE user_id = :id and attribute = :attribute", nativeQuery = true)
    void updateAttributeRankingForUser(@Param("value") Float value, @Param("id") UUID userId, @Param("attribute") String attribute);

    Optional<List<UserAttributeRanking>> findUserAttributeRankingByUserIdOrderByRankingDesc(UUID userId);

}
