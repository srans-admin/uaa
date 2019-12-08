package com.srans.uaa.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.srans.uaa.domain.Subscription;
import com.srans.uaa.domain.User;
import com.srans.uaa.domain.UserSubscription;
import com.srans.uaa.oauth2service.SubscriptionService;
import com.srans.uaa.oauth2service.UserService;

@RestController
public class UserSubscriptionController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SubscriptionService subscriptionService;
	

    @GetMapping("/user/me")
    public Principal user(Principal principal) {
        return principal;
    }
    
    @PostMapping("/v1/users")
    public ResponseEntity<UserSubscription> addUserWithSubscription(@RequestBody UserSubscription userSubscription) {
    	
    	UserSubscription userSubscriptionNew =  new UserSubscription();
    	userSubscriptionNew.setUser(userService.save(userSubscription.getUser()));
    	userSubscriptionNew.setSubscription(subscriptionService.save(userSubscription.getSubscription()));
    	
    	return new ResponseEntity<UserSubscription>(userSubscriptionNew, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/v1/users")
    public ResponseEntity<List<User>> getUsers() {
    	
    	List<User> users = userService.getAll(); 
    	return new ResponseEntity<List<User>>(users, new HttpHeaders(), HttpStatus.OK);
    }
    
    @GetMapping("/v1/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
    	
    	User usernew = userService.get(id); 
    	return new ResponseEntity<User>(usernew, new HttpHeaders(), HttpStatus.OK);
    }
    
    @DeleteMapping("/v1/users/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
    	
    	userService.delete(id); 
    	boolean isDone = true;
    	return new ResponseEntity<Boolean>(isDone, new HttpHeaders(), HttpStatus.OK);
    }
    
}
