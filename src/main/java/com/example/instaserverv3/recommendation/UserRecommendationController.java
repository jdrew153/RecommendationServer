package com.example.instaserverv3.recommendation;

import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserRecommendationController {

    private final UserRecommendationService userRecommendationService;
    private final UserProfileRecommendationService userProfileRecommendationService;

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @PostMapping("/create-user-recommendation")
    public ResponseEntity createUserRecommendation(@RequestBody UserRecommendation request) {

        UserRecommendation newRec =  userRecommendationService.createUserRecommendation(request.getUserId(), request.getPostId());
        return ResponseEntity.ok().body(newRec);
    }

    @PostMapping("/get-users-recommended-posts")
    public ResponseEntity fetchUserRecommendations(@RequestBody User request) {
        System.out.println(request.getId());
        return ResponseEntity.ok().body(userRecommendationService.fetchUsersRecommendedPosts(UUID.fromString(request.getId())));
    }

    @PostMapping("/fetch-recommended-post-feed")
    public ResponseEntity fetchRecommendedPosts(@RequestBody User request ) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.SET_COOKIE, "test=titties httpOnly; Secure; SameSite=none;");
        return  ResponseEntity.ok().headers(header).body(userRecommendationService.fetchActualPostsForRecommendedPosts(UUID.fromString(request.getId())));
    }

    @PostMapping("/calc-user-profile-similarity")
    public ResponseEntity calculateUserProfileSimilarity(@RequestBody UserProfileRecommendation request) {
        return ResponseEntity.status(201).body(userProfileRecommendationService.CalculateUserProfileSimilarity(request.getUserId(), request.getUserIdB()));
    }

    @PostMapping("/create-a-bunch-of-recommendations")
    public ResponseEntity createUserRecommendationsHandler(@RequestBody UserRecommendation request) {
        boolean status = userRecommendationService.createUserRecommendations(request.getUserId().toString());

        return ResponseEntity.ok().body(status);
    }
}
