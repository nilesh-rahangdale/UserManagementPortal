package com.niyora.UserManagementProject.Repositories;

import com.niyora.UserManagementProject.Entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

//    boolean existByUsername(@NotBlank String username);

    boolean existsByUsername(@NotBlank String username);
}
