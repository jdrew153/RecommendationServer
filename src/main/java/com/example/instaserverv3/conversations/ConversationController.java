package com.example.instaserverv3.conversations;

import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping("/conversations/find-users-conversations")
    public ResponseEntity findUsersConversationsHandler(@RequestBody User request) {
        return ResponseEntity.ok().body(conversationService.getConversationsForUser(UUID.fromString(request.getId())));
    }
}
