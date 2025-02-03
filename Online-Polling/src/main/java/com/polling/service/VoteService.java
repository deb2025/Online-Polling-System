package com.polling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.polling.model.Poll;
import com.polling.model.User;
import com.polling.model.Vote;
import com.polling.repository.PollRepository;
import com.polling.repository.UserRepository;
import com.polling.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private UserRepository userRepository;

    public String castVote(String username, Long pollId, String selectedOption) {
        // Fetch user from database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch poll
        Poll poll = pollRepository.findById(pollId)
                .orElseThrow(() -> new RuntimeException("Poll not found"));

        // Validate option
        if (!poll.getOptions().contains(selectedOption)) {
            throw new RuntimeException("Invalid option selected");
        }

        // Check if user has already voted
        if (voteRepository.existsByUserAndPoll(user, poll)) {
            return "You have already voted in this poll!";
        }

        // Save vote
        Vote vote = new Vote();
        vote.setUser(user);
        vote.setPoll(poll);
        vote.setSelectedOption(selectedOption);
        voteRepository.save(vote);

        return "Vote cast successfully!";
    }
}
