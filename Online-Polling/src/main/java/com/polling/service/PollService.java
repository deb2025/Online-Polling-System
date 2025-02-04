package com.polling.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public Poll createPoll(Poll poll, String creatorUsername) {
        // Ensure the creator exists
        User creator = userRepository.findByUsername(creatorUsername)
                                     .orElseThrow(() -> new RuntimeException("Creator not found"));

        poll.setCreator(creator); // Set the poll creator

        // ✅ Ensure options are not null
        if (poll.getOptions() == null || poll.getOptions().isEmpty()) {
            throw new RuntimeException("Poll must have at least one option");
        }

        return pollRepository.save(poll);  // ✅ Directly save poll with options
    }


    @Transactional
    public Poll updatePoll(Long pollId, Poll updatedPoll) {
        Poll existingPoll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        // Update fields
        existingPoll.setTitle(updatedPoll.getTitle());
        existingPoll.setDescription(updatedPoll.getDescription());

        // ✅ Remove old options and add new options properly
        existingPoll.getOptions().clear();
        existingPoll.getOptions().addAll(updatedPoll.getOptions());

        return pollRepository.save(existingPoll);
    }


    @Autowired
    private VoteRepository voteRepository;
    
    public Map<String, Object> getPollStatistics(Long pollId) {
        // Find the poll
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        // Get total votes per option
        Map<String, Long> voteCounts = new HashMap<>();
        for (String option : poll.getOptions()) {
            Long count = voteRepository.countByPollAndSelectedOption(poll, option);
            voteCounts.put(option, count);
        }

        // Create statistics response
        Map<String, Object> stats = new HashMap<>();
        stats.put("pollTitle", poll.getTitle());
        stats.put("pollDescription", poll.getDescription());
        stats.put("totalVotes", voteRepository.countByPoll(poll));
        stats.put("voteBreakdown", voteCounts);

        return stats;
    }


    public void deletePoll(Long id) {
        pollRepository.deleteById(id);
    }
}