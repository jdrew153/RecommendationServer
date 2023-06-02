package com.example.instaserverv3.followers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, UUID> {

    Optional<List<Follower>> findFollowerByUserIdA(UUID userIdA);

    void deleteFollowersByUserIdBAndUserIdA(UUID userIdB, UUID userIdA);
}
