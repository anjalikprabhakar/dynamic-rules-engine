package com.dynamicrulesengine.dynamicrulesengine.controller;

import com.dynamicrulesengine.dynamicrulesengine.contract.request.LoginRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.request.SignupRequest;
import com.dynamicrulesengine.dynamicrulesengine.contract.response.SignupResponse;
import com.dynamicrulesengine.dynamicrulesengine.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignupResponse SignUp(@Valid @RequestBody SignupRequest request) {
        return userService.signUp(request);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
}
