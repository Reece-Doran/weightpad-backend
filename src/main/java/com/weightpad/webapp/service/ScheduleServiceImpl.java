package com.weightpad.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weightpad.webapp.model.Routine;
import com.weightpad.webapp.model.Schedule;
import com.weightpad.webapp.repository.RoutineRepository;
import com.weightpad.webapp.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired
	ScheduleRepository schedulerepository;
	
	@Autowired
	RoutineRepository routineRepository; 
	
	@Override
	public Schedule getSchedule() {
	
		return null;
	}

	@Override
	public void saveSchedule(Schedule schedule) {
		schedulerepository.save(schedule);		
	}
	
	@Override
	public Schedule findByScheduleId(Long id) {
		
		return schedulerepository.findByScheduleId(id);
	}
	
	
	@Override
	public String updateSchedule(Long scheduleId, Long routineId, String day) {
				
		Schedule schedule = schedulerepository.getById(scheduleId);		
		Routine selectedRoutine = routineRepository.getById(routineId);
		
		switch(day) {
		
		case "monday": schedule.setMonday(selectedRoutine);
		break;
		case "tuesday": schedule.setTuesday(selectedRoutine);
		break;
		case "wednesday": schedule.setWednesday(selectedRoutine);
		break;
		case "thursday": schedule.setThursday(selectedRoutine);
		break;
		case "friday": schedule.setFriday(selectedRoutine);
		break;
		case "saturday": schedule.setSaturday(selectedRoutine);
		break;
		case "sunday": schedule.setSunday(selectedRoutine);
		break;
		}

		schedulerepository.updateSchedule(schedule.getscheduleId(), schedule);
		return "updated";
//		schedulerepository.updateSchedule(schedule.getscheduleId(), schedule);
//		return "updated";
	}

	

}
