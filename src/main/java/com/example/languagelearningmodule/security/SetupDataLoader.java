package com.example.languagelearningmodule.security;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.security.permissions.Permission;
import com.example.languagelearningmodule.security.permissions.PermissionRepository;
import com.example.languagelearningmodule.security.roles.Role;
import com.example.languagelearningmodule.security.roles.RoleRepository;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

            if (alreadySetup)
                return;

            //creating permissions
            //user endpoints
            Permission registerNewUser = createPermissionIfNotFound("CREATE_USER");
            Permission displayUserDetails = createPermissionIfNotFound("DISPLAY_USER");
            Permission updateUser = createPermissionIfNotFound("UPDATE_USER");
            Permission deleteUser = createPermissionIfNotFound("DELETE_USER");
            Permission displayUsers = createPermissionIfNotFound("DISPLAY_USERS");

            //lesson endpoints
            Permission createNewLesson = createPermissionIfNotFound("CREATE_LESSON");
            Permission displayLessonDetails = createPermissionIfNotFound("VIEW_LESSON_DETAILS");
            Permission updateLesson = createPermissionIfNotFound("UPDATE_LESSON");
            Permission deleteLesson = createPermissionIfNotFound("DELETE_LESSON");
            Permission displayLessons = createPermissionIfNotFound("VIEW_LESSONS");


            //exercise endpoints
            Permission createNewExercise = createPermissionIfNotFound("CREATE_EXERCISE");
            Permission displayExerciseDetails = createPermissionIfNotFound("VIEW_EXERCISE_DETAILS");
            Permission updateExercise = createPermissionIfNotFound("UPDATE_EXERCISE");
            Permission deleteExercise = createPermissionIfNotFound("DELETE_EXERCISE");
            Permission displayExercises = createPermissionIfNotFound("VIEW_EXERCISES");


            //user progress endpoints
            Permission viewUserProgress = createPermissionIfNotFound("VIEW_USER_PROGRESS");
            Permission completeExercise = createPermissionIfNotFound("COMPLETE_EXERCISE");


            List<Permission> adminPrivileges = Arrays.asList(
                    registerNewUser,
                    displayUserDetails,
                    updateUser,
                    deleteUser,
                    displayUsers,
                    createNewLesson,
                    displayLessonDetails,
                    updateLesson,
                    deleteLesson,
                    displayLessons,
                    createNewExercise,
                    displayExerciseDetails,
                    updateExercise,
                    deleteExercise,
                    displayExercises,
                    viewUserProgress,
                    completeExercise
            );

        List<Permission> userPrivileges = Arrays.asList(
                displayUserDetails,
                updateUser, //a user can update their own account
                deleteUser, //a user can delete their own account
                displayLessonDetails,
                displayLessons,
                displayExerciseDetails,
                displayExercises,
                viewUserProgress, //a user can view their own progress on lessons
                completeExercise
        );

            createRoleIfNotFound("ADMIN", adminPrivileges);
            createRoleIfNotFound("USER", userPrivileges);

            Role adminRole = roleRepository.findRoleByName("ADMIN");
            User user = new User();
            user.setFirstName("Test");
            user.setLastName("Test");
            user.setPassword(passwordEncoder.encode("test"));
            user.setEmail("test@test.com");
            user.setRoles(Arrays.asList(adminRole));
            userRepository.save(user);

            alreadySetup = true;
        }

        @Transactional
        Permission createPermissionIfNotFound(String name) {

            Permission permission = permissionRepository.findByName(name);
            if (permission == null) {
                permission = new Permission(name);
                permissionRepository.save(permission);
            }
            return permission;
        }

        @Transactional
        Role createRoleIfNotFound(String name, Collection<Permission> permissions) {

            Role role = roleRepository.findRoleByName(name);
            if (role == null) {
                role = new Role(name);
                role.setPermissions(permissions);
                roleRepository.save(role);
            }
            return role;
        }
}

