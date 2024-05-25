package com.t3h.topcv.controller;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.service.UserService;
import com.t3h.topcv.user.WebUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/account")
public class AccountController {

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final UserService userService;

	@Autowired
	public AccountController(UserService userService) {
		this.userService = userService;
    }

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@PostMapping("register")
	public ResponseEntity<?> createAccount(
			@Valid @RequestBody WebUser theWebUser,
			BindingResult theBindingResult) {

		String userName = theWebUser.getUserName();
		logger.info("Processing registration form for: " + userName);

		// form validation
		if (theBindingResult.hasErrors()) {
			return new ResponseEntity<>(theBindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
		}

		// check the database if account already exists
		Account existing = userService.findByUserName(userName);
		if (existing != null) {
			logger.warning("Account name already exists.");
			return new ResponseEntity<>("Account name already exists.", HttpStatus.CONFLICT);
		}

		// create account and store in the database
		userService.save(theWebUser);

		logger.info("Successfully created account: " + userName);

		return new ResponseEntity<>(theWebUser, HttpStatus.CREATED);
	}

	@GetMapping("login")
	public ResponseEntity<?> login(@RequestBody WebUser theWebUser) {
		String userName = theWebUser.getUserName();
		String password = theWebUser.getPassword();
		logger.info("Processing login form for: " + userName);

		// check the database if account already exists
		Account existing = userService.findByUserName(userName);
		if (existing == null) {
			logger.warning("Account name not exists.");
			return new ResponseEntity<>("Account name not exists.", HttpStatus.NOT_FOUND);
		}
		if (!existing.getPassword().equals(password)) {
			logger.warning("Password is incorrect.");
			return new ResponseEntity<>("Password is incorrect.", HttpStatus.UNAUTHORIZED);
		}

		logger.info("Successfully login account: " + userName);

		return new ResponseEntity<>(theWebUser, HttpStatus.OK);
	}

	@GetMapping("logout")
	public ResponseEntity<?> logout(@RequestBody WebUser theWebUser) {
		String userName = theWebUser.getUserName();
		logger.info("Processing logout form for: " + userName);

		// check the database if account already exists
		Account existing = userService.findByUserName(userName);
		if (existing == null) {
			logger.warning("Account name not exists.");
			return new ResponseEntity<>("Account name not exists.", HttpStatus.NOT_FOUND);
		}

		logger.info("Successfully logout account: " + userName);

		return new ResponseEntity<>(theWebUser, HttpStatus.OK);
	}
}
