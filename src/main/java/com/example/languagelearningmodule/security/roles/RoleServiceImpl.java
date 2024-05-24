package com.example.languagelearningmodule.security.roles;

import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import com.example.languagelearningmodule.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id){
        Optional<Role> role= roleRepository.findById(id);

        if(role.isPresent()){
            return role.get();
        } else {
            throw new ResourceNotFoundException("Lesson", "id", id);
        }
    }

    @Override
    @Transactional
    public Optional<Role> updateRoleById(Long roleId, Role updatedRole) {
        Optional<Role> possibleExistingRole = roleRepository.findById(roleId);
        if (possibleExistingRole.isPresent()) {
            Role existingRole = possibleExistingRole.get();
            if (updatedRole.getName() != null) {
                existingRole.setName(updatedRole.getName());
            }
            existingRole.setPermissions(updatedRole.getPermissions());
            roleRepository.save(existingRole);
            return Optional.of(existingRole);
        } else throw new NoResourceFoundException("The role to be updated was not found");
    }

    @Override
    public List<Role> displayRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteRole(Long roleId) throws NoResourceFoundException {
        Optional<Role> possibleRole = roleRepository.findById(roleId);
        if (possibleRole.isPresent()) {
            roleRepository.delete(possibleRole.get());
        } else throw new NoResourceFoundException("role of id " + roleId + " does not exist");
    }

    private RoleDTO convertToDto(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }
}
