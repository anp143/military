package com.militaryassetmanagementsystem.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.militaryassetmanagementsystem.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	List<User> findByRole(String role);

}
