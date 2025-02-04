package com.polling.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polling.model.Poll;

import jakarta.transaction.Transactional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
	@Transactional
	public Optional<Poll> findById(Long id);
}
