package it.uniroma3.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.model.Ingradient;
import it.uniroma3.repository.IngradientRepository;

@Service
public class IngradientService {

	@Autowired
	private IngradientRepository ingradientRepo;
	
	public void save(Ingradient ingradient) {
		this.ingradientRepo.save(ingradient);
	}
	
	public Ingradient findById(Long id) {
		return this.ingradientRepo.findById(id).get();
	}
	
	public List<Ingradient> findAll(){
		List<Ingradient> ingradients = new ArrayList<Ingradient>();
		
		for (Ingradient i : this.ingradientRepo.findAll()) {
			ingradients.add(i);
		}
		return ingradients;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.ingradientRepo.deleteById(id);
	}
	
}
