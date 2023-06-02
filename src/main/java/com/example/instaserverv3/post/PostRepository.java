package com.example.instaserverv3.post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    public Optional<List<Post>> findPostByPosterId(UUID posterId);

    @Query(name = "SELECT * FROM posts WHERE poster_id != ?1", nativeQuery = true)
    public List<Post> findPostsNotByPosterIdNot(UUID posterId);


}
