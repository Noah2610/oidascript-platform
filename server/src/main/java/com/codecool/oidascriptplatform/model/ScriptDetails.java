package com.codecool.oidascriptplatform.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "script_details")
public class ScriptDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    @Nullable
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
