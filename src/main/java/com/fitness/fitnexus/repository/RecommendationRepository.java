package com.fitness.fitnexus.repository;

import com.fitness.fitnexus.DTO.RecommendationResponse;
import com.fitness.fitnexus.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, String> {
    public List<Recommendation> findByUserId(String userId);

    public List<Recommendation> findByActivityId(String activityId);
}
