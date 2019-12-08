package com.srans.uaa.domain;

public class UserSubscription {
	
	private User user;
	private Subscription subscription;
	 
	
	
	public UserSubscription() {
		super();
		this.user = new User(null, null, 0, 0, null);
		this.subscription = new Subscription();
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
		builder.append("UserSubscription [user=");
		builder.append(user);
		builder.append(", subscription=");
		builder.append(subscription);
		builder.append("]");
		return builder.toString();
	}
	
	

}
