package com.fitness.fitnexus.controller;

import com.fitness.fitnexus.DTO.RecommendationRequest;
import com.fitness.fitnexus.DTO.RecommendationResponse;
import com.fitness.fitnexus.services.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public ResponseEntity<RecommendationResponse> generateRecommendation(@RequestBody RecommendationRequest request) {
       RecommendationResponse response = this.recommendationService.generateRecommendation(request);
       try {
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           e.fillInStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RecommendationResponse>> getUserRecommendations(@RequestParam String userId) {
        List<RecommendationResponse> userRecommendationList = this.recommendationService.getUserRecommendations(userId);
       if (userRecommendationList.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }

       return ResponseEntity.ok(userRecommendationList);
    }

    @GetMapping("/activity")
    public ResponseEntity<List<RecommendationResponse>> getActivityRecommendations(@RequestHeader("X-Activity-ID") String activityId) {
        List<RecommendationResponse> activityRecommendationList = this.recommendationService.getActivityRecommendations(activityId);
        if (activityRecommendationList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(activityRecommendationList);
    }
}
