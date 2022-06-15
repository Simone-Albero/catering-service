package it.uniroma3.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.model.Review;

public interface ReviewRepository extends CrudRepository<Review, Long> {

	public List<Review> findAllByBuffet(Buffet buffet);

	public Review findByName(String name);

}
