package com.example.languagelearningmodule.Lesson;

import com.example.languagelearningmodule.Exercise.Exercise;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.UserProgress.UserProgress;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name="lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "lesson")
    private Set<Exercise> exercises;

    @ManyToMany(mappedBy = "lessons")
    private Set<User> enrolledUsers;

    //a lesson has multiple students enrolled for it, hence multiple progress records
    @OneToMany(mappedBy = "lesson")
    private Set<UserProgress> progressRecords;

}
