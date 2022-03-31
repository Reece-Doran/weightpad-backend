package com.weightpad.webapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weightpad.webapp.model.Exercise;
import com.weightpad.webapp.model.Routine;
import com.weightpad.webapp.model.User;
import com.weightpad.webapp.repository.ExerciseRepository;
import com.weightpad.webapp.repository.RoutineRepository;

@Service
public class RoutineServiceImple implements RoutineService {
	
	@Autowired
	RoutineRepository repository;
	
	@Autowired
	UserService userService;

	@Autowired
	ExerciseRepository exerciseRepository;
	
	
	
	public RoutineServiceImple(RoutineRepository repository, UserService userService) {
		super();
		this.repository = repository;
		this.userService = userService;
	}


	public Routine saveRoutine(Routine routine, String userId) {
		
		Long longUserId = Long.parseLong(userId);
		routine.setUser(userService.findById(longUserId));
		routine.setRoutineToUser();
		repository.save(routine);
        return routine;
    }
	
	
	public List<Routine> getRoutines() {
		return repository.findAll();
	}

	public List<Routine> getRoutineByUserId(Long id) {
		User user = userService.findById(id);
		return repository.findByUserId(user);
	}


	public Routine findById(Long id) {
		return repository.getById(id);
	}


	@Override
	public String deleteRoutine(Long id) {
		Routine routine = findById(id);
		List<Exercise> routineExercises = exerciseRepository.findByRoutineId(routine);
		
		if (routineExercises.isEmpty()) {
			repository.deleteById(id);
		} else {
			for(Exercise exercise : routineExercises) {				
				exerciseRepository.deleteById(exercise.getId());
			}
			repository.deleteById(id);
		} 
		
		
		return "Routine deleted";
	}
	
}