package it.uniroma3.catering.controller.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.User;
import it.uniroma3.catering.service.UserService;

@Component
public class UserValidator implements Validator {
	
	final Integer MAX_NAME_LENGTH = 50;
    final Integer MIN_NAME_LENGTH = 5;
	
    @Autowired
    private UserService userService;

    @Override
    public void validate(Object obj, Errors errors) {
        User user = (User) obj;
        String name = user.getName().trim();
        String surname = user.getSurname().trim();
        String email = user.getEmail().trim();


        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
            errors.rejectValue("name", "size");

        if (surname.length() < MIN_NAME_LENGTH || surname.length() > MAX_NAME_LENGTH)
            errors.rejectValue("surname", "size");

        if (!isEmailValid(email)) {
            errors.rejectValue("email", "email.invalid");
        }
        if(user.getId() == null || !user.getEmail().equals(this.userService.findById(user.getId()).getEmail())) {
            if (this.userService.findByEmail(email) != null)
                errors.rejectValue("email", "email.duplication");
        }

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }
    
    private boolean isEmailValid(String email) {
        String regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
                + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        return Pattern.compile(regexPattern).matcher(email).matches();
    }

}
