package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Ingradient;
import it.uniroma3.catering.service.IngradientService;

@Component
public class IngradientValidator implements Validator {

	final Integer DESC_MAX_LENGTH = 200;
	final Integer MAX_LENGTH = 50;
    final Integer MIN_LENGTH = 5;
	
    @Autowired
    private IngradientService ingradientService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Ingradient.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Ingradient ingradient = (Ingradient) target;
        String name = ingradient.getName().trim();
        String description = ingradient.getDescription().trim();
        String provenance = ingradient.getProvenance().trim();

        
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
            errors.rejectValue("name", "size");
        
        if (description.length() < MIN_LENGTH || description.length() > DESC_MAX_LENGTH)
            errors.rejectValue("description", "desc.size");
        
        if (provenance.length() < MIN_LENGTH || provenance.length() > MAX_LENGTH)
            errors.rejectValue("provenance", "size");
        
        if (this.ingradientService.alreadyExists(ingradient)){
			errors.reject("ingradient.duplication");
		}
	}

}
