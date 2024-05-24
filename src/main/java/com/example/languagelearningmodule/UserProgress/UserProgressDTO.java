package com.example.languagelearningmodule.UserProgress;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.User.User;
import lombok.Data;

@Data
public class UserProgressDTO {
    private User user;
    private Lesson lesson;
    private Exercise exercise;
}
