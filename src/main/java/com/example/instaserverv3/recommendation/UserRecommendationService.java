package com.example.instaserverv3.recommendation;

import com.example.instaserverv3.post.Post;
import com.example.instaserverv3.post.PostRepository;
import com.example.instaserverv3.post.PostService;
import com.example.instaserverv3.post.PostUserDTO;
import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.postAttribute.PostAttributeService;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRanking;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserRecommendationService {

    private final UserRecommendationRepository userRecommendationRepository;
    private final UserAttributeRankingService userAttributeRankingService;
    private final PostAttributeService postAttributeService;
    private final PostService postService;

    private final VectorService vectorService;
    private final PostRepository postRepository;

    public UserRecommendation createUserRecommendation(UUID userId, UUID postId) {

       List<PostAttribute> postAttributeList = postAttributeService.findListOfAttributesByPost(postId);
       postAttributeList.forEach(a -> {
           System.out.println(a.getAttribute());
       });
       List<UserAttributeRanking> userAttributeRankingList = userAttributeRankingService.findRankingForExistingAttributeForUser(String.valueOf(userId));

       // create hashmaps of attribute ranking lists

        HashMap<String, Integer> postAttributeRankingMap = new HashMap<String, Integer>();
        HashMap<String, Integer> userAttributeRankingMap = new HashMap<String, Integer>();

        for (int i =0; i < postAttributeList.size(); i++) {
            postAttributeRankingMap.put(postAttributeList.get(i).getAttribute(), postAttributeList.get(i).getRanking());
        }
        for (int j=0; j < userAttributeRankingList.size(); j++) {
            userAttributeRankingMap.put(userAttributeRankingList.get(j).getAttribute(), userAttributeRankingList.get(j).getRanking());
        }

        System.out.println(userAttributeRankingMap);
        System.out.println(postAttributeRankingMap);

        // create vector of corresponding attributes and rankings
        Set<String> postAttributes = postAttributeRankingMap.keySet();
        Vector<Integer> userAttributeRankingVec = new Vector<>(postAttributeRankingMap.size());
        Vector<Integer> postAttributeRankingVec = new Vector<>(postAttributeRankingMap.size());

        postAttributes.forEach(s -> {
            int postRanking = postAttributeRankingMap.get(s);
            postAttributeRankingVec.add(postRanking);
            boolean check = userAttributeRankingMap.containsKey(s);

            if (check) {
                int ranking = userAttributeRankingMap.get(s);
                userAttributeRankingVec.add(ranking);
            } else {
                userAttributeRankingVec.add(0);
            }

        });

        System.out.println(userAttributeRankingVec);
        System.out.println(postAttributeRankingVec);

        // getting cosine similarity for post using previous vecs
        double similarity = vectorService.cosineSimilarity(userAttributeRankingVec, postAttributeRankingVec);

        UserRecommendation recommendation = UserRecommendation.builder()
                .userRecommendationId(UUID.randomUUID())
                .userId(userId)
                .postId(postId)
                .similarity(similarity)
                .build();
    System.out.println(recommendation.getSimilarity());
        UserRecommendation newRecommendation = userRecommendationRepository.save(recommendation);

        return newRecommendation;
    }

    public List<UserRecommendation> fetchUsersRecommendedPosts(UUID userId) throws RuntimeException {
        return  userRecommendationRepository.findUserRecommendationByUserIdOrderBySimilarityDesc(userId)
                .orElseThrow(() -> new RuntimeException("error finding users recommended list"));
    }

    public List<PostUserDTO> fetchActualPostsForRecommendedPosts(UUID userId) throws RuntimeException{
        List<UserRecommendation> userRecommendationList = this.fetchUsersRecommendedPosts(userId);
        List<PostUserDTO> recommendedPosts = new ArrayList<>();

        userRecommendationList.forEach(post -> {
            PostUserDTO recommendedPost = postService.findPostByPostId(post.getPostId());
            recommendedPosts.add(recommendedPost);
        });

        return recommendedPosts;
    }

    @Transactional
    public boolean createUserRecommendations(String userId) throws RuntimeException {
        System.out.println(userId);
        this.deleteRecommendationsByUserId(UUID.fromString(userId));
        userRecommendationRepository.flush();
        List<Post> allPosts = postRepository.findPostsNotByPosterIdNot(UUID.fromString(userId));
        System.out.println(allPosts);
        allPosts.forEach(post -> {
            System.out.println(userId);
            this.createUserRecommendation(UUID.fromString(userId), post.getPostId());
        });

        return true;
    }

    @Transactional
    public void deleteRecommendationsByUserId(UUID userId) {
        userRecommendationRepository.deleteByUserId(userId);
    }

}
