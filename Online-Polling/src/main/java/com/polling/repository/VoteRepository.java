package com.polling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.polling.model.Poll;
import com.polling.model.User;
import com.polling.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserAndPoll(User user, Poll poll);  // Ensure this uses your entity User
}

