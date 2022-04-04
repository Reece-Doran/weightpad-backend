package com.weightpad.webapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weightpad.webapp.model.Schedule;
import com.weightpad.webapp.service.ScheduleService;
import com.weightpad.webapp.config.Config;

@RestController
@CrossOrigin(origins = Config.frontendDomain)
//@CrossOrigin(origins = "http://localhost:3000/")

@RequestMapping("/api/v1/user/schedule")
public class ScheduleController {
	
	@Autowired
	ScheduleService scheduleService;
	
	@GetMapping("/")
	public Schedule getSchedule() {
		return scheduleService.getSchedule();
	}
	@GetMapping("/getbyid/{id}")
	public Schedule getScheduleByUserId(@PathVariable Long id) {		
		return scheduleService.findByScheduleId(id);
	}
	
	
	@PutMapping("/update/{scheduleId}/{routineId}")
    public String updateSchedule(@PathVariable String scheduleId, @PathVariable String routineId, @RequestParam("day") String day) {
		Long longScheduleId = Long.parseLong(scheduleId);
		Long longRoutineId = Long.parseLong(routineId);
				
        return scheduleService.updateSchedule(longScheduleId, longRoutineId, day);
    }
//	@GetMapping("/update")
//	public String confirmEmailToken(@RequestParam("token") String token) {
//		return userService.confirmEmailToken(token);
//	}
}
