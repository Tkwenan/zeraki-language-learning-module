package com.example.languagelearningmodule.security.roles;

import com.example.languagelearningmodule.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private final RoleService roleService;

    @Autowired
    private final ModelMapper modelMapper;

    @PostMapping("/create-new-role")
    public ResponseEntity<RoleDTO> createNewRole(@RequestBody RoleDTO roleDTO){
        //convert DTO to entity
        Role roleRequest = modelMapper.map(roleDTO, Role.class);
        Role role = roleService.addNewRole(roleRequest);

        //convert entity to DTO
        RoleDTO roleResponse = modelMapper.map(role, RoleDTO.class);
        return new ResponseEntity<RoleDTO>(roleResponse, HttpStatus.CREATED);
    }

    @GetMapping("/display-roles/{id}")
    public ResponseEntity<RoleDTO> displayRoleDetails(@PathVariable(name = "id") Long id) {
        Role role = roleService.getRoleById(id);

        //convert to DTO
        RoleDTO roleResponse = modelMapper.map(role, RoleDTO.class);

        return ResponseEntity.ok().body(roleResponse);
    }

    @PutMapping("/update-role/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable long id, @RequestBody RoleDTO roleDTO) {
        //convert DTO to entity
        Role roleRequest = modelMapper.map(roleDTO, Role.class);

        Optional<Role> role = roleService.updateRoleById(id, roleRequest);

        //entity to DTO
        RoleDTO roleResponse = modelMapper.map(role, RoleDTO.class);
        return ResponseEntity.ok().body(roleResponse);
    }

    @DeleteMapping("/delete-role/{id}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable(name = "id") Long id) {
        roleService.deleteRole(id);
        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Role deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/display-roles")
    public List<RoleDTO> displayRoles() {
        List<Role> rolesList = roleService.displayRoles();

        return rolesList
                .stream()
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }
}
