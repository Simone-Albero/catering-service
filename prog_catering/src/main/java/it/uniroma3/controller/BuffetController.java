package it.uniroma3.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.model.Buffet;
import it.uniroma3.model.Chef;
import it.uniroma3.presentation.FileStorer;
import it.uniroma3.service.BuffetService;

@Controller
public class BuffetController {

	private BuffetService buffetService;
	
	@PostMapping("/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet")Buffet buffet, @RequestParam("file")MultipartFile[] files, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			for(MultipartFile file : files) {
				FileStorer.store(file, buffet.getName());
			}
			
			this.buffetService.save(buffet);
			model.addAttribute("buffet", this.buffetService.findById(buffet.getId()));
			return "buffet.html";
		}
		else return "buffetForm.html";
	}
	
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		return "buffet.html";
	}
	
	
	@GetMapping("/buffets")
	public String getBuffets(Model model) {
		model.addAttribute("buffets", this.buffetService.findAll());
		return "buffets.html";
	}
	
	@GetMapping("/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		this.buffetService.deleteById(id);
		return "index.html";
	}
	
	/*
	 * si ipotizza di avere uno chef come attributo del modello
	 */
	@GetMapping("/buffetForm")
	public String getForm(@ModelAttribute("chef")Chef chef, Model model) {
		Buffet buffet = new Buffet();
		buffet.setChef(chef);
		
		model.addAttribute("buffet", buffet);
		return "buffetForm.html";
	}
	
}
