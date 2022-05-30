package it.uniroma3.catering.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.model.Nation;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.ChefService;

@Controller
@RequestMapping("/chef")
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@PostMapping("/add")
	public String addChef(@Valid @ModelAttribute("chef")Chef chef, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			chef.setImg(FileStorer.store(file, chef.getSurname()));
			
			this.chefService.save(chef);
			model.addAttribute("chef", this.chefService.findById(chef.getId()));
			return "chef.html";
		}
		else return "chefForm.html";
	}
	
	@GetMapping("/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return "chef.html";
	}
	
	@GetMapping("/all")
	public String getChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chefs.html";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		this.chefService.deleteById(id);
		return "index.html";
	}
	
	@GetMapping("/form")
	public String getForm(Model model) {
		Chef chef = new Chef();
		chef.setNation(new Nation());
		
		/**Gestire il controllo della nazione**/
		model.addAttribute("chef", chef);
		return "chefForm.html";
	}
	
	
}
