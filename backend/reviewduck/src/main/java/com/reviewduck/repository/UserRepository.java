package com.reviewduck.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reviewduck.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
