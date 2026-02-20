package com.fitness.fitnexus.services;

import com.fitness.fitnexus.DTO.ActivityRequest;
import com.fitness.fitnexus.DTO.ActivityResponse;
import com.fitness.fitnexus.model.Activity;
import com.fitness.fitnexus.model.User;
import com.fitness.fitnexus.repository.ActivityRepository;
import com.fitness.fitnexus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Invalid user: " + request.getUserId()));
        Activity activity = Activity.builder()
                .user(user)
                .type(request.getType())
                .duration(request.getDuration())
                .additionalMetrics(request.getAdditionalMetrics())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);

    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse response = new ActivityResponse();
        response.setId(savedActivity.getId());
        response.setUserId(savedActivity.getUser().getId());
        response.setType(savedActivity.getType());
        response.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
        response.setDuration(savedActivity.getDuration());
        response.setCaloriesBurned(savedActivity.getCaloriesBurned());
        response.setStartTime(savedActivity.getStartTime());
        response.setCreatedAt(savedActivity.getCreatedAt());
        response.setUpdatedAt(savedActivity.getUpdatedAt());
        return  response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activityList= this.activityRepository.findByUserId(userId);
        return activityList.stream().map(this :: mapToResponse).collect(Collectors.toList());
    }
}
