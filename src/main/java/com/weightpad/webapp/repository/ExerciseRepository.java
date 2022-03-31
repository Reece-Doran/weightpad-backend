package com.weightpad.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.weightpad.webapp.model.Exercise;
import com.weightpad.webapp.model.Routine;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long>{
	
	@Query("FROM Exercise WHERE routine_id = ?1")
	List<Exercise> findByRoutineId(Routine routineId);
}
