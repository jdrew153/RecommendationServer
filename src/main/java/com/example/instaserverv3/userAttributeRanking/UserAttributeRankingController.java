package com.example.instaserverv3.userAttributeRanking;

import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserAttributeRankingController {
    private final UserAttributeRankingService userAttributeRankingService;

    @PostMapping("/update-user-attribute-rankings")
    public ResponseEntity getUserAttributeRanking(@RequestBody UserAttributeRanking request) {
        UserAttributeRanking newRanking = userAttributeRankingService.addNewUserAttributeRanking(request);

        if (String.valueOf(newRanking.getUserAttributeRankingId()).length() >0) {
            return ResponseEntity.status(201).body("created new attribute ranking for user");
        } else {
            return ResponseEntity.status(500).body("could not create new attribute ranking for user");
        }
    }

    @PostMapping("/find-user-attribute-ranking-list")
    public ResponseEntity findUserAttributeRankingList(@RequestBody User user) {
        if (user.getId().length() == 0) {
            return ResponseEntity.status(500).body("no user id was provided.");
        }
        List<UserAttributeRanking> returnList = userAttributeRankingService.findRankingForExistingAttributeForUser(user.getId());
        return ResponseEntity.ok().body(returnList);
    }
}
