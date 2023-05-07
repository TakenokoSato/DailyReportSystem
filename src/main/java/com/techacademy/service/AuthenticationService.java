package com.techacademy.service;

import org.springframework.stereotype.Service;


import com.techacademy.entity.Authentication;
import com.techacademy.repository.AuthenticationRepository;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository repository) {
        this.authenticationRepository = repository;
    }

    public Authentication findByCode(String code) {
        Optional<Authentication> optionalAuthentication = authenticationRepository.findByCode(code);
        return optionalAuthentication.orElse(null);
    }


}
