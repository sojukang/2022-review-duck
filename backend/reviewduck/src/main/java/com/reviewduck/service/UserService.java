package com.reviewduck.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reviewduck.domain.User;
import com.reviewduck.repository.UserRepository;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long save(String nickname) {
        return userRepository.save(new User(nickname)).getId();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
            () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }
}
