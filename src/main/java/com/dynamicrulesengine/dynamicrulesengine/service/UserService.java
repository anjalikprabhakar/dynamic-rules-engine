package com.dynamicrulesengine.dynamicrulesengine.service;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.LoginRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.SignupRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.SignupResponse;
import com.dynamicrulesengine.dynamicrulesengine.exceptions.InvalidLoginException;
import com.dynamicrulesengine.dynamicrulesengine.model.User;
import com.dynamicrulesengine.dynamicrulesengine.repository.UserRepository;
import com.dynamicrulesengine.dynamicrulesengine.security.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public SignupResponse signUp(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Invalid Signup");
        }
        User user =
                User.builder()
                        .userName(request.getName())
                        .email(request.getEmail())
                        .hashedPassword(passwordEncoder.encode(request.getPassword()))
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignupResponse.class);
    }

    public String login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        if (!userRepository.existsByEmail(email)) {
            throw new EntityNotFoundException("Invalid login");
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (passwordEncoder.matches(password, user.getHashedPassword())) {
            String jwtToken = jwtService.generateToken(user);
            return jwtToken;
        }
        throw new InvalidLoginException();
    }
}
