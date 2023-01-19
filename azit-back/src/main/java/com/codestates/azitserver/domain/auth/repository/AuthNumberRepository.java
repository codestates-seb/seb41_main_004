package com.codestates.azitserver.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codestates.azitserver.domain.auth.entity.AuthNumber;

public interface AuthNumberRepository extends JpaRepository<AuthNumber, Long> {
	Optional<AuthNumber> findByEmail(String email);
}
