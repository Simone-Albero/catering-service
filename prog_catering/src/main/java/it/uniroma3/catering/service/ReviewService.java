package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.model.Review;
import it.uniroma3.catering.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepo;
	
	public void save(Review review) {
		this.reviewRepo.save(review);
	}
	
	public Review findById(Long id) {
		return this.reviewRepo.findById(id).get();
	}
	
	public List<Review> findAll(){
		List<Review> reviews = new ArrayList<Review>();
		
		for (Review r : this.reviewRepo.findAll()) {
			reviews.add(r);
		}
		return reviews;
	}
	
	public List<Review> findAllByBuffet(Buffet buffet){
		List<Review> reviews = new ArrayList<Review>();
		
		for (Review r : this.reviewRepo.findAllByBuffet(buffet)) {
			reviews.add(r);
		}
		return reviews;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.reviewRepo.deleteById(id);
	}

	public boolean alreadyExists(Review review) {
		Review that = this.reviewRepo.findByName(review.getName());
		if (that != null)
			return true;
		else 
			return false;
	}


	

}
