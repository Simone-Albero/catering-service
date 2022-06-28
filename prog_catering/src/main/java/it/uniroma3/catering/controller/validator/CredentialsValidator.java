package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Credentials;
import it.uniroma3.catering.model.User;
import it.uniroma3.catering.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired
    private CredentialsService credentialsService;

    final Integer MAX_USERNAME_LENGTH = 20;
    final Integer MIN_USERNAME_LENGTH = 4;
    final Integer MAX_PASSWORD_LENGTH = 20;
    final Integer MIN_PASSWORD_LENGTH = 6;

    @Override
    public void validate(Object o, Errors errors) {
        Credentials credentials = (Credentials) o;
        String username = credentials.getUsername().trim();


        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH)
            errors.rejectValue("username", "username.size");
        
        if(credentials.getId() == null || !credentials.getUsername().equals(this.credentialsService.findById(credentials.getId()).getUsername())) {
        	if (this.credentialsService.findByUsername(username) != null)
                errors.rejectValue("username", "username.duplication");
        }
        
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Credentials.class.equals(clazz);
    }

}
