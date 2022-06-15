package it.uniroma3.catering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.catering.controller.validator.ReviewValidator;
import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.model.Review;
import it.uniroma3.catering.service.BuffetService;
import it.uniroma3.catering.service.ReviewService;

@Controller
@RequestMapping("/review")
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ReviewValidator reviewValidator;
	
	
	@PostMapping("/add")
	public String addReview(@ModelAttribute("review")Review review, BindingResult bindingResult, Model model) {
		this.reviewValidator.validate(review, bindingResult);
		if(!bindingResult.hasErrors()) {
			review.getBuffet().addReview(review);
			this.buffetService.save(review.getBuffet());
			
			Buffet buffet = review.getBuffet();
			model.addAttribute("buffet", buffet);
			model.addAttribute("reviews", buffet.getReviews());
			return "/buffet/info";
		}
		else return "/review/form";
	}
	
	@GetMapping("/{id}")
	public String getReview(@PathVariable("id") Long id, Model model) {
		model.addAttribute("review", this.reviewService.findById(id));
		return "/review/info";
	}
	
	@GetMapping("/all")
	public String getReviews(Model model) {
		model.addAttribute("reviews", this.reviewService.findAll());
		return "/review/all";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteReview(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.reviewService.findById(id).getBuffet();
		this.reviewService.deleteById(id);
		
		model.addAttribute("buffet", buffet);
		model.addAttribute("reviews", buffet.getReviews());
		return "/buffet/info";
	}
	
	/**L'id è del buffet per il quale è stata scritta la recensione **/
	@GetMapping("/form/{id}")
	public String getForm(@PathVariable("id")Long id, Model model) {
		Review review = new Review();
		Buffet buffet = this.buffetService.findById(id);
		
		review.setBuffet(buffet);
		
		model.addAttribute("review", review);
		return "/review/form";
	}
	
	@GetMapping("/modify/{id}")
	public String modifyReview(@PathVariable("id") Long id, Model model) {		
		model.addAttribute("review", this.reviewService.findById(id));
		return "review/modify";
	}
	
	@PostMapping("/modify")
	public String updateReview(@ModelAttribute("review")Review review, BindingResult bindingResult, Model model) {
		this.reviewValidator.validate(review, bindingResult);
		if(!bindingResult.hasErrors()) {
			
			this.reviewService.save(review);
			Buffet buffet = review.getBuffet();
			model.addAttribute("buffet", buffet);
			model.addAttribute("reviews", buffet.getReviews());
			return "/buffet/info";
		}
		else return "/chef/modify";
	}
	

}
