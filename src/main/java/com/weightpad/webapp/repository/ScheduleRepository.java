package com.weightpad.webapp.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weightpad.webapp.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>{
	
	@Query("FROM Schedule WHERE schedule_id = ?1")
	Schedule findByScheduleId(Long scheduleId);
	

	@Transactional
	@Modifying
	@Query("UPDATE Schedule c SET c = ?2 WHERE schedule_id = ?1")
	
	void updateSchedule(Long id, Schedule schedule);
	
}
