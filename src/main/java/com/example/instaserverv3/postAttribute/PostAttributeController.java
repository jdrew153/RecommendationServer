package com.example.instaserverv3.postAttribute;

import com.example.instaserverv3.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class PostAttributeController {
    private final PostAttributeService postAttributeService;

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity handleException(SQLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @PostMapping("/find-attribute-by-postId")
    public ResponseEntity findAttributeByPostIdHandler(@RequestBody Post request) {
        List<PostAttribute> attributeList = postAttributeService.findListOfAttributesByPost(request.getPostId());

        return ResponseEntity.ok().body(attributeList);
    }
}
