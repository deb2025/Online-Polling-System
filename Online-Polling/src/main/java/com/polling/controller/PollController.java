package com.polling.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.polling.model.Poll;
import com.polling.model.VoteRequest;
import com.polling.service.PollService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollController {

    @Autowired
    private PollService pollService;

    @GetMapping
    public List<Poll> getAllPolls() {
        return pollService.getAllPolls();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Poll> updatePoll(@PathVariable Long id, @RequestBody Poll updatedPoll) {
        Poll poll = pollService.updatePoll(id, updatedPoll);
        return ResponseEntity.ok(poll);
    }



    @PostMapping("/create")
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll, @RequestParam String creatorUsername) {
        Poll createdPoll = pollService.createPoll(poll, creatorUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPoll);
    }

    @DeleteMapping("/manage/{id}")
    public void deletePoll(@PathVariable Long id) {
        pollService.deletePoll(id);
    }
    @GetMapping("/stats/{id}")
    public ResponseEntity<Map<String, Object>> getPollStatistics(@PathVariable Long id) {
        Map<String, Object> stats = pollService.getPollStatistics(id);
        return ResponseEntity.ok(stats);
    }

}