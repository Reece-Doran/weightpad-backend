package com.weightpad.webapp.service;


import com.weightpad.webapp.model.Schedule;


public interface ScheduleService {
	
	Schedule getSchedule();
	
	void saveSchedule(Schedule schedule);

	String updateSchedule(Long scheduleId, Long routineId,String day);
	
	Schedule findByScheduleId(Long id);
//	String getScheduleByUserId(Long)
}
