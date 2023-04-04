package com.application.workmanagement.logic;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.common.exception.UserNotFoundException;
import com.application.workmanagement.domain.model.Users;
import com.application.workmanagement.domain.repository.UserRepository;
import com.application.workmanagement.service.rest.v1.model.UsersDto;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public void addUser(UsersDto user) {
		Users addedUser = modelMapper.map(user, Users.class);
		addedUser.setPassword(passwordEncoder.encode(user.getPassword()));
		addedUser.setRole("Admin");
		
		userRepository.save(addedUser);
	}

	public List<UsersDto> getUsers() {
		List<UsersDto> users = userRepository.findAll().stream().map(user -> modelMapper.map(user, UsersDto.class)).collect(Collectors.toList());
		return users;
	}

	public void deleteUsers() {
		userRepository.deleteAll();
		
	}

	public String validateUser(UsersDto user) {
		List<UsersDto> users = userRepository.findByUsername(user.getUsername()).stream().map(u -> modelMapper.map(u, UsersDto.class)).collect(Collectors.toList());

		if(passwordEncoder.matches(user.getPassword(), users.get(0).getPassword())) {
			
			return "User is Authenticated";
		}
		else {
			throw new UserNotFoundException("User is not Authenticated");
		}
	}

}
