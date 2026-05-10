package com.maks.subscriptionsystem.repository;

import com.maks.subscriptionsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT u FROM User u
        WHERE (:email IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', CAST(:email AS string), '%')))
        AND (:firstName IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', CAST(:firstName AS string), '%')))
        AND (:lastName IS NULL OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', CAST(:lastName AS string), '%')))
    """)
    Page<User> findAllBy(
            @Param("email") String email,
            @Param("firstName") String firstName,
            @Param("lastName")String lastName,
            Pageable pageable
    );
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
