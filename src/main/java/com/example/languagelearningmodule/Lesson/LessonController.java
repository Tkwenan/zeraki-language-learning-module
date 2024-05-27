package com.example.languagelearningmodule.Lesson;

import com.example.languagelearningmodule.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private final LessonService lessonService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/create-new-lesson")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<LessonDTO> createNewLesson(@RequestBody LessonDTO lessonDTO){
        //convert DTO to entity
        Lesson lessonRequest = modelMapper.map(lessonDTO, Lesson.class);
        Lesson lesson = lessonService.createNewLesson(lessonRequest);

        //convert entity to DTO
        LessonDTO lessonResponse = modelMapper.map(lesson, LessonDTO.class);
        return new ResponseEntity<LessonDTO>(lessonResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view-lesson-details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LessonDTO> displayLessonDetails(@PathVariable(name = "id") Long id) {
        Lesson lesson = lessonService.getLessonById(id);

        //convert to DTO
        LessonDTO lessonResponse = modelMapper.map(lesson, LessonDTO.class);

        return ResponseEntity.ok().body(lessonResponse);
    }

    @PutMapping("/update-lesson/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<LessonDTO> updateLesson(@PathVariable long id,
                                              @RequestBody LessonDTO lessonDTO) {
        //convert DTO to entity
        Lesson lessonRequest = modelMapper.map(lessonDTO, Lesson.class);

        Optional<Lesson> lesson = lessonService.updateLessonById(id, lessonRequest);

        //entity to DTO
        LessonDTO lessonResponse = modelMapper.map(lesson, LessonDTO.class);
        return ResponseEntity.ok().body(lessonResponse);
    }

    @DeleteMapping("/delete-lesson/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> deleteLesson(@PathVariable(name = "id") Long id) {
        lessonService.deleteLessonById(id);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Lesson deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view-lessons")
    @PreAuthorize("isAuthenticated()")
    public List<LessonDTO> displayLessons(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "25") int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        Slice<Lesson> lessonSlice = lessonService.displayAllLessons(pageable);

        return lessonSlice.getContent()
                .stream()
                .map(lesson -> modelMapper.map(lesson, LessonDTO.class))
                .collect(Collectors.toList());
    }
}
