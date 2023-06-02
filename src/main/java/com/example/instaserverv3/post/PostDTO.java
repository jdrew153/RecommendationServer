package com.example.instaserverv3.post;

import lombok.Builder;

import java.util.HashMap;
import java.util.List;

@Builder
public class PostDTO {
    public Post post;
    public List<HashMap<String, Integer>> postAttributesAndRankings;
}
