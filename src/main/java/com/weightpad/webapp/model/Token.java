package com.weightpad.webapp.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "token")
public class Token {
	
	public Token() {}
	
	public Token(String token, LocalDateTime created, LocalDateTime expires, User user) {
			this.token = token;
			this.created = created;
			this.expires = expires;
			this.user = user;
	}
	
	@SequenceGenerator(
            name = "confirmation_token_seq",
            sequenceName = "confirmation_token_seq",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_seq"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column(nullable = false)
    private LocalDateTime expires;

    @Column()
    private LocalDateTime confirmed;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getExpires() {
		return expires;
	}

	public void setExpires(LocalDateTime expires) {
		this.expires = expires;
	}

	public LocalDateTime getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(LocalDateTime confirmed) {
		this.confirmed = confirmed;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
    
}
