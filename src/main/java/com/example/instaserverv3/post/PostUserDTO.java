package com.example.instaserverv3.post;

import com.example.instaserverv3.user.User;
import lombok.Builder;


@Builder
public class PostUserDTO {

    public Post post;
    public User user;
}
