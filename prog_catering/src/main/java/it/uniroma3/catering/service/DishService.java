package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Dish;
import it.uniroma3.catering.repository.DishRepository;

@Service
public class DishService {
	
	@Autowired
	private DishRepository dishRepo;
	
	public void save(Dish course) {
		this.dishRepo.save(course);
	}
	
	public Dish findById(Long id) {
		return this.dishRepo.findById(id).get();
	}
	
	public List<Dish> findAll(){
		List<Dish> courses = new ArrayList<Dish>();
		
		for (Dish c : this.dishRepo.findAll()) {
			courses.add(c);
		}
		return courses;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.dishRepo.deleteById(id);
	}
}