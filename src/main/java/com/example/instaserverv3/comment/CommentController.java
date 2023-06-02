package com.example.instaserverv3.comment;


import com.example.instaserverv3.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @PostMapping("/write-new-post-comment")
    public ResponseEntity writeNewCommentHandler(@RequestBody Comment request) {

        return ResponseEntity.ok()
                .body(commentService.writeNewComment(request.getUserId(), request.getProfilePicUrl(), request.getPostId(), request.getComment()));
    }

    @PostMapping("/retrieve-comments-for-post")
    public ResponseEntity retrieveCommentsForPost(@RequestBody Post request) {
        return ResponseEntity.ok()
                .body(commentService.findCommentsByPost(request.getPostId()));
    }

    @PostMapping("/page-comments-for-post")
    public ResponseEntity pageCommentsForPost(@RequestBody CommentPageDTO request) {
        return ResponseEntity.ok().body(commentService.paginateCommentList(request.postId, request.page));
    }
}
