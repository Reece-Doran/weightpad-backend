package com.weightpad.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weightpad.webapp.model.Routine;
import com.weightpad.webapp.service.RoutineService;
import com.weightpad.webapp.config.Config;

@CrossOrigin(origins = Config.frontendDomain)
//@CrossOrigin(origins = "http://localhost:3000/")

@RestController
@RequestMapping("/api/v1/routine")
public class RoutineController {
	
	@Autowired
	RoutineService routineService;
//	private RoutineRepositroy routineRepository;
	
	@GetMapping("")
	public List<Routine> getAllRoutines() {
		return routineService.getRoutines();
	}
	
	@GetMapping("/routinesByUserId/{id}")
	public List<Routine>findRoutineById(@PathVariable String id) {
		
		Long longRoutineId = Long.parseLong(id);
		return routineService.getRoutineByUserId(longRoutineId);
	}
	
	@PostMapping("/{userId}" )
	public Routine createRoutine(@PathVariable String userId, @RequestBody Routine routine) {
		return routineService.saveRoutine(routine, userId);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteRoutine(@PathVariable String id) {
		Long longRoutineId = Long.parseLong(id);
		return routineService.deleteRoutine(longRoutineId);
		
	}
}