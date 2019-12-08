package com.srans.uaa.oauth2service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.srans.uaa.domain.Subscription;
import com.srans.uaa.repository.SubscriptionRepository;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository; 

	public Subscription save(Subscription subscription) { 
		return subscriptionRepository.save(subscription);
	}

	public List<Subscription> getAll() { 
		return (List<Subscription>) subscriptionRepository.findAll();
	}

	public Subscription get(Long id) {
		return subscriptionRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		subscriptionRepository.deleteById(id); 
	}
}