package com.ecommerce.userservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private String roles;
    private String phone;
    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt=Instant.now();
        updatedAt=createdAt;
    }
    @PreUpdate
    public void preUpdate(){
        updatedAt=Instant.now();
    }


}
