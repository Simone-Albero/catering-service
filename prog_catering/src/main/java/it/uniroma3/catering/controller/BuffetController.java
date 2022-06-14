package it.uniroma3.catering.controller;

import java.util.List;

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

import it.uniroma3.catering.controller.validator.BuffetValidator;
import it.uniroma3.catering.model.Buffet;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.BuffetService;
import it.uniroma3.catering.service.ChefService;

@Controller
@RequestMapping("/buffet")
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	
	@PostMapping("/admin/add")
	public String addBuffet(@ModelAttribute("buffet")Buffet buffet, @RequestParam("files")MultipartFile[] files, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			int i=0;
 			for(MultipartFile file : files) {
 				buffet.getImgs()[i] = FileStorer.store(file, buffet.getDirectoryName());
 				i++;
			}
 			
 			buffet.getChef().addBuffet(buffet);
 			this.chefService.save(buffet.getChef());
 			
			this.buffetService.save(buffet);
			return this.getBuffetsHome(model);
		}
		else return "/buffet/form";
	}
	
	@GetMapping("/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", this.buffetService.findById(id));
		return "/buffet/info";
	}
	
	
	@GetMapping("/all")
	public String getBuffets(Model model) {
		model.addAttribute("buffets", this.buffetService.findAll());
		return "/buffet/all";
	}

	@GetMapping("/home")
	public String getBuffetsHome(Model model) {
		List<Buffet> buffets = this.buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "/buffet/home";
	}
	
	
	@GetMapping("/{id}/all")
	public String getBuffetsByChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffets", this.buffetService.findAllByChef(this.chefService.findById(id)));
		return "/buffet/all";
	}
	
	@GetMapping("/{id}/home")
	public String getBuffetsHomeByChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffets", this.buffetService.findAllByChef(this.chefService.findById(id)));
		model.addAttribute("chef", this.chefService.findById(id));
		return "/buffet/home";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		FileStorer.removeImgsAndDir(buffet.getDirectoryName(), buffet.getImgs());
		this.buffetService.deleteById(id);
		return this.getBuffetsHome(model);
	}
	
	@GetMapping("/admin/delete/{id}/{img}")
	public String deleteImage(@PathVariable("id") Long id, @PathVariable("img") String img, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		for(String currImg : buffet.getImgs()) {
			if(currImg != null && currImg.equals(img)) {
				buffet.removeImg(img);
				FileStorer.removeImg(buffet.getDirectoryName(), img);
			}
			
		}
			
		this.buffetService.save(buffet);
		model.addAttribute("buffet", this.buffetService.findById(id));
		return "buffet/modify";
	}
	
	/**l'id e' dello chef per il quale si sta inserendo il buffet**/
	@GetMapping("/admin/form/{id}")
	public String getForm(@PathVariable("id") Long id, Model model) {
		Buffet buffet = new Buffet();
		buffet.setChef(this.chefService.findById(id));		
		model.addAttribute("buffet", buffet);
		return "/buffet/form";
	}
	
	@GetMapping("/admin/modify/{id}")
	public String modifyBuffet(@PathVariable("id") Long id, Model model) {
		Buffet oldBuffet =  this.buffetService.findById(id);
		model.addAttribute("buffet", oldBuffet);
		return "buffet/modify";
	}
	
	@PostMapping("/admin/modify")
	public String updateBuffet(@Valid @ModelAttribute("buffet")Buffet buffet, @RequestParam("files")MultipartFile[] files, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);
		if(!bindingResult.hasErrors()) {
			FileStorer.dirRename(this.buffetService.findById(buffet.getId()).getDirectoryName() , buffet.getDirectoryName());
			if (!files[0].isEmpty()) {
				FileStorer.dirEmpty(buffet.getDirectoryName());
				buffet.emptyImgs();
				int i=0;
				for(MultipartFile file : files) {
					if(!file.isEmpty()) {
						buffet.getImgs()[i]= FileStorer.store(file, buffet.getDirectoryName());
						i++;
					}
				}
			}
			this.buffetService.save(buffet);
			return this.getBuffetsHome(model);
		}
		else return "/buffet/modify";
	}
	
}
