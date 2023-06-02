package com.example.instaserverv3.userAttributeRanking;

import com.example.instaserverv3.postAttribute.PostAttribute;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserAttributeRankingService {
    private final UserAttributeRankingRepository userAttributeRankingRepository;


    // may want to add a method to get the attributes off a post and place them in the
    // userAttributeRanking table to be used for every post that is uploaded.

    public UserAttributeRanking addNewUserAttributeRanking(UserAttributeRanking ranking) {
        UserAttributeRanking attributeRanking = UserAttributeRanking.builder()
                .userAttributeRankingId(UUID.randomUUID())
                .ranking(ranking.getRanking())
                .attribute(ranking.getAttribute())
                .userId(ranking.getUserId())
                .build();
        return userAttributeRankingRepository.save(attributeRanking);
    }

    public List<UserAttributeRanking> findRankingForExistingAttributeForUser(String userId) throws RuntimeException {
        return userAttributeRankingRepository
                .findUserAttributeRankingByUserId(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("could not find users attribute rankings"));

    }

    public void updateUsersRankingForAttribute(UUID userId, List<PostAttribute> attributeList) {
        attributeList.forEach(a -> {
            userAttributeRankingRepository.updateAttributeRankingForUser(0.2F, userId, a.getAttribute());
        });
    }

    public Optional<List<UserAttributeRanking>> findUsersTopAttributeRankings(UUID userId) {
        return userAttributeRankingRepository.findUserAttributeRankingByUserIdOrderByRankingDesc(userId);
    }
}
