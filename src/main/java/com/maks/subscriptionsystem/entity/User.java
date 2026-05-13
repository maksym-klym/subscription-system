package com.maks.subscriptionsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDateTime createdAt;
}
