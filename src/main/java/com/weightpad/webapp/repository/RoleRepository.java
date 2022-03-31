package com.weightpad.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weightpad.webapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long>{
	Role findByName(String role);
}
