package com.polling.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polling.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @PostMapping("/cast/{poll_id}")
    public ResponseEntity<String> castVote(
            @PathVariable("poll_id") Long pollId,  // Get poll ID from URL
            @RequestParam("selectedOption") String selectedOption,
            Principal principal  // Get authenticated user
    ) {
        String username = principal.getName(); // Extract the username from the logged-in user
        String result = voteService.castVote(username, pollId, selectedOption);
        return ResponseEntity.ok(result);
    }
}

