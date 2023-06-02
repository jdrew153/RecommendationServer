package com.example.instaserverv3.comment;

import com.example.instaserverv3.postAttribute.PostAttribute;
import com.example.instaserverv3.postAttribute.PostAttributeService;
import com.example.instaserverv3.user.User;

import com.example.instaserverv3.user.UserService;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRanking;
import com.example.instaserverv3.userAttributeRanking.UserAttributeRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostAttributeService postAttributeService;
    private final UserAttributeRankingService userAttributeRankingService;

    @Transactional
    public CommentUserDTO writeNewComment(UUID userId, String profilePic, UUID postId, String comment) throws RuntimeException {
        Comment newComment = Comment.builder()
                .comment(comment)
                .commentId(UUID.randomUUID())
                .postId(postId)
                .userId(userId)
                .profilePicUrl(profilePic)
                .timeStamp(new Timestamp(System.currentTimeMillis()))
                .build();

        if (newComment.getCommentId().toString().length() > 0) {
            commentRepository.save(newComment);

            List<PostAttribute> attributeList = postAttributeService.findListOfAttributesByPost(postId);
            // updating for existing attributes
            userAttributeRankingService.updateUsersRankingForAttribute(userId, attributeList);

            // update for unique attribute
            List<UserAttributeRanking> userAttributeRankingList = userAttributeRankingService
                    .findRankingForExistingAttributeForUser(String.valueOf(userId));
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
            User user = userService.loadUserById(userId.toString());
            CommentUserDTO commentUserDTO = CommentUserDTO.builder()
                    .user(user)
                    .comment(newComment)
                    .build();
            return commentUserDTO;
        } else {
            throw new RuntimeException("failed to create comment..");
        }
    }

    public List<CommentUserDTO> findCommentsByPost(UUID postId) throws RuntimeException {
        List<CommentUserDTO> commentUserDTOList = new ArrayList<>();

        List<Comment> commentList = commentRepository.findCommentByPostId(postId)
                .orElseThrow(() -> new RuntimeException("failed to find comments on post"));

        commentList.forEach(comment -> {
            User user = userService.loadUserById(comment.getUserId().toString());
            CommentUserDTO commentUserDTO = CommentUserDTO.builder()
                    .comment(comment)
                    .user(user)
                    .build();

            commentUserDTOList.add(commentUserDTO);
        });

        commentUserDTOList.sort(Comparator.comparing(c -> c.comment.getTimeStamp()));
        return commentUserDTOList;
    }


    // pagination request
    public List<CommentUserDTO> paginateCommentList(UUID postId, Integer pageNo) throws RuntimeException{
        List<CommentUserDTO> commentUserDTOList = new ArrayList<>();

        Pageable request =  PageRequest.of(pageNo, 20);

        Page<Comment> commentList = commentRepository.findAllByPostId(postId, request);

        commentList.forEach(comment -> {
            User user = userService.loadUserById(comment.getUserId().toString());
            CommentUserDTO commentUserDTO = CommentUserDTO.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentUserDTOList.add(commentUserDTO);
        });

        commentUserDTOList.sort(Comparator.comparing(c -> c.comment.getTimeStamp()));
        return commentUserDTOList;
    }
}
