package com.example.instaserverv3.messages;

import com.example.instaserverv3.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@CrossOrigin
@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages/send-message")
    public ResponseEntity sendMessage(@RequestBody Message request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.createAndSendMessage(request));
    }

    @PostMapping("/messages/find-users-messages")
    public ResponseEntity findUsersMessages(@RequestBody User request) {

        return ResponseEntity.ok().body(messageService.getUsersMessages(UUID.fromString(request.getId())));

    }

    @PostMapping("/messages/find-users-conversation-message-map")
    public ResponseEntity findUsersConversationsHandler(@RequestBody User request) {
        System.out.println(request.getId());
        return ResponseEntity.ok().body(messageService.findConversationsService(UUID.fromString(request.getId())));
    }
}
