package com.example.languagelearningmodule.security.roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(RoleEnum name);
        @Override
        void delete(Role role);
}
