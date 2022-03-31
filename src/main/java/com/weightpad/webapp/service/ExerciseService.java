package com.weightpad.webapp.service;

import java.util.List;

import com.weightpad.webapp.model.Exercise;

public interface ExerciseService {
	
	public Exercise saveExercise(String routineId, Exercise exercise);
	
	List<Exercise> getExercises();
	
	List<Exercise> getExerciseByRoutineId(Long id);
	
	String deleteExercise(Long id);
	
	Exercise updateExercise(Long longId, Exercise exercise);
	
}
