package com.srans.uaa.repository;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.srans.uaa.domain.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	
	@Query("from Subscription s where s.userName=:userName ")
	public Optional<Subscription> findByUserName(@Param("userName") String userName);
	
	@Query("select s from Subscription s  where  s.userName=?1")
	public List<Subscription> subsDetailsByUsername(String username);

	
}
