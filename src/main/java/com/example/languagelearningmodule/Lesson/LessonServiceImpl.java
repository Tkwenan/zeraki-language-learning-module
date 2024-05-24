package com.example.languagelearningmodule.Lesson;

import com.example.languagelearningmodule.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    @Autowired
    private final LessonRepository lessonRepository;

    public Lesson createNewLesson(Lesson lesson) {
        return  lessonRepository.save(lesson);
    }

    public Lesson getLessonById(Long id){
        Optional<Lesson> lesson= lessonRepository.findById(id);

        if(lesson.isPresent()){
            return lesson.get();
        } else {
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
    }
    public Optional<Lesson> updateLessonById(Long id, Lesson updatedLesson) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        if (optionalLesson.isPresent()) {
            Lesson lesson = optionalLesson.get();
            lesson.setTitle(updatedLesson.getTitle());
            lesson.setExercises(updatedLesson.getExercises());
            lesson.setEnrolledUsers(updatedLesson.getEnrolledUsers());
            lesson.setProgressRecords(updatedLesson.getProgressRecords());
            lessonRepository.save(lesson);
            return Optional.of(lesson);
        } else {
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
    }

    public void deleteLessonById(Long id) {
        Lesson lesson =  lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", "id", id));
        lessonRepository.delete(lesson);
    }

    public Slice<Lesson> displayAllLessons(Pageable pageable){
        return lessonRepository.findAll(pageable);
    }
}

