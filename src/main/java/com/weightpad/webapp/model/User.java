package com.weightpad.webapp.model;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3730195552672838423L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	
	@Column(name= "email")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name="enabled")
	private Boolean enabled = false;
	
	@Column(name="locked")
	private Boolean locked = false;
	
	@ManyToMany (fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Collection<Role> roles = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "user")
	private List<Routine> routines = new ArrayList<Routine>();
	
	@OneToOne(mappedBy = "user")
	private Schedule schedule;
	
	
	public User() {}
	
	
	
	public User(String username, String password, Collection<Role> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		
		Collection<Role> authArr = getRoles();
		
		for (Role role: authArr) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
		
	}
	
	
	
	public Schedule getSchedule() {
		return schedule;
	}


	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}


	public void addRoutineToList(Routine routine) {
		this.routines.add(routine);
	}
//	
	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.enabled;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	
	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public List<Routine> getRoutines() {
		return routines;
	}

	public void setRoutines(List<Routine> routines) {
		this.routines = routines;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "username: "+ getUsername() +" password: " +getPassword();
	}
}
