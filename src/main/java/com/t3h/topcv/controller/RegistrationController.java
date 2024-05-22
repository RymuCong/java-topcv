package com.t3h.topcv.controller;

import com.t3h.topcv.entity.Account;
import com.t3h.topcv.service.UserService;
import com.t3h.topcv.user.WebUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@Controller
@RequestMapping("/register")
public class RegistrationController {

	private final Logger logger = Logger.getLogger(getClass().getName());

    private final UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;
	}

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		
		theModel.addAttribute("webAccount", new WebUser());
		
		return "register/registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(
			@Valid @ModelAttribute("webAccount") WebUser theWebUser,
			BindingResult theBindingResult,
			HttpSession session, Model theModel) {

		String userName = theWebUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		
		// form validation
		 if (theBindingResult.hasErrors()){
			 return "register/registration-form";
		 }

		// check the database if account already exists
        Account existing = userService.findByUserName(userName);
        if (existing != null){
        	theModel.addAttribute("webAccount", new WebUser());
			theModel.addAttribute("registrationError", "Account name already exists.");

			logger.warning("Account name already exists.");
        	return "register/registration-form";
        }
        
        // create account and store in the database
        userService.save(theWebUser);
        
        logger.info("Successfully created account: " + userName);

		// place account in the web http session for later use
		session.setAttribute("account", theWebUser);

        return "register/registration-confirmation";
	}
}
