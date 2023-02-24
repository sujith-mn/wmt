package com.application.workmanagement.service.rest.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.workmanagement.logic.UserService;
import com.application.workmanagement.service.rest.v1.model.UsersDto;

@RestController
@RequestMapping("/registrations")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	private ResponseInfo info;
	
	@PostMapping()
	public ResponseEntity<ResponseInfo> addUsers(@RequestBody UsersDto user){
		
		userService.addUser(user);
		
		info = new ResponseInfo(HttpStatus.CREATED, user, "User added");
		return new ResponseEntity<> (info,HttpStatus.CREATED);
	}
	
	@GetMapping()
	public ResponseEntity<List<UsersDto>> getUsers(){
		
		List<UsersDto> users = userService.getUsers();
		return new ResponseEntity<> (users,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping()
	public ResponseEntity<ResponseInfo> deleteusers() {
		userService.deleteUsers();
		info = new ResponseInfo(HttpStatus.ACCEPTED, "deleted","User Deleted" );
		return new ResponseEntity<> (info,HttpStatus.ACCEPTED);
		
	}
}
