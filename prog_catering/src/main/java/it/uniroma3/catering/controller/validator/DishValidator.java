package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Dish;
import it.uniroma3.catering.service.DishService;

@Component
public class DishValidator implements Validator {
	
	final Integer DESC_MAX_LENGTH = 200;
	final Integer MAX_LENGTH = 50;
    final Integer MIN_LENGTH = 5;
    
    @Autowired
    private DishService dishService;
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Dish.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Dish dish = (Dish) target;
        String name = dish.getName().trim();
        String description = dish.getDescription().trim();

        
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
            errors.rejectValue("name", "size");
        
        if (description.length() < MIN_LENGTH || description.length() > DESC_MAX_LENGTH)
            errors.rejectValue("description", "desc.size");

        if(dish.getId() == null || !dish.getName().equals(this.dishService.findById(dish.getId()).getName())) {
        	if (this.dishService.alreadyExists(dish)){
    			errors.reject("dish.duplication");
    		}
        }
        
	}

}
