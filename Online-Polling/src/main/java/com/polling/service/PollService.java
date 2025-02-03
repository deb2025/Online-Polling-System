package com.polling.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.polling.model.Poll;
import com.polling.model.User;
import com.polling.model.Vote;
import com.polling.model.VoteRequest;
import com.polling.repository.PollRepository;
import com.polling.repository.UserRepository;
import com.polling.repository.VoteRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;
    
    @Autowired
    private UserRepository userRepository;
    

    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    @Transactional  // ✅ Ensure transaction handling
    public Poll createPoll(Poll poll, String creatorUsername) {
        // Ensure the creator exists
        User creator = userRepository.findByUsername(creatorUsername)
                                     .orElseThrow(() -> new RuntimeException("Creator not found"));

        poll.setCreator(creator); // Set the poll creator

        // ✅ First, save the poll without options
        poll.setOptions(Collections.emptyList());  // Save initially without options
        Poll savedPoll = pollRepository.save(poll);

        // ✅ Now add options and save again
        savedPoll.setOptions(poll.getOptions());
        return pollRepository.save(savedPoll);  // Save again with options
    }


    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}