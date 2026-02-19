package com.fitness.fitnexus.controller;

import com.fitness.fitnexus.DTO.ActivityRequest;
import com.fitness.fitnexus.DTO.ActivityResponse;
import com.fitness.fitnexus.services.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest) {
        ActivityResponse response = this.activityService.trackActivity(activityRequest);
        try {
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.fillInStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(String userId) {
       List<ActivityResponse> activityList = this.activityService.getUserActivities(userId);
       if (activityList.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
       }

       return ResponseEntity.ok(activityList);
    }
}
