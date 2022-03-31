package com.weightpad.webapp.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "schedule_id")
	private Long scheduleId;
	
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private User user;
	
	
	@OneToOne
	@JoinColumn(name = "monday")
	private Routine monday = null;
	
	@OneToOne
	@JoinColumn(name = "tuesday")
	private Routine tuesday = null;
	
	@OneToOne
	@JoinColumn(name = "wednesday")
	private Routine wednesday = null;
	
	@OneToOne
	@JoinColumn(name = "thursday")
	private Routine thursday = null;
	
	@OneToOne
	@JoinColumn(name = "friday")
	private Routine friday = null;
	
	@OneToOne
	@JoinColumn(name = "saturday")
	private Routine saturday = null;
	
	@OneToOne
	@JoinColumn(name = "sunday")
	private Routine sunday = null;
	

	public Schedule() {}
	
	public Schedule(User user) {
		this.user = user;
	}
	

	public Schedule(Routine monday, Routine tuesday, Routine wednesday, Routine thursday, Routine friday,
			Routine saturday, Routine sunday) {
		super();
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
	}

	
	
	public Long getscheduleId() {
		return scheduleId;
	}

	public void setUserId(Long userId) {
		this.scheduleId = userId;
	}

//	public User getUser() {
//		return user;
//	}

	public void setUser(User user) {
		this.user = user;
	}

	public Routine getMonday() {
		return monday;
	}

	public void setMonday(Routine monday) {
		this.monday = monday;
	}

	public Routine getTuesday() {
		return tuesday;
	}

	public void setTuesday(Routine tuesday) {
		this.tuesday = tuesday;
	}

	public Routine getWednesday() {
		return wednesday;
	}

	public void setWednesday(Routine wednesday) {
		this.wednesday = wednesday;
	}

	public Routine getThursday() {
		return thursday;
	}

	public void setThursday(Routine thursday) {
		this.thursday = thursday;
	}

	public Routine getFriday() {
		return friday;
	}

	public void setFriday(Routine friday) {
		this.friday = friday;
	}

	public Routine getSaturday() {
		return saturday;
	}

	public void setSaturday(Routine saturday) {
		this.saturday = saturday;
	}

	public Routine getSunday() {
		return sunday;
	}

	public void setSunday(Routine sunday) {
		this.sunday = sunday;
	}
	
	

	
	
}
