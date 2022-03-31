package com.weightpad.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weightpad.webapp.model.Routine;
import com.weightpad.webapp.model.User;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Long> {
	
	@Query("FROM Routine WHERE user = ?1")
	List<Routine> findByUserId(User user);
}
