package com.example.instaserverv3.comment;


import com.example.instaserverv3.user.User;
import lombok.Builder;

@Builder
public class CommentUserDTO {
    public Comment comment;

    public User user;
}
