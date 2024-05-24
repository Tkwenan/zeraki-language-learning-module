package com.example.languagelearningmodule.UserProgress;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.User.User;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "user_progress")
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToMany
    @JoinTable(
            name = "user_progress_exercise",
            joinColumns = @JoinColumn(name = "user_progress_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id"))
    private Set<Exercise> completedExercises = new HashSet<>();
}
