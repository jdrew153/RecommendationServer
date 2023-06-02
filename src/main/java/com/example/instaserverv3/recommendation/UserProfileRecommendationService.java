package com.example.instaserverv3.recommendation;

import com.example.instaserverv3.userAttributeRanking.UserAttributeRanking;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileRecommendationService {
    private final UserProfileRecommendationRepository userProfileRecommendationRepository;
    private final UserAttributeRankingService userAttributeRankingService;
    private final EuclideanDistanceService euclideanDistanceService;

    public UserProfileRecommendation CalculateUserProfileSimilarity(UUID userId, UUID userIdB) {
        // essentially need two vecs of floats (rankings) to calc euclidean distance.
        // first step is to create HashMaps of the users preferences and rankings
        // the hash map will be used to ensure that rankings between each user match up

        HashMap<String, Float> userIdMap = new HashMap<>();
        List<UserAttributeRanking> user1Rankings = userAttributeRankingService.findRankingForExistingAttributeForUser(String.valueOf(userId));
        user1Rankings.forEach(r -> {
            userIdMap.put(r.getAttribute(), Float.valueOf(r.getRanking()));
        });

        HashMap<String, Float> userIdBMap = new HashMap<>();
        List<UserAttributeRanking> user2Rankings = userAttributeRankingService.findRankingForExistingAttributeForUser(String.valueOf(userIdB));
        user2Rankings.forEach(r -> {
            userIdBMap.put(r.getAttribute(), Float.valueOf(r.getRanking()));
        });

        // Next create the ranking vecs, user1 is being compared to user2

        float[] user1Vec =  new float[userIdBMap.size()];
        float[] user2Vec =  new float[userIdBMap.size()];
        final int[] count = {0};
        userIdBMap.forEach((key, value) -> {
            if (userIdMap.get(key) != null) {
                user1Vec[count[0]] = userIdMap.get(key);
                user2Vec[count[0]] = value;
            } else {
                user1Vec[count[0]] = (0.0F);
                user2Vec[count[0]] = value;
            }
            count[0]++;
        });

        float result = euclideanDistanceService.euclideanDistance(user1Vec, user2Vec);

        System.out.println(result);

        UserProfileRecommendation recommendation = UserProfileRecommendation.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .userIdB(userIdB)
                .similarity(result)
                .build();

        userProfileRecommendationRepository.save(recommendation);

        return recommendation;
    }
}
