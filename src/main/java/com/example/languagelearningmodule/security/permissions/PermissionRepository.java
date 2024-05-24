package com.example.languagelearningmodule.security.permissions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
    @Override
    void delete(Permission privilege);
}
