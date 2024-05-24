package com.example.languagelearningmodule.UserProgress;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.Lesson.Lesson;
import com.example.languagelearningmodule.User.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
public interface UserProgressService {
    double calculateProgress(User user, Lesson lesson);
    void updateProgress(User user, Lesson lesson, Exercise exercise);
    Slice<UserProgress> displayAllUserProgressRecords(Pageable pageable);

}
