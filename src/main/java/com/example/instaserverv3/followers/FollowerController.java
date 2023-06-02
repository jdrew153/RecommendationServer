package com.example.instaserverv3.followers;


import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class FollowerController {

    private final FollowerService followerService;

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @PostMapping("/create-new-follow-relationship")
    public ResponseEntity createNewFollowRelationship(@RequestBody Follower request) throws ResponseStatusException {
        UUID result = followerService.followNewUser(request.getUserIdA(), request.getUserIdB());

        if (result.toString().length() == 0) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to create relationship");
        }

        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/destroy-follow-relationship")
    public ResponseEntity destroyFollowRelationshipHandler(@RequestBody Follower request) {

        followerService.destroyFollowRelationship(request.getUserIdA(), request.getUserIdB());


        return ResponseEntity.noContent().build();
    }


    @PostMapping("/find-followers-posts")
    public ResponseEntity findFollowersPostsHandler(@RequestBody User request) throws ResponseStatusException {

        return ResponseEntity.ok().body(followerService.findFollowedUsersPosts(UUID.fromString(request.getId())));
    }

}
