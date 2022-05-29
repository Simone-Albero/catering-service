package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired
	private ChefRepository chefRepo;
	
	public void save(Chef chef) {
		this.chefRepo.save(chef);
	}
	
	public Chef findById(Long id) {
		return this.chefRepo.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		
		for (Chef c : this.chefRepo.findAll()) {
			chefs.add(c);
		}
		return chefs;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.chefRepo.deleteById(id);
	}
}
