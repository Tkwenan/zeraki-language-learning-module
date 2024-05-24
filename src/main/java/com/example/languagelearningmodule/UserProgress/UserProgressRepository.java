package com.example.languagelearningmodule.UserProgress;

import com.example.languagelearningmodule.UserProgress.UserProgress;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.Lesson.Lesson;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long>{
    UserProgress findByUserAndLesson(User user, Lesson lesson);
}
