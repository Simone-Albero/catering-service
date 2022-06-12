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

import it.uniroma3.catering.model.Ingradient;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.DishService;
import it.uniroma3.catering.service.IngradientService;

@Controller
@RequestMapping("/ingradient")
public class IngradientController {

	@Autowired
	private IngradientService ingradientService;
	
	@Autowired
	private DishService dishService;
	

	@PostMapping("/admin/add")
	public String addIngradient(@Valid @ModelAttribute("ingradient")Ingradient ingradient, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			ingradient.setImg(FileStorer.store(file, ingradient.getDirectoryName()));
			
			ingradient.getDish().addIngradient(ingradient);
			this.dishService.save(ingradient.getDish());
			
			this.ingradientService.save(ingradient);
			model.addAttribute("dish", ingradient.getDish());
			
			return "/dish/info";
		}
		else return "/ingradient/form";
	}
	
	@GetMapping("/{id}")
	public String getIngradient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingradient", this.ingradientService.findById(id));
		return "/ingradient/info";
	}
	
	@GetMapping("/all")
	public String getIngradients(Model model) {
		model.addAttribute("ingradients", this.ingradientService.findAll());
		return "/ingradient/all";
	}
	
	@GetMapping("/{id}/all")
	public String getIngradientsByDish(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingradients", this.ingradientService.findAllByDish(this.dishService.findById(id)));
		return "/ingradient/all";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteIngradient(@PathVariable("id") Long id, Model model) {
		Ingradient ingradient = ingradientService.findById(id);
		FileStorer.dirEmptyEndDelete(ingradient.getDirectoryName());
		this.ingradientService.deleteById(id);

		model.addAttribute("dish", ingradient.getDish());
		return "/dish/info";
	}
	
	@GetMapping("/admin/delete/image/{id}")
	public String deleteImage(@PathVariable("id") Long id, Model model) {
		Ingradient ingradient = this.ingradientService.findById(id);
		FileStorer.removeImg(ingradient.getDirectoryName(), ingradient.getImg());
		ingradient.setImg(null);
			
		this.ingradientService.save(ingradient);
		model.addAttribute("ingradient", this.ingradientService.findById(id));
		return "/ingradient/modify";
	}
	
	/**l'id e' del piatto per il quale si sta inserendo l'ingrediente**/
	@GetMapping("/admin/form/{id}")
	public String getForm(@PathVariable("id") Long id, Model model) {
		Ingradient ingradient = new Ingradient();
		ingradient.setDish(this.dishService.findById(id));	
		model.addAttribute("ingradient", ingradient);
		return "/ingradient/form";
	}
	
	@GetMapping("/admin/modify/{id}")
	public String modifyIngradient(@PathVariable("id") Long id, Model model) {
		Ingradient oldIngradient =  this.ingradientService.findById(id);
		model.addAttribute("ingradient", oldIngradient);
		return "ingradient/modify";
	}
	
	@PostMapping("/admin/modify")
	public String updateIngradient(@Valid @ModelAttribute("ingradient")Ingradient ingradient, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			FileStorer.dirRename(this.ingradientService.findById(ingradient.getId()).getDirectoryName() , ingradient.getDirectoryName());
			
			if(!file.isEmpty()) {
				FileStorer.removeImgAndDir(ingradient.getDirectoryName(), ingradient.getImg());
				ingradient.setImg(FileStorer.store(file, ingradient.getDirectoryName()));
			}
			
			this.ingradientService.save(ingradient);
			model.addAttribute("dish", ingradient.getDish());
			
			return "/dish/info";
		}
		else return "/ingradient/modify";
	}
	
	
}
