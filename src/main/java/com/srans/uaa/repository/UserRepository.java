package com.srans.uaa.repository;
 
import java.util.List;
import java.util.Optional;

import javax.xml.ws.Response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srans.uaa.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("from User s where s.username=:username ")
	public Optional<User> findByUserName(@Param("username") String username);
	
	@Query(value = "select username from user  where  role=?1", nativeQuery = true)
	 public Object[] findUsernameByRole(String role);
	
	@Query(value = "select u from User u where password=?1")
	public List<User> getUserInfo(String pass);
	
	@Query(value="select count(u.id) from User u where u.password=?1")
	public int checkDetails(String password);
	
	@Query(value="select u.password from User u where username=?1")
	public String getPassword(String username);
	
	@Query(value="select count(u.username) from User u where username=?1")
	public int checkDuplicateUserName(String username);
	
	@Query(value = "select u from User u where u.username=?1")
	public List<User> getUserDetails(String username);
}
