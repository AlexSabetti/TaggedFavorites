package com.codingdojo.groupproject.services;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.codingdojo.groupproject.models.LoginUser;
import com.codingdojo.groupproject.models.RegisterUser;
import com.codingdojo.groupproject.models.User;
import com.codingdojo.groupproject.repositories.RoleRepository;
import com.codingdojo.groupproject.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	public List<User> findUsers(){
		return userRepository.findAll();
	}
	
	public User register(RegisterUser newRegisterObject, BindingResult result) {
		Boolean valid = true;
		
		Optional<User> potentialUser = userRepository.findByEmail(newRegisterObject.getEmail());
		if(potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "This email already has an account attached to it.");
			valid = false;
		}
		
		if(!newRegisterObject.getPassword().equals(newRegisterObject.getConfirm())) {
			result.rejectValue("confirm", "Matches", "The confirming password must match the password.");
			valid = false;
		}
		if (result.hasErrors()) valid = false;
		
		if(valid) {
			String hashed = BCrypt.hashpw(newRegisterObject.getPassword(), BCrypt.gensalt());
			User user = new User(newRegisterObject.getUserName(), newRegisterObject.getEmail(), hashed);
			//Subject to change, first account that is registered in an empty database will become the administrator
			if(userRepository.findAll().size() == 0) { //This should be changed later because having to rely on both finding and then measuring the size of an ever increasing list of users is not very smart
				user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
				user = userRepository.save(user);
			} else {
				user.setRoles(roleRepository.findByName("ROLE_USER"));
				user = userRepository.save(user);
			}
			
			return userRepository.save(user);
		}else {
			return null;
		}	
	}
	
	public User login(LoginUser newLoginObject, BindingResult result) {
		
		Optional<User> potentialUser = userRepository.findByEmail(newLoginObject.getEmail());
		if(!potentialUser.isPresent()) {
			result.rejectValue("email", "Matches", "Unknown Password or Email");	
			return null;
		}
		
		//If we get here, the user does exist
		User userInDatabase = potentialUser.get();
		
		
		if(!BCrypt.checkpw(newLoginObject.getPassword(), userInDatabase.getPassword())) {
			result.rejectValue("password", "Matches", "Unknown Password or Email");
			return null;
		} else {
			return potentialUser.get();
		}
	}
	
	public User findUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			return user.get();
		} else {
			return null;
		}
	}
	
	public List<User> findByRole(String roleName){
		return userRepository.findByRoles(roleRepository.findByName(roleName));
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
}
