package it.uniroma3.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.model.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {
	public List<Dish> findAllByBuffet(Buffet buffet);
}
