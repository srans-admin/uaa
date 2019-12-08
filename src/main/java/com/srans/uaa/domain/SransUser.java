package com.srans.uaa.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SransUser extends org.springframework.security.core.userdetails.User {
	  
	private static final long serialVersionUID = 1L;
	
	private Subscription subscription;
	
	public SransUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SransUser [subscription=");
		builder.append(subscription); 
		builder.append("]");
		return builder.toString();
	}  
   
    
} 