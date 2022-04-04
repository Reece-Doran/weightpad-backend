package com.weightpad.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weightpad.webapp.model.Exercise;
import com.weightpad.webapp.service.ExerciseService;
import com.weightpad.webapp.config.Config;


@CrossOrigin(origins = Config.frontendDomain)
//@CrossOrigin(origins = "https://weightpad-frontend.herokuapp.com/")
//@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/exercise" )
public class ExerciseController {
	
	
    // these are the controllers handling the exercise functionalities
	@Autowired
	ExerciseService exerciseService;
	
	
	@GetMapping("/exercisesbyroutineId/{id}")
	public List<Exercise>findExerciseById(@PathVariable String id) {
		Long longId = Long.parseLong(id);
		return exerciseService.getExerciseByRoutineId(longId);
	}
	
	@PostMapping("/{routineId}")
	public Exercise saveExercise(@PathVariable String routineId,@RequestBody Exercise exercise) {

		return exerciseService.saveExercise(routineId, exercise);
	}
	
	@PutMapping("/update/{exerciseId}")
    public Exercise updateExercise(@PathVariable String exerciseId,@RequestBody Exercise exercise) {
		Long longId = Long.parseLong(exerciseId);
        return exerciseService.updateExercise(longId,exercise);
    }
	
	@DeleteMapping("/delete/{id}")
    public String deleteExercise(@PathVariable String id) {
		Long longId = Long.parseLong(id);
        return exerciseService.deleteExercise(longId);
    }

	
}
