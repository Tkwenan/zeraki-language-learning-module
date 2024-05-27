package com.example.languagelearningmodule.UserProgress;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.Exercise.ExerciseRepository;
import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.Lesson.LessonRepository;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-progress")
public class UserProgressController {
    @Autowired
    private UserProgressService userProgressService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @GetMapping("/{userId}/{lessonId}")
    @PreAuthorize("isAuthenticated()")
    public double getProgress(@PathVariable Long userId, @PathVariable Long lessonId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NoResourceFoundException("Lesson not found"));
        return userProgressService.calculateProgress(user, lesson);
    }

    @PostMapping("/{userId}/{lessonId}/completeExercise/{exerciseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public void completeExercise(@PathVariable Long userId, @PathVariable Long lessonId, @PathVariable Long exerciseId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoResourceFoundException("User not found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new NoResourceFoundException("Lesson not found"));
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new NoResourceFoundException("Exercise not found"));
        userProgressService.updateProgress(user, lesson, exercise);
    }
}
