package com.example.instaserverv3.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findUserByUsername(String username);


    @Modifying
    @Query(value = "UPDATE _users SET followers = followers + 1 WHERE id = :id ", nativeQuery = true)
    void updateFollowerCountForUser(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE _users SET following = following + 1 WHERE id = :id ", nativeQuery = true)
    void updateFollowingCountForUser(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE _users SET followers = followers - 1 WHERE id = :id ", nativeQuery = true)
    void decrementFollowerCountForUser(@Param("id") UUID id);

    @Modifying
    @Query(value = "UPDATE _users SET following = following - 1 WHERE id = :id ", nativeQuery = true)
    void decrementFollowingCountForUser(@Param("id") UUID id);

    @Query(value = "SELECT * from _users where to_tsvector(username) @@ to_tsquery(:input)", nativeQuery = true)
    Optional<List<User>> searchUserUsername(@Param("input") String input);



}
