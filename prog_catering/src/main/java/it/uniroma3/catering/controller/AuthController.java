package it.uniroma3.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.catering.model.Credentials;
import it.uniroma3.catering.model.User;
import it.uniroma3.catering.service.CredentialsService;
import it.uniroma3.catering.validator.CredentialsValidator;
import it.uniroma3.catering.validator.UserValidator;

@Controller
public class AuthController {
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private CredentialsValidator credentialsValidator;
	
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "/user/registerForm";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "/user/loginForm";
	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "index";
	}
	
    @GetMapping("/default")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.findByUsername(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/home";
        }
        return "/user/home";
    }
    
    @PostMapping("/registration/validate")
    public String registerUser(@ModelAttribute("user") User user, BindingResult userBindingResult, @ModelAttribute("credentials") Credentials credentials, BindingResult credentialsBindingResult, Model model) {

        this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            credentials.setUser(user);
            credentialsService.save(credentials);
            return "/user/registrationSuccessful";
        }
        return "/user/registerForm";
    }
    
    /**TODO logica admin-user**/
}
