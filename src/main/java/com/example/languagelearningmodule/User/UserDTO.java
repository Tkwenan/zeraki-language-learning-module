package com.example.languagelearningmodule.User;

import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.UserProgress.UserProgress;
import com.example.languagelearningmodule.security.roles.Role;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private Set<Lesson> lessons = new HashSet<>();
    private Set<UserProgress> progressRecords;
    private Collection<Role> roles;
}
