package com.example.instaserverv3.post;

import com.example.instaserverv3.like.LikeRelationshipService;
import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.postAttribute.PostAttributeService;
import com.example.instaserverv3.user.User;
import com.example.instaserverv3.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostAttributeService postAttributeService;
    private final LikeRelationshipService likeRelationshipService;
    private final UserRepository userRepository;

    public Boolean createNewPost(PostDTO newPost) throws RuntimeException {
        UUID postId = UUID.randomUUID();
        Post postToBeSaved = Post.builder()
                .posterId(newPost.post.getPosterId())
                .url(newPost.post.getUrl())
                .postId(postId)
                .caption(newPost.post.getCaption())
                .postedAt(new Timestamp(System.currentTimeMillis()))
                .likes(0)
                .build();
        Post savedPost = postRepository.save(postToBeSaved);

        List<HashMap<String, Integer>> attributes = newPost.postAttributesAndRankings;
        if (attributes.size() == 0) { return false; }
        for (int i = 0; i < attributes.size(); i ++ ){
            System.out.println(attributes);
            HashMap<String, Integer> attributesAndRankings = attributes.get(i);
            attributesAndRankings.forEach((key, value) -> {
                PostAttribute newPostAttribute = postAttributeService.createNewPostAttribute(savedPost.getPostId(), key, value);
                System.out.println(newPostAttribute);
            });
        }

        return String.valueOf(savedPost.getPostId()).length() > 0;
    }

    public PostUserDTO findPostByPostId(UUID postId) throws RuntimeException {

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("no post could be found"));
        User user = userRepository.findById(post.getPosterId()).orElseThrow(() -> new RuntimeException("the user could not be found"));

        return PostUserDTO.builder()
                .post(post)
                .user(user)
                .build();
    }

    public List<Post> findPostsByUserId(User user) throws RuntimeException {
        return postRepository.findPostByPosterId(UUID.fromString(user.getId())).orElseThrow(() -> new RuntimeException("failed to find users posts"));
    }

    public String executeLikeForPost(UUID userId, UUID postId) throws RuntimeException {
        UUID result = likeRelationshipService.createLikeRelationship(userId, postId);

        return String.valueOf(result);
    }


    public PostUserDTO retrievePostById(UUID postId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("no post found"));

        User user = userRepository.findById(post.getPosterId()).orElseThrow(() -> new RuntimeException("no user found..."));

        return PostUserDTO.builder()
                .post(post)
                .user(user)
                .build();
    }
}
