package com.example.instaserverv3.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Optional<List<Comment>> findCommentByPostId(UUID postId);

    Page<Comment> findAllByPostId(UUID postId, Pageable pageable);

}
