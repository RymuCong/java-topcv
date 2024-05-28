package com.t3h.topcv.controller;

import com.t3h.topcv.dao.RoleDaoImpl;
import com.t3h.topcv.dto.AuthRequest;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.service.UserService;
import com.t3h.topcv.user.WebUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AccountController {

//	@Autowired
	private final AuthenticationManager authenticationManager;

	private final Logger logger = Logger.getLogger(getClass().getName());

	private final UserService userService;

	RoleDaoImpl roleDao;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public AccountController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
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

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		System.out.println("AuthRequest: " + authRequest);
		return ResponseEntity.ok(userService.login(authRequest));
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
