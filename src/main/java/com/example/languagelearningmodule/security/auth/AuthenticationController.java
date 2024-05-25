package com.example.languagelearningmodule.security.auth;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService service;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.service = authenticationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        if (!userRepository.existsByEmail(authenticationRequest.getUserEmail())) {
            throw new NoResourceFoundException("Invalid Authentication Credentials");
        } else {
            User user = userRepository.findByEmail(authenticationRequest.getUserEmail());

            if (!passwordEncoder.matches(authenticationRequest.getUserPassword(), user.getPassword())) {
                throw new NoResourceFoundException("Invalid Authentication Credentials");
            }
            return ResponseEntity.ok(service.authenticate(authenticationRequest));
        }
    }

}
