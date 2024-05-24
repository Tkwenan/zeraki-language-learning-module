package com.example.languagelearningmodule.Exercise;

import jakarta.persistence.*;

import com.example.languagelearningmodule.Lesson.Lesson;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    //set of questions in a given exercise
    @ElementCollection
    private Set<String> questions;

}
