package com.srans.uaa.repository;
 
import java.util.List;
import java.util.Optional;

import javax.xml.ws.Response;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srans.uaa.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	@Query("from User s where s.username=:username ")
	public Optional<User> findByUserName(@Param("username") String username);
	
	@Query(value = "select username from user  where  role=?1", nativeQuery = true)
	 public Object[] findUsernameByRole(String role);
}
