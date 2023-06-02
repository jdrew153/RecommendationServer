package com.example.instaserverv3.user;

import com.example.instaserverv3.like.LikeRelationship;
import com.example.instaserverv3.like.LikeRelationshipService;
import com.example.instaserverv3.post.Post;
import com.example.instaserverv3.post.PostRepository;
import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.postAttribute.PostAttributeService;
import com.example.instaserverv3.userAttributeRanking.UserAttributePostRankingDTO;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRanking;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LikeRelationshipService likeRelationshipService;
    private final PostRepository postRepository;
    private final UserAttributeRankingService userAttributeRankingService;
    private final PostAttributeService postAttributeService;


    public User loadUserById(String userId) throws RuntimeException {
        return userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new RuntimeException("user not found"));
    }

    public List<Post> findUsersLikedPosts(String userId) throws RuntimeException {
        List<LikeRelationship> likeRelationshipList = likeRelationshipService.findLikeRelationshipsForUser(UUID.fromString(userId));

        List<Post> returnPosts = new ArrayList<>();
        likeRelationshipList.forEach(likeRelationship -> {
            Post foundPost = postRepository.findById(likeRelationship.getPostId()).orElseThrow(() -> new RuntimeException("no post with that id found"));
            returnPosts.add(foundPost);
        });

        return returnPosts;
    }

    public Optional<List<User>> searchUser(String username) {
        String searchInput = username + ":*";

        return userRepository.searchUserUsername(searchInput);
    }

    // something to find the users top attributes for the search page of the app. Have like a header of sorts for the top of the row with the tag and posts that match in a view below
    public List<UserAttributePostRankingDTO> getPostsForUserByTopAttributeRanking(UUID userId) throws RuntimeException{
        Optional<List<UserAttributeRanking>> topAttributes = userAttributeRankingService.findUsersTopAttributeRankings(userId);
        List<UserAttributeRanking> safeTopAttributesList = topAttributes.orElseThrow(() -> new RuntimeException("no attributes found for user"));
        List<UserAttributePostRankingDTO> topAttributesPostList = new ArrayList<>();

        safeTopAttributesList.forEach( userAttributeRanking -> {
            List<PostAttribute> attributes = postAttributeService.findPostAttributesByAttribute(userAttributeRanking.getAttribute());

           // group posts by top attribute and map to dto using aggregated list

            Set<Post> postsWithAttribute = new HashSet<>();

            attributes.forEach(attribute -> {

                Post post = postRepository.findById(attribute.getPostId()).orElseThrow(() -> new RuntimeException("no post found..."));
                postsWithAttribute.add(post);
            });

            UserAttributePostRankingDTO dto = new UserAttributePostRankingDTO(userAttributeRanking.getAttribute(), postsWithAttribute);
            topAttributesPostList.add(dto);

        });
        topAttributesPostList.forEach(post -> {
            System.out.println(post.topAttribute());
        });
        return topAttributesPostList;
    }
}
