package com.srans.uaa.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.srans.uaa.domain.Subscription;
import com.srans.uaa.domain.User;
import com.srans.uaa.domain.UserSubscription;
import com.srans.uaa.oauth2service.SubscriptionService;
import com.srans.uaa.oauth2service.UserService;
import com.srans.uaa.repository.SubscriptionRepository;
import com.srans.uaa.repository.UserRepository;

@RestController
public class UserSubscriptionController {

	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@GetMapping("/user/me")
	public Principal user(Principal principal) {
		return principal;
	}

	@PostMapping("/v1/users")
	public ResponseEntity<UserSubscription> addUserWithSubscription(@RequestBody UserSubscription userSubscription) {

		UserSubscription userSubscriptionNew = new UserSubscription();
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

	@GetMapping("/v1/users/curr_pass/{username}")
	public Map<String, String> findPassword(@PathVariable("username") String userName) {
		String userPassword = userRepo.getPassword(userName);
		Map<String, String> response = new HashMap<>();
		response.put("password", userPassword);
		return response;
	}

	@DeleteMapping("/v1/users/{id}")
	public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {

		userService.delete(id);
		boolean isDone = true;
		return new ResponseEntity<Boolean>(isDone, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("v1/superAdminUserName")
	public Object[] getSuperAdminUserName() {

		Object[] superAdmin = userRepo.findUsernameByRole("superadmin".toUpperCase());
		return superAdmin;
	}

	@PutMapping("v1/users/")
	public Map<String, Boolean> updateUsers(@Valid @RequestBody User userDetails) {

		Boolean status = false;

		if (userDetails.getUsername() == null) {
			int checkPassword = userRepo.checkDetails(userDetails.getOldPassword());

			if (checkPassword > 0) {

				System.out.println(checkPassword);
				userRepo.getUserInfo(userDetails.getOldPassword()).stream().forEach(user -> {
					user.setPassword(userDetails.getPassword());
					userRepo.save(user);

				});

				status = true;
			}
		}

		else {
			int checkUserName = userRepo.checkDuplicateUserName(userDetails.getUsername());
			if (checkUserName > 0) {
			} else {
				System.out.println(userDetails.getOldUserName());

				userRepo.getUserDetails(userDetails.getOldUserName()).stream().forEach(user -> {

					user.setUsername(userDetails.getUsername());
					System.out.println(user.getUsername());

					subscriptionRepo.subsDetailsByUsername(userDetails.getOldUserName()).stream()
							.forEach(subscription -> {
								subscription.setUserName(userDetails.getUsername());
								subscriptionRepo.save(subscription);

							});

					userRepo.save(user);
				});
				status = true;
			}
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("updateStatus", status);
		return response;

	}

}
