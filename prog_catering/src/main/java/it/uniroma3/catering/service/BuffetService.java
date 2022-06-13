package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired
	private BuffetRepository buffetRepo;
	
	public void save(Buffet buffet) {
		this.buffetRepo.save(buffet);
	}
	
	public Buffet findById(Long id) {
		return this.buffetRepo.findById(id).get();
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<Buffet>();
		
		for (Buffet b : this.buffetRepo.findAll()) {
			buffets.add(b);
		}
		return buffets;
	}
	
	public List<Buffet> findAllByChef(Chef chef){
		List<Buffet> buffets = new ArrayList<Buffet>();
		
		for (Buffet b : this.buffetRepo.findAllByChef(chef)) {
			buffets.add(b);
		}
		return buffets;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.buffetRepo.deleteById(id);
	}

	public boolean alreadyExists(Buffet buffet) {
		Buffet that = this.buffetRepo.findByName(buffet.getName());
		if (that != null)
			return true;
		else 
			return false;
	}
	
}
