package com.example.languagelearningmodule.Exercise;

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
@RequestMapping("/api/exercises")
public class ExerciseController {
    @Autowired
    public ExerciseController(ModelMapper modelMapper, ExerciseService exerciseService) {
        this.modelMapper = modelMapper;
        this.exerciseService = exerciseService;
    }
    @Autowired
    private final ExerciseService exerciseService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/create-new-exercise")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ExerciseDTO> createNewExercise(@RequestBody ExerciseDTO exerciseDTO){
        //convert DTO to entity
        Exercise exerciseRequest = modelMapper.map(exerciseDTO, Exercise.class);
        Exercise exercise = exerciseService.createNewExercise(exerciseRequest);

        //convert entity to DTO
        ExerciseDTO exerciseResponse = modelMapper.map(exercise, ExerciseDTO.class);
        return new ResponseEntity<ExerciseDTO>(exerciseResponse, HttpStatus.CREATED);
    }

    @GetMapping("/view-exercise-details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ExerciseDTO> displayExerciseDetails(@PathVariable(name = "id") Long id) {
        Exercise exercise = exerciseService.getExerciseById(id);

        //convert to DTO
        ExerciseDTO exerciseResponse = modelMapper.map(exercise, ExerciseDTO.class);

        return ResponseEntity.ok().body(exerciseResponse);
    }

    @PutMapping("/update-exercise/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ExerciseDTO> updateExercise(@PathVariable long id,
                                              @RequestBody ExerciseDTO exerciseDTO) {
        //convert DTO to entity
        Exercise exerciseRequest = modelMapper.map(exerciseDTO, Exercise.class);

        Optional<Exercise> exercise = exerciseService.updateExerciseById(id, exerciseRequest);

        //entity to DTO
        ExerciseDTO exerciseResponse = modelMapper.map(exercise, ExerciseDTO.class);
        return ResponseEntity.ok().body(exerciseResponse);
    }

    @DeleteMapping("/delete-exercise/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse> deleteExercise(@PathVariable(name = "id") Long id) {
        exerciseService.deleteExerciseById(id);;
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Exercise deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/view-exercises")
    @PreAuthorize("isAuthenticated()")
    public List<ExerciseDTO> displayExercises(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "25") int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        Slice<Exercise> exerciseSlice = exerciseService.displayAllExercises(pageable);

        return exerciseSlice.getContent()
                .stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .collect(Collectors.toList());
    }
}
