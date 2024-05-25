package com.example.languagelearningmodule.User;

import com.example.languagelearningmodule.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private final UserService userService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerNewUser(@RequestBody UserDTO userDTO){
        User user = userService.registerNewUser(userDTO);

        //convert entity to DTO
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<UserDTO>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/display-users/{id}")
    public ResponseEntity<UserDTO> displayUserDetails(@PathVariable(name = "id") Long id) {
        User user = userService.getUserById(id);

        //convert to DTO
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);

        return ResponseEntity.ok().body(userResponse);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable long id,
                                                    @RequestBody UserDTO userDTO) {
        //convert DTO to entity
        User userRequest = modelMapper.map(userDTO, User.class);

        Optional<User> user = userService.updateUserById(id, userRequest);

        //entity to DTO
        UserDTO userResponse = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userResponse);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUserById(id);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "User deleted successfully", HttpStatus.OK);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/display-users")
    public List<UserDTO> displayUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "25") int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);

        Slice<User> userSlice = userService.displayAllUsers(pageable);

        return userSlice.getContent()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

}
