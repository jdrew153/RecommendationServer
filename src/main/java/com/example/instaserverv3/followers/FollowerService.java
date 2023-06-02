package com.example.instaserverv3.followers;


import com.example.instaserverv3.post.Post;
import com.example.instaserverv3.post.PostDTO;
import com.example.instaserverv3.post.PostRepository;
import com.example.instaserverv3.post.PostUserDTO;
import com.example.instaserverv3.user.User;
import com.example.instaserverv3.user.UserRepository;
import com.example.instaserverv3.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowerService {

    private final FollowerRepository followerRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public UUID followNewUser(UUID userIdA, UUID userIdB) throws RuntimeException {
            Follower newFollowerRelationship = Follower.builder()
                    .relationshipId(UUID.randomUUID())
                    .userIdA(userIdA)
                    .userIdB(userIdB)
                    .timeStamp(new Timestamp(System.currentTimeMillis()))
                    .build();

                followerRepository.save(newFollowerRelationship);
                userRepository.updateFollowerCountForUser(userIdB);
                userRepository.updateFollowingCountForUser(userIdA);

                return newFollowerRelationship.getRelationshipId();
    }

    @Transactional
    public void destroyFollowRelationship(UUID userIdA, UUID userIdB) {
        followerRepository.deleteFollowersByUserIdBAndUserIdA(userIdB, userIdA);
        userRepository.decrementFollowerCountForUser(userIdB);
        userRepository.decrementFollowingCountForUser(userIdA);
    }


    public List<PostUserDTO> findFollowedUsersPosts(UUID userId) {
        List<PostUserDTO> returnPosts = new ArrayList<>();

        List<Follower> usersFollowers = this.findUsersFollowers(userId);

        ModelMapper modelMapper = new ModelMapper();

        usersFollowers.forEach(follower -> {
            List<Post> followedUsersPosts = postRepository.findPostByPosterId(follower.getUserIdB())
                    .orElseThrow(() -> new RuntimeException("no posts were found for that user.."));
            User user = userService.loadUserById(follower.getUserIdB().toString());

            followedUsersPosts.forEach(post -> {
                PostUserDTO postDTO = PostUserDTO.builder()
                        .post(post)
                        .user(user)
                        .build();
                returnPosts.add(postDTO);
            });
        });

         returnPosts.sort(Comparator.comparing(post -> post.post.getPostedAt()));

         return returnPosts;
    }

    public List<Follower> findUsersFollowers(UUID userId) throws RuntimeException {
        return followerRepository.findFollowerByUserIdA(userId)
                .orElseThrow(() -> new RuntimeException("no followers could be found"));
    }

}
