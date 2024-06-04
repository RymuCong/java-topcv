package com.t3h.topcv.controller;

import com.t3h.topcv.dao.RoleDaoImpl;
import com.t3h.topcv.dto.AuthRequest;
import com.t3h.topcv.entity.Account;
import com.t3h.topcv.entity.candidate.Candidate;
import com.t3h.topcv.entity.company.Address_Company;
import com.t3h.topcv.entity.company.Company;
import com.t3h.topcv.repository.Company.AddressCompanyRepository;
import com.t3h.topcv.service.Candidate.CandidateService;
import com.t3h.topcv.service.CompanyService;
import com.t3h.topcv.service.UserService;
import com.t3h.topcv.user.CompanyUser;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

	private final CandidateService candidateService;

	private final CompanyService companyService;

	private final AddressCompanyRepository addressCompanyRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	public AccountController(AuthenticationManager authenticationManager, UserService userService, CandidateService candidateService, CompanyService companyService, AddressCompanyRepository addressCompanyRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.candidateService = candidateService;
        this.companyService = companyService;
        this.addressCompanyRepository = addressCompanyRepository;
    }

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {

		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	@PostMapping("/register")
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
		userService.saveCandidate(theWebUser);

		// create candidate and store in the database
		Candidate candidate = new Candidate();
		candidate.setName(theWebUser.getFullName());
		Account account = userService.findByUserName(userName);
		candidate.setAccount(account);
		Candidate candidateTemp = candidateService.createCandidate(candidate,account.getId());
		candidateService.save(candidateTemp);
		logger.info("Successfully created account: " + userName);

		return new ResponseEntity<>(theWebUser, HttpStatus.CREATED);
	}

	@PostMapping("/register-company")
	public ResponseEntity<?> createCompany(
			@Valid @RequestBody CompanyUser theCompanyUser,
			BindingResult theBindingResult) {

		String userName = theCompanyUser.getUsername();
		logger.info("Processing company registration form for: " + userName);

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
		userService.saveCompany(theCompanyUser);

		// create company and store in the database
		Company company = new Company();
		company.setName(theCompanyUser.getName());
		Account account = userService.findByUserName(userName);
		company.setAccount(account);
		Company companyTemp = companyService.create(company,account.getId());
		companyService.save(companyTemp);

		// add address to company
		Address_Company addressCompany = new Address_Company();
		addressCompany.setAddress(theCompanyUser.getAddress());
		addressCompany.setStatus("1");
		addressCompany.setCreatedAt(new Date().toString());
		addressCompany.setUpdatedAt(new Date().toString());
		addressCompany.setCompanyId(company);

		// Check if address already exists
		Address_Company existingAddress = addressCompanyRepository.findByAddress(theCompanyUser.getAddress());
		if (existingAddress != null) {
			logger.warning("Address already exists.");
			return new ResponseEntity<>("Address already exists.", HttpStatus.CONFLICT);
		}

		addressCompanyRepository.save(addressCompany);

		logger.info("Successfully created account: " + userName);

		return new ResponseEntity<>(theCompanyUser, HttpStatus.CREATED);
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
