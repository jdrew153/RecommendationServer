package com.example.instaserverv3.like;

import com.example.instaserverv3.post.PostRepository;
import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.postAttribute.PostAttributeRepository;
import com.example.instaserverv3.postAttribute.PostAttributeService;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRanking;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeRelationshipService {
    private final LikeRelationshipRepository likeRelationshipRepository;
    private final UserAttributeRankingService userAttributeRankingService;
    private final PostAttributeService postAttributeService;
    private final PostRepository postRepository;

    @Transactional
    public UUID createLikeRelationship(UUID userId, UUID postId) throws RuntimeException {
        LikeRelationship newRel = LikeRelationship.builder()
                .id(UUID.randomUUID())
                .postId(postId)
                .userId(userId)
                .build();
        LikeRelationship savedEntity = likeRelationshipRepository.save(newRel);

        if (savedEntity.getId().toString().length() == 0) {
            throw new RuntimeException("failed to save relationship");
        }

        List<PostAttribute> attributeList = postAttributeService.findListOfAttributesByPost(postId);
        // updating for existing attributes
        userAttributeRankingService.updateUsersRankingForAttribute(userId, attributeList);

        // update for unique attribute
        List<UserAttributeRanking> userAttributeRankingList = userAttributeRankingService.findRankingForExistingAttributeForUser(String.valueOf(userId));
        Map<String, Integer> userAttributeMap = new HashMap<>();
        for (int i =0; i < userAttributeRankingList.size(); i++) {
            userAttributeMap.put(userAttributeRankingList.get(i).getAttribute(), i);
        }
        System.out.println(userAttributeMap);
        attributeList.forEach(a -> {

            if (userAttributeMap.get(a.getAttribute()) == null) {
                System.out.println(a.getAttribute());
                UserAttributeRanking newRankingForUser =
                        UserAttributeRanking.builder()
                                .userId(userId)
                                .ranking(1)
                                .userAttributeRankingId(UUID.randomUUID())
                                .attribute(a.getAttribute())
                                .build();
                userAttributeRankingService.addNewUserAttributeRanking(newRankingForUser);
            } else {
                System.out.printf("already existing attribute %s\n", a.getAttribute());
            }
        });


        return savedEntity.getId();
    }

    public List<LikeRelationship> findLikeRelationshipsForUser(UUID userId) throws RuntimeException {
        return likeRelationshipRepository.findLikeRelationshipByUserId(userId).orElseThrow(() -> new RuntimeException("no relationships found for user..."));
    }
}
