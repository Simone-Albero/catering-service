package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.service.BuffetService;

@Component
public class BuffetValidator implements Validator {

	final Integer DESC_MAX_LENGTH = 200;
	final Integer MAX_LENGTH = 50;
    final Integer MIN_LENGTH = 2;
    
    @Autowired
    private BuffetService buffetService;
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Buffet.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Buffet buffet = (Buffet) target;
        String name = buffet.getName().trim();
        String description = buffet.getDescription().trim();

        
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
            errors.rejectValue("name", "size");
        
        if (description.length() < MIN_LENGTH || description.length() > DESC_MAX_LENGTH)
            errors.rejectValue("description", "desc.size");
        
        if(buffet.getId() == null || !buffet.getName().equals(this.buffetService.findById(buffet.getId()).getName())) {
        	if (this.buffetService.alreadyExists(buffet)){
    			errors.reject("buffet.duplication");
    		}
        }
        
	}

}
