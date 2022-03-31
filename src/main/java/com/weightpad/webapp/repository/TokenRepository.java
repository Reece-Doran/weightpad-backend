package com.weightpad.webapp.repository;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.weightpad.webapp.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{
	Token findByToken(String token);
	
	@Query("FROM Token WHERE user_id = ?1")
	Token findTokenByUser(Long id);
	
	@Transactional
    @Modifying
    @Query("UPDATE Token c " +
            "SET c.confirmed = ?2 " +
            "WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmed);

	
//	UPDATE Student e SET e.firstName = 'Ram' WHERE e.id = :id
	@Transactional
	@Modifying
	@Query("UPDATE Token c " +
		            "SET c.created = ?2, c.expires = ?3 "+
		            "WHERE id = ?1")
	void update(Long token, LocalDateTime created, LocalDateTime expires);
	
}

