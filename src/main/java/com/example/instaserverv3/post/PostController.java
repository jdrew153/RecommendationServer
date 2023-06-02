package com.example.instaserverv3.post;

import com.example.instaserverv3.like.LikeRelationship;
import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @PostMapping("/create-new-post")
    public ResponseEntity createNewPost(@RequestBody PostDTO request) {
        Boolean result = postService.createNewPost(request);

        if (!result) {
            return ResponseEntity.internalServerError().body("could not create post..");
        } else {
            return ResponseEntity.status(201).body("post successfully created");
        }
    }

    @PostMapping("/find-users-posts")
    public ResponseEntity findUsersPosts(@RequestBody User request) {

        if (String.valueOf(request.getId()).length() == 0) {
            return ResponseEntity.status(500).body("no user id was passed, thus cannot complete request");
        }

        List<Post> returnPosts = postService.findPostsByUserId(request);

        return ResponseEntity.ok().body(returnPosts);
    }

    @PostMapping("/execute-like")
    public ResponseEntity executeLike(@RequestBody LikeRelationship request) throws ResponseStatusException {
        String result = postService.executeLikeForPost(request.getUserId(), request.getPostId());

        if (result.length() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to execute like");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/retrieve-post-by-id")
    public ResponseEntity retrievePostByIdHandler(@RequestBody Post request) {
        System.out.println(request.getPostId());
        PostUserDTO post = postService.retrievePostById(request.getPostId());

        return ResponseEntity.ok().body(post);
    }
}
