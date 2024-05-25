package com.example.languagelearningmodule.security.auth;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import com.example.languagelearningmodule.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private final AuthenticationService authenticationService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserRepository userRepository,  PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerRequest) {
        User registeredUser = authenticationService.signup(registerRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        if (!userRepository.existsByEmail(authenticationRequest.getUserEmail())) {
            throw new NoResourceFoundException("Invalid Authentication Credentials");
        } else {
            Optional<User> possibleUser = userRepository.findByEmail(authenticationRequest.getUserEmail());
            User user = possibleUser.get();

            if (!passwordEncoder.matches(authenticationRequest.getUserPassword(), user.getPassword())) {
                throw new NoResourceFoundException("Invalid Authentication Credentials");
            }
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        }
    }

}
