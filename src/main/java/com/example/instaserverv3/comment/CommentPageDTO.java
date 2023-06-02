package com.example.instaserverv3.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Builder
@Getter
@Setter
public class CommentPageDTO {
    public UUID postId;
    public Integer page;
}
