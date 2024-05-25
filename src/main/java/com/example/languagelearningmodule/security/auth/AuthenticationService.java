package com.example.languagelearningmodule.security.auth;

import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import com.example.languagelearningmodule.security.jwt.JwtService;
import com.example.languagelearningmodule.security.roles.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository,  RoleRepository roleRepository, ModelMapper modelMapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUserEmail(),
                        authenticationRequest.getUserPassword())
        );

        //if we get here, then the user is authenticated
        var user = userRepository.findByEmail(authenticationRequest.getUserEmail());

        if(user == null){
            throw new NoResourceFoundException("User not found");
        }

        //var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(authenticationRequest.getUserEmail())
                //.token(jwtToken)
                .build();
    }

}
