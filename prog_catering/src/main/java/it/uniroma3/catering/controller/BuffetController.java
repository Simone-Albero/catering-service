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

	private String getDirectoryName(Buffet buffet) {
		return buffet.getChef().getName()+"_"+buffet.getChef().getSurname()+ "/" + buffet.getName();
	}
	
	
	@PostMapping("/add")
	public String addBuffet(@Valid @ModelAttribute("buffet")Buffet buffet, @RequestParam("files")MultipartFile[] files, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			int i=0;
 			for(MultipartFile file : files) {
 				buffet.getImgs()[i] = FileStorer.store(file, getDirectoryName(buffet));
 				i++;
			}
 			
			this.buffetService.save(buffet);
			model.addAttribute("buffet", this.buffetService.findById(buffet.getId()));
			return "/buffet/info";
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
	
	@GetMapping("/{id}/all")
	public String getBuffetsByChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffets", this.buffetService.findAllByChef(this.chefService.findById(id)));
		return "/buffet/all";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		FileStorer.removeImgsAndDir(getDirectoryName(buffet), buffet.getImgs());
		this.buffetService.deleteById(id);
		return "index.html";
	}
	
	@GetMapping("/delete/{id}/{img}")
	public String deleteImage(@PathVariable("id") Long id, @PathVariable("img") String img, Model model) {
		Buffet buffet = this.buffetService.findById(id);
		for(String currImg : buffet.getImgs()) {
			if(currImg != null && currImg.equals(img)) {
				buffet.removeImg(img);
				FileStorer.removeImg(getDirectoryName(buffet), img);
			}
			
		}
			
		this.buffetService.save(buffet);
		model.addAttribute("buffet", this.buffetService.findById(id));
		return "buffet/modify";
	}
	
	/*
	 * si ipotizza di avere uno chef come attributo del modello
	 */
	@GetMapping("/form/{id}")
	public String getForm(@PathVariable("id") Long id, Model model) {
		Buffet buffet = new Buffet();
		buffet.setChef(this.chefService.findById(id));		
		model.addAttribute("buffet", buffet);
		return "/buffet/form";
	}
	
	@GetMapping("/modify/{id}")
	public String modifyBuffet(@PathVariable("id") Long id, Model model) {
		Buffet oldBuffet =  this.buffetService.findById(id);
		model.addAttribute("buffet", oldBuffet);
		return "buffet/modify";
	}
	
	@PostMapping("/modify")
	public String updateBuffet(@Valid @ModelAttribute("buffet")Buffet buffet, @RequestParam("files")MultipartFile[] files, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			if (files != null) {
				FileStorer.dirEmpty(getDirectoryName(buffet));
				buffet.emptyImgst();
				int i=0;
				for(MultipartFile file : files) {
					if(!file.isEmpty()) {
						buffet.getImgs()[i]= FileStorer.store(file, getDirectoryName(buffet));
						i++;
					}
				}
			}
			
			this.buffetService.save(buffet);
			model.addAttribute("buffet", this.buffetService.findById(buffet.getId()));
			return "/buffet/info";
		}
		else return "/buffet/modify";
	}
	
}
