package it.uniroma3.catering.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.catering.model.Dish;
import it.uniroma3.catering.model.Ingradient;

public interface IngradientRepository extends CrudRepository<Ingradient, Long> {
	public List<Ingradient> findAllByDish(Dish dish);
}
