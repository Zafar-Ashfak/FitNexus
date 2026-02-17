package com.fitness.fitnexus.services;

import com.fitness.fitnexus.model.User;
import com.fitness.fitnexus.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {
        return this.userRepository.save(user);
    }
}
