package com.weightpad.webapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "routine")
public class Routine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	

	@Column(name = "name")
	private String name;
	
	//schedule mapping
	
	@OneToOne(mappedBy = "monday")
	@PrimaryKeyJoinColumn
    private Schedule monday;
	
	@OneToOne(mappedBy = "tuesday")
	@PrimaryKeyJoinColumn
    private Schedule tuesday;
	
	@OneToOne(mappedBy = "wednesday")
	@PrimaryKeyJoinColumn
    private Schedule wednesday;
	
	@OneToOne(mappedBy = "thursday")
	@PrimaryKeyJoinColumn
	private Schedule thursday;
	
	@OneToOne(mappedBy = "friday")
	@PrimaryKeyJoinColumn
	private Schedule friday;
	
	@OneToOne(mappedBy = "saturday")
	@PrimaryKeyJoinColumn
	private Schedule saturday;
	
	@OneToOne(mappedBy = "sunday")
	@PrimaryKeyJoinColumn
	private Schedule sunday;
	
	
	
	
	

	@ManyToOne
    @JoinColumn(name = "user", insertable = true, updatable = true)
    private User user;
	
	@OneToMany(mappedBy = "routine")
	private List<Exercise> exercises = new ArrayList<Exercise>();
	
	public Routine() {}
	
	//Set<Exercise> exercise -> goes in constructor

	public Routine(String name) {
		super();
		this.name = name;

	}
	
	public void addExerciseToList(Exercise exercise) {
		this.exercises.add(exercise);
	}
	
	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public void setRoutineToUser() {
		this.user.addRoutineToList(this);
	}
	
	public Long getUserId() {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return getName();
	}

}
