package com.fitness.fitnexus.services;

import com.fitness.fitnexus.DTO.RecommendationRequest;
import com.fitness.fitnexus.DTO.RecommendationResponse;
import com.fitness.fitnexus.model.Activity;
import com.fitness.fitnexus.model.Recommendation;
import com.fitness.fitnexus.model.User;
import com.fitness.fitnexus.repository.ActivityRepository;
import com.fitness.fitnexus.repository.RecommendationRepository;
import com.fitness.fitnexus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public RecommendationResponse generateRecommendation(RecommendationRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("Invalid user: " + request.getUserId()));
        Activity activity = activityRepository.findById(request.getActivityId()).orElseThrow(() -> new RuntimeException("Invalid activity: " + request.getActivityId()));

        Recommendation recommendation = Recommendation.builder()
                .user(user)
                .activity(activity)
                .type(request.getType())
                .recommendation(request.getRecommendation())
                .improvements(request.getImprovements())
                .suggestions(request.getSuggestions())
                .safety(request.getSafety())
                .build();


        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return mapToResponse(savedRecommendation);
    }

    private RecommendationResponse mapToResponse(Recommendation savedRecommendation) {
        RecommendationResponse response = new RecommendationResponse();
        response.setId(savedRecommendation.getId());
        response.setUserId(savedRecommendation.getUser().getId());
        response.setActivityId(savedRecommendation.getActivity().getId());
        response.setType(savedRecommendation.getType());
        response.setRecommendation(savedRecommendation.getRecommendation());
        response.setImprovements(savedRecommendation.getImprovements());
        response.setSuggestions(savedRecommendation.getSuggestions());
        response.setSafety(savedRecommendation.getSafety());
        response.setCreatedAt(savedRecommendation.getCreatedAt());
        response.setUpdatedAt(savedRecommendation.getUpdatedAt());
        return response;
    }

    public List<RecommendationResponse> getUserRecommendations(String userId) {
        List<Recommendation> userRecommendationList = this.recommendationRepository.findByUserId(userId);
        return userRecommendationList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }


    public List<RecommendationResponse> getActivityRecommendations(String activityId) {
        List<Recommendation> activityRecommendationList = this.recommendationRepository.findByActivityId(activityId);
        return activityRecommendationList.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
}
