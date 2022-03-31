package com.weightpad.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weightpad.webapp.model.Exercise;
import com.weightpad.webapp.model.Routine;
import com.weightpad.webapp.repository.ExerciseRepository;

@Service
public class ExerciseServiceImple implements ExerciseService {
	
	@Autowired
	private ExerciseRepository repository;
	
	@Autowired
	private RoutineService routineService;
	
	
	public ExerciseServiceImple(ExerciseRepository repository, RoutineService routineService) {
		super();
		this.repository = repository;
		this.routineService = routineService;
	}

	public Exercise saveExercise(String routineId, Exercise exercise) {
		
		Long longRoutineId = Long.parseLong(routineId);
		exercise.setRoutine(routineService.findById(longRoutineId));
		exercise.setExerciseToRoutine();
		repository.save(exercise);
		return exercise;

    }
	
	public List<Exercise> getExercises() {
		return  repository.findAll();
	}
	
	public List<Exercise> getExerciseByRoutineId(Long id) {
		Routine rout = routineService.findById(id);
		return repository.findByRoutineId(rout);
	}
	
	public String deleteExercise(Long id) {
        repository.deleteById(id);
        return "Exercise removed !! " + id;
    }
	
	public Exercise updateExercise(Long longId,Exercise exercise) {
		Exercise existingExercise = repository.findById(longId).orElse(null);
		existingExercise.setName(exercise.getName());
		existingExercise.setRepsSets(exercise.getRepsSets());
		existingExercise.setWeight(exercise.getWeight());
//        repository.save(existingExercise);
        return repository.save(existingExercise);
    }

	
}