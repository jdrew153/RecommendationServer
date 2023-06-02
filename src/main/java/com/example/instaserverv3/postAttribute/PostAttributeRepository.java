package com.example.instaserverv3.postAttribute;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostAttributeRepository extends JpaRepository<PostAttribute, UUID> {

    Optional<List<PostAttribute>> findPostAttributeByPostId(UUID postId);

    Optional<List<PostAttribute>> findPostAttributeByAttributeOrderByRankingDesc(String attribute);
}
