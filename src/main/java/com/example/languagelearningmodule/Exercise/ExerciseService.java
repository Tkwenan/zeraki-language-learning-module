package com.example.languagelearningmodule.Exercise;

import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ExerciseService {
    Exercise createNewExercise(Exercise exercise);
    Exercise getExerciseById(Long id) throws NoResourceFoundException;
    Optional<Exercise> updateExerciseById(Long id, Exercise exercise);
    void deleteExerciseById(Long id);
    Slice<Exercise> displayAllExercises(Pageable pageable);
}
