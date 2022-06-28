package it.uniroma3.catering.controller;

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

import it.uniroma3.catering.controller.validator.ChefValidator;
import it.uniroma3.catering.model.Chef;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.ChefService;

@Controller
@RequestMapping("/chef")
public class ChefController {
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ChefValidator chefValidator;
	
	@PostMapping("/admin/add")
	public String addChef(@ModelAttribute("chef")Chef chef, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			chef.setImg(FileStorer.store(file, chef.getDirectoryName()));
			
			this.chefService.save(chef);
			return this.getChefsHome(model);
		}
		else return "chef/form";
	}
	
	@GetMapping("/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return "chef/info";
	}
	
	@GetMapping("/all")
	public String getChefs(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chef/all";
	}
	
	@GetMapping("/home")
	public String getChefsHome(Model model) {
		model.addAttribute("chefs", this.chefService.findAll());
		return "chef/home";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		FileStorer.dirEmptyEndDelete(chef.getDirectoryName());
		this.chefService.deleteById(id);
		return this.getChefsHome(model);
	}
	
	@GetMapping("/admin/delete/image/{id}")
	public String deleteImage(@PathVariable("id") Long id, Model model) {
		Chef chef = this.chefService.findById(id);
		FileStorer.removeImg(chef.getDirectoryName(), chef.getImg());
		chef.setImg(null);
			
		this.chefService.save(chef);
		model.addAttribute("chef", this.chefService.findById(id));
		return "chef/modify";
	}
	
	@GetMapping("/admin/form")
	public String getForm(Model model) {
		model.addAttribute("chef", new Chef());
		return "chef/form";
	}
	
	@GetMapping("/admin/modify/{id}")
	public String modifyChef(@PathVariable("id") Long id, Model model) {
		Chef oldChef =  this.chefService.findById(id);
		model.addAttribute("chef", oldChef);
		return "chef/modify";
	}
	
	@PostMapping("/admin/modify")
	public String updateChef(@ModelAttribute("chef")Chef chef, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			FileStorer.dirRename(this.chefService.findById(chef.getId()).getDirectoryName() , chef.getDirectoryName());
			
			if(!file.isEmpty()) {
				FileStorer.removeImgAndDir(chef.getDirectoryName(), chef.getImg());
				chef.setImg(FileStorer.store(file, chef.getDirectoryName()));
			}
			
			this.chefService.save(chef);
			
			return this.getChefsHome(model);
		}
		else return "chef/modify";
	}
	
}
