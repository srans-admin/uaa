package com.srans.uaa.repository;
 
import org.springframework.stereotype.Repository;

import com.srans.uaa.domain.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("from User s where s.username=:username ")
	public Optional<User> findByUserName(@Param("username") String username);
}
