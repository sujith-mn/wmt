package com.application.workmanagement.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.workmanagement.domain.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	List<Users> findByUsername(String username);

}
