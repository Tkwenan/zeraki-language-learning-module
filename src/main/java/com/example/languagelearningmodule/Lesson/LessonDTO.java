package com.example.languagelearningmodule.Lesson;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.UserProgress.UserProgress;
import lombok.Data;

import java.util.Set;

@Data
public class LessonDTO {
    private String title;
    private Set<Exercise> exercises;
    private Set<User> enrolledUsers;
    private Set<UserProgress> progressRecords;
}
