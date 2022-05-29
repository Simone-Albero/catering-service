package it.uniroma3.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.model.Ingradient;
import it.uniroma3.presentation.FileStorer;
import it.uniroma3.service.IngradientService;

@Controller
public class IngradientController {

	@Autowired
	private IngradientService ingradientService;
	

	@PostMapping("/ingradient")
	public String addIngradient(@Valid @ModelAttribute("ingradient")Ingradient ingradient, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			FileStorer.store(file, ingradient.getName()); /**TO-DO: da sistemare**/
			
			this.ingradientService.save(ingradient);
			model.addAttribute("ingradient", this.ingradientService.findById(ingradient.getId()));
			return "ingradient.html";
		}
		else return "ingradientForm.html";
	}
	
	@GetMapping("/ingradient/{id}")
	public String getIngradient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingradient", this.ingradientService.findById(id));
		return "ingradient.html";
	}
	
	@GetMapping("/ingradients")
	public String getIngradients(Model model) {
		model.addAttribute("ingradients", this.ingradientService.findAll());
		return "ingradients.html";
	}
	
	@GetMapping("/ingradient/{id}")
	public String deleteIngradient(@PathVariable("id") Long id, Model model) {
		this.ingradientService.deleteById(id);
		return "index.html";
	}
	
	@GetMapping("/ingradientForm")
	public String getForm(Model model) {
		model.addAttribute("ingradient", new Ingradient());
		return "ingradientForm.html";
	}
	
	
	
}
