package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.service.ChefService;

@Component
public class ChefValidator implements Validator {
	
	final Integer MAX_LENGTH = 50;
    final Integer MIN_LENGTH = 5;
	
    @Autowired
    private ChefService chefService;
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Chef.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Chef chef = (Chef) target;
        String name = chef.getName().trim();
        String surname = chef.getSurname().trim();
        String nation = chef.getNation().trim();

        
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
            errors.rejectValue("name", "size");
        
        if (surname.length() < MIN_LENGTH || surname.length() > MAX_LENGTH)
            errors.rejectValue("surname", "size");
        
        if (nation.length() < MIN_LENGTH || nation.length() > MAX_LENGTH)
            errors.rejectValue("nation", "size");
        
        if(chef.getId() == null || !chef.getName().equals(this.chefService.findById(chef.getId()).getName())|| !chef.getSurname().equals(this.chefService.findById(chef.getId()).getSurname())) {
            if (this.chefService.alreadyExists(chef)){
    			errors.reject("chef.duplication");
    		}
        }

	}

}
