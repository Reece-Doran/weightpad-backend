package com.weightpad.webapp.service;

import java.util.List;

import com.weightpad.webapp.model.Routine;

public interface RoutineService {

	Routine saveRoutine(Routine routine, String userId);
	
	List<Routine> getRoutines();
	
	List<Routine> getRoutineByUserId(Long id);
	
	Routine findById(Long id);

	String deleteRoutine(Long longRoutineId);
}
