package com.eresh.spring.rest;

import com.eresh.spring.service.UserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By Gorantla, Eresh on 23/Dec/2019
 **/
@RestController
@RequestMapping("/api")
public class UserDetailsController {

	@Autowired
	UserDetailsService userDetailsService;

	@PutMapping("/users/normal")
	public ResponseEntity<String> saveUsers() {
		userDetailsService.saveUserDetails();
		return ResponseEntity.ok(HttpStatus.OK.toString());
	}

	@PutMapping("/users/bulk")
	public ResponseEntity<String> saveBulkUsers() {
		userDetailsService.bulkOperation();
		return ResponseEntity.ok(HttpStatus.OK.toString());
	}
}
