package com.example.languagelearningmodule.Exercise;


import com.example.languagelearningmodule.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService{
    @Autowired
    private final ExerciseRepository exerciseRepository;

    public Exercise createNewExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public Exercise getExerciseById(Long id){
        Optional<Exercise> exercise= exerciseRepository.findById(id);

        if(exercise.isPresent()){
            return exercise.get();
        } else {
            throw new ResourceNotFoundException("Exercise", "id", id);
        }
    }
    public Optional<Exercise> updateExerciseById(Long id, Exercise updatedExercise) {
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if (optionalExercise.isPresent()) {
            Exercise exercise = optionalExercise.get();
            exercise.setLesson(updatedExercise.getLesson());
            exercise.setQuestions(updatedExercise.getQuestions());
            exerciseRepository.save(exercise);
            return Optional.of(exercise);
        } else {
            throw new ResourceNotFoundException("Exercise", "id", id);
        }
    }

    public void deleteExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", id));
        exerciseRepository.delete(exercise);
    }

    public Slice<Exercise> displayAllExercises(Pageable pageable){
        return exerciseRepository.findAll(pageable);
    }
}
