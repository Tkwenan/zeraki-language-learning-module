package com.example.languagelearningmodule.Lesson;

import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface LessonService {
    Lesson createNewLesson(Lesson lesson);
    Lesson getLessonById(Long id) throws NoResourceFoundException;
    Optional<Lesson> updateLessonById(Long id, Lesson lesson);
    void deleteLessonById(Long id);
    Slice<Lesson> displayAllLessons(Pageable pageable);
}
