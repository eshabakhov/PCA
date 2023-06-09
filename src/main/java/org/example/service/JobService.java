package org.example.service;

import lombok.AllArgsConstructor;
import org.example.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JobService {

    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 3 * 60 *1000)
    public void resetLocks(){
        userRepository.resetLocks();
    }

}
