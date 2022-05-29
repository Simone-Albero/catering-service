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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.model.Nation;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.ChefService;

@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@PostMapping("/chef")
	public String addChef(@Valid @ModelAttribute("chef")Chef chef, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			FileStorer.store(file, chef.getName());
			
			this.chefService.save(chef);
			model.addAttribute("chef", this.chefService.findById(chef.getId()));
			return "chef.html";
		}
		else return "chefForm.html";
	}
	
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return "chef.html";
	}
	
	@GetMapping("/chefs")
	public String getChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chefs.html";
	}
	
	@GetMapping("/deleteChef/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		this.chefService.deleteById(id);
		return "index.html";
	}
	
	@GetMapping("/chefForm")
	public String getForm(Model model) {
		Chef chef = new Chef();
		Nation nation = new Nation(); /**non lo so a priori, ma lanazione potrebbe già esistere**/
		chef.setNation(nation);
		
		model.addAttribute("chef", chef);
		model.addAttribute("nation", nation);
		return "chefForm.html";
	}
	
	
}
