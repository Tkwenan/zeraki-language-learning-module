package com.example.languagelearningmodule.User;

import com.example.languagelearningmodule.exceptions.ResourceNotFoundException;
import com.example.languagelearningmodule.exceptions.UserAlreadyExistsException;
import com.example.languagelearningmodule.security.roles.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUser(UserDTO userDTO){
        if(emailExists(userDTO.getEmail())){
            throw new UserAlreadyExistsException("There is an account with that email address: " + userDTO.getEmail());
        }

        User user = new User();

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findRoleByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            return user.get();
        } else {
            throw new ResourceNotFoundException("User", "id", id);
        }
    }
    public Optional<User> updateUserById(Long id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setLessons(updatedUser.getLessons());
            user.setProgressRecords(updatedUser.getProgressRecords());
            userRepository.save(user);
            return Optional.of(user);
        } else {
            throw new ResourceNotFoundException("User", "id", id);
        }
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    public Slice<User> displayAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    private boolean emailExists(final String email) {
        return userRepository.findByEmail(email) != null;
    }
}
