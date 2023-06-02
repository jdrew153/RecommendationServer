package com.example.instaserverv3.postAttribute;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostAttributeService {
    private final PostAttributeRepository postAttributeRepository;

    public PostAttribute createNewPostAttribute(UUID postId, String attribute, Integer ranking) {
        PostAttribute newPostAttribute = PostAttribute.builder()
                .postAttributeId(UUID.randomUUID())
                .attribute(attribute)
                .ranking(ranking)
                .postId(postId)
                .build();
        return postAttributeRepository.save(newPostAttribute);
    }

    public List<PostAttribute> findListOfAttributesByPost(UUID postId) throws RuntimeException {
        return postAttributeRepository.findPostAttributeByPostId(postId).orElseThrow(() -> new RuntimeException("unable to find attributes for post"));
    }

    public List<PostAttribute> findPostAttributesByAttribute(String attribute) throws RuntimeException {
        return postAttributeRepository.findPostAttributeByAttributeOrderByRankingDesc(attribute).orElseThrow(() -> new RuntimeException("no posts for that attribute"));
    }
}
