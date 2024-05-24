package com.example.languagelearningmodule.User;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    User registerNewUser(UserDTO user);
    void saveRegisteredUser(User user);
    User findUserByEmail(String email);
    User getUserById(Long id) throws NoResourceFoundException;
    Optional<User> updateUserById(Long id, User user);
    void deleteUserById(Long id);
    Slice<User> displayAllUsers(Pageable pageable);
}
