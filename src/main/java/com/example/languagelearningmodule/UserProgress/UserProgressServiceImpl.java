package com.example.languagelearningmodule.UserProgress;


import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.UserProgress.UserProgress;
import com.example.languagelearningmodule.Exercise.ExerciseRepository;
import com.example.languagelearningmodule.Lesson.LessonRepository;
import com.example.languagelearningmodule.UserProgress.UserProgressRepository;
import com.example.languagelearningmodule.UserProgress.UserProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProgressServiceImpl implements UserProgressService {
    @Autowired
    private final UserProgressRepository userProgressRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private final LessonRepository lessonRepository;

    public double calculateProgress(User user, Lesson lesson) {
        UserProgress userProgress = userProgressRepository.findByUserAndLesson(user, lesson);
        if (userProgress == null) {
            return 0;
        }
        int exercisesCompleted = userProgress.getCompletedExercises().size();;
        int totalExercises = lesson.getExercises().size();
        return (double) exercisesCompleted / totalExercises * 100;
    }

    //Update lesson progress when a user completes an exercise that's part of that lesson
    public void updateProgress(User user, Lesson lesson, Exercise exercise) {
        UserProgress userProgress = userProgressRepository.findByUserAndLesson(user, lesson);
        if (userProgress == null) {
            userProgress = new UserProgress();
            userProgress.setUser(user);
            userProgress.setLesson(lesson);
        }
        userProgress.getCompletedExercises().add(exercise);
        userProgressRepository.save(userProgress);
    }

    public Slice<UserProgress> displayAllUserProgressRecords(Pageable pageable){
        return userProgressRepository.findAll(pageable);
    }
}
