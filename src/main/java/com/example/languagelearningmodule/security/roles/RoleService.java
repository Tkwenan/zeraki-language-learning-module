package com.example.languagelearningmodule.security.roles;

import com.example.languagelearningmodule.exceptions.NoResourceFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RoleService {
    Role addNewRole(Role role);
    Role getRoleById(Long id) throws NoResourceFoundException;
    Optional<Role> updateRoleById(Long roleId, Role updatedRole);
    void deleteRole(Long roleId) throws NoResourceFoundException;
    List<Role> displayRoles();
}

