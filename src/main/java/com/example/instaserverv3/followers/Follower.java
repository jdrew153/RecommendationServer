package com.example.instaserverv3.followers;

import com.example.instaserverv3.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "followers")
public class Follower {
    @Id
    private UUID relationshipId;

    @Column(name = "user_id_a")
    private UUID userIdA;

    @Column(name = "user_id_b")
    private UUID userIdB;
    @Column(name = "time_stamp")
    private Timestamp timeStamp;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_a", insertable = false, updatable = false)
    @JsonBackReference
    User user;



}
