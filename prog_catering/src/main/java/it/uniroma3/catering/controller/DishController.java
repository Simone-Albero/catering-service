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

import it.uniroma3.catering.controller.validator.DishValidator;
import it.uniroma3.catering.model.Dish;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.BuffetService;
import it.uniroma3.catering.service.DishService;

@Controller
@RequestMapping("/dish")
public class DishController {
	
	@Autowired
	private DishService dishService;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private DishValidator dishValidator;
	
	@PostMapping("/admin/add")
	public String addDish(@ModelAttribute("dish")Dish dish, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		this.dishValidator.validate(dish, bindingResult);
		if(!bindingResult.hasErrors()) {
			dish.setImg(FileStorer.store(file, dish.getDirectoryName()));
			
			dish.getBuffet().addDish(dish);
			this.buffetService.save(dish.getBuffet());
			
			this.dishService.save(dish);
			model.addAttribute("buffet", dish.getBuffet());
			
			return "/buffet/info";
		}
		else return "/dish/form";
	}
	
	@GetMapping("/{id}")
	public String getDish(@PathVariable("id") Long id, Model model) {
		model.addAttribute("dish", this.dishService.findById(id));
		return "/dish/info";
	}
	
	@GetMapping("/all")
	public String getDishes(Model model) {
		model.addAttribute("dishes", this.dishService.findAll());
		return "/dish/all";
	}
	
	@GetMapping("/{id}/all")
	public String getDishesByBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("dishes", this.dishService.findAllByBuffet(this.buffetService.findById(id)));
		return "/dish/all";
	}
	
	
	@GetMapping("/admin/delete/{id}")
	public String deleteDish(@PathVariable("id") Long id, Model model) {
		Dish dish = dishService.findById(id);
		model.addAttribute("buffet", dish.getBuffet());
		FileStorer.dirEmptyEndDelete(dish.getDirectoryName());
		this.dishService.deleteById(id);
		
		return "/buffet/info";
	}
	
	@GetMapping("/admin/delete/image/{id}")
	public String deleteImage(@PathVariable("id") Long id, Model model) {
		Dish dish = this.dishService.findById(id);
		FileStorer.removeImg(dish.getDirectoryName(), dish.getImg());
		dish.setImg(null);
			
		this.dishService.save(dish);
		model.addAttribute("dish", this.dishService.findById(id));
		return "/dish/modify";
	}
	
	/**l'id e' del buffet per il quale si sta inserendo il piatto**/
	@GetMapping("/admin/form/{id}")
	public String getForm(@PathVariable("id") Long id, Model model) {
		Dish dish = new Dish();
		dish.setBuffet(this.buffetService.findById(id));	
		model.addAttribute("dish", dish);
		return "/dish/form";
	}
	
	@GetMapping("/admin/modify/{id}")
	public String modifyDish(@PathVariable("id") Long id, Model model) {
		Dish oldDish =  this.dishService.findById(id);
		model.addAttribute("dish", oldDish);
		return "dish/modify";
	}
	
	@PostMapping("/admin/modify")
	public String updateDish(@ModelAttribute("dish")Dish dish, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		this.dishValidator.validate(dish, bindingResult);
		if(!bindingResult.hasErrors()) {
			FileStorer.dirRename(this.dishService.findById(dish.getId()).getDirectoryName() , dish.getDirectoryName());
			
			if(!file.isEmpty()) {
				FileStorer.removeImgAndDir(dish.getDirectoryName(), dish.getImg());
				dish.setImg(FileStorer.store(file, dish.getDirectoryName()));
			}
			
			this.dishService.save(dish);
			model.addAttribute("buffet", dish.getBuffet());
			return "/buffet/info";
		}
		else return "/dish/modify";
	}
	
}
