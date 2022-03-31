package com.weightpad.webapp.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weightpad.webapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

	User findByUsername(String username); 
	
	@Query("FROM User WHERE id = ?1")
	User getByUserId(Long id);
	
	@Query("SELECT enabled FROM User WHERE Email = ?1")
	Boolean checkIfEnabled(String username);

	@Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.enabled = TRUE WHERE a.username = ?1")
    int enableUser(String username);


}