package com.weightpad.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "exercise")
public class Exercise {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Long Id;
	
	@ManyToOne
    @JoinColumn(name = "routine_id", insertable = true, updatable = true)
    private Routine routine;

	@Column(name = "name")
	private String name;
	
	
	@Column(name = "reps_sets")
	private String repsSets;
	
	@Column(name = "weight")
	private String weight;
	
	public Exercise() {}
	
	public Exercise(String name, String repsSets, String weight) {
		super();
		this.name = name;
		this.repsSets = repsSets;
		this.weight = weight;
	}

	public Long getRoutineId() {
		return routine.getId();
	}


	
	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRepsSets() {
		return repsSets;
	}

	public void setRepsSets(String setsReps) {
		this.repsSets = setsReps;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}


	public void setRoutine(Routine routine) {
		this.routine = routine;
		
	}


	public void setExerciseToRoutine() {
		this.routine.addExerciseToList(this);
		
	}
	public void setRoutineToUser() {
		
	}
	public String toString() {
		return "name: "+ getName() + ", Reps and sets: "+getRepsSets() + ", weight: "+getWeight(); 
	}
}

