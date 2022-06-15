package it.uniroma3.catering.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.catering.model.Review;
import it.uniroma3.catering.service.ReviewService;

@Component
public class ReviewValidator implements Validator {
	
	final Integer DESC_MAX_LENGTH = 200;
	final Integer MAX_LENGTH = 50;
    final Integer MIN_LENGTH = 2;
    
    @Autowired
    private ReviewService reviewService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Review.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Review review = (Review) target;
        String name = review.getName().trim();
        String description = review.getDescription().trim();

        
        if (name.length() < MIN_LENGTH || name.length() > MAX_LENGTH)
            errors.rejectValue("name", "size");
        
        if (description.length() < MIN_LENGTH || description.length() > DESC_MAX_LENGTH)
            errors.rejectValue("description", "desc.size");
        
        if(review.getId() == null || !review.getName().equals(this.reviewService.findById(review.getId()).getName())) {
        	if (this.reviewService.alreadyExists(review)){
    			errors.reject("review.duplication");
    		}
        }

	}

}
