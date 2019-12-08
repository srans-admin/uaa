package com.srans.uaa.oauth2service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.srans.uaa.domain.SransUser;
import com.srans.uaa.domain.Subscription;
import com.srans.uaa.domain.User;
import com.srans.uaa.repository.SubscriptionRepository;
import com.srans.uaa.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Override
	public SransUser loadUserByUsername(final String username) throws UsernameNotFoundException {

		try {

			SransUser sransUser = null;
			Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
			Optional<User> user = userRepository.findByUserName(username);

			if (user.isPresent()) {
				User tmpUser = user.get();
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + tmpUser.getRole());
				grantedAuthoritiesList.add(grantedAuthority);

				Optional<Subscription> subscriptionOp = subscriptionRepository.findByUserName(tmpUser.getUsername());
				if (subscriptionOp.isPresent()) {
					sransUser = new SransUser(tmpUser.getUsername(), tmpUser.getPassword(), grantedAuthoritiesList);
					sransUser.setSubscription(subscriptionOp.get());
					
					if(sransUser.getSubscription().getValidTo().compareTo(new Date()) > 1) {
						throw new UsernameNotFoundException("Subscription got expired.."+sransUser.getSubscription().getValidTo());
					}
					
				}
				return sransUser;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("User " + username + " was not found in the database");
		}

		return null;
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public List<User> getAll() { 
		return (List<User>) userRepository.findAll();
	}

	public User get(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}
}