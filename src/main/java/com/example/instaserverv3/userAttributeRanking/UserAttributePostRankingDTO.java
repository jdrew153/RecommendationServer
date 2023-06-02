package com.example.instaserverv3.userAttributeRanking;

import com.example.instaserverv3.post.Post;
import java.util.Set;

public record UserAttributePostRankingDTO(String topAttribute, Set<Post> post) {
}
