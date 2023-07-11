package com.apper.theblogservice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
@Entity
@Data
@Table(name = "BLOGGER")
public class Blogger {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "COMPLETE_NAME")
    private String name;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdate;

    @PrePersist
    public void setInitialTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        lastUpdate = now;
    }

    @PreUpdate
    public void setLastUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
