package com.example.instaserverv3.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.UUID;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    // Need a user id for this route
    @PostMapping("/find-user")
    public ResponseEntity getUser(@RequestBody User request) {
        User user = userService.loadUserById(String.valueOf(request.getId()));
        if (String.valueOf(user.getId()).length() > 0) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/find-users-liked-posts")
    public ResponseEntity findUsersLikedPostController(@RequestBody User request) throws ResponseStatusException {
        return ResponseEntity.ok().body(userService.findUsersLikedPosts(request.getId()));
    }

    @PostMapping("/search-user")
    public ResponseEntity searchUserHandler(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.searchUser(user.getUsername()));
    }


    // Just need the user id for this route
    @PostMapping("/find-top-attributed-posts-for-user")
    public ResponseEntity findTopAttributedPostsForUSer(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.getPostsForUserByTopAttributeRanking(UUID.fromString(user.getId())));
    }
}
