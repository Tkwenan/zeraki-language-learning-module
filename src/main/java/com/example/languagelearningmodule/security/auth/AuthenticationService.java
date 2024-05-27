package com.example.languagelearningmodule.security.auth;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterRequest input) {
        User user = new User();
        user.setFirstName(input.getFirstName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setUsername(input.getUsername());

        return userRepository.save(user);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserEmail(),
                        authenticationRequest.getUserPassword())
        );

        //if we get here, then the user is authenticated
        Optional<User> possibleUser = userRepository.findByEmail(authenticationRequest.getUserEmail());

        User user;

        if(possibleUser.isPresent()) {
            user = possibleUser.get();
        } else{
            throw new NoResourceFoundException("User not found");
        }

        //var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(authenticationRequest.getUserEmail())
                .build();
    }

}
