package com.example.languagelearningmodule.Exercise;

import com.example.languagelearningmodule.Lesson.Lesson;
import lombok.Data;

import java.util.Set;

@Data
public class ExerciseDTO {
    private Lesson lesson;
    private Set<String> questions;
}
