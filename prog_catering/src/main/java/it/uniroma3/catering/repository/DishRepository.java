package it.uniroma3.catering.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.catering.model.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {

}
