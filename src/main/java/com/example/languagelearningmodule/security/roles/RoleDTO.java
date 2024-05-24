package com.example.languagelearningmodule.security.roles;

import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.security.permissions.Permission;
import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class RoleDTO {
        private String name;
        private Collection<Permission> permissions;
        private Collection<User> users;
}
