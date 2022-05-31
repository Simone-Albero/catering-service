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

import it.uniroma3.catering.model.Dish;
import it.uniroma3.catering.presentation.FileStorer;
import it.uniroma3.catering.service.CourseService;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	
	@PostMapping("/course")
	public String addCourse(@Valid @ModelAttribute("course")Dish course, @RequestParam("file")MultipartFile file, BindingResult bindingResult, Model model) {
		if(!bindingResult.hasErrors()) {
			FileStorer.store(file, course.getName()); /**TO-DO: da sistemare**/
			
			this.courseService.save(course);
			model.addAttribute("course", this.courseService.findById(course.getId()));
			return "course.html";
		}
		else return "courseForm.html";
	}
	
	@GetMapping("/course/{id}")
	public String getCourse(@PathVariable("id") Long id, Model model) {
		model.addAttribute("course", this.courseService.findById(id));
		return "course.html";
	}
	
	@GetMapping("/courses")
	public String getCourses(Model model) {
		model.addAttribute("courses", this.courseService.findAll());
		return "courses.html";
	}
	
	@GetMapping("/deleteCourse/{id}")
	public String deleteCourse(@PathVariable("id") Long id, Model model) {
		this.courseService.deleteById(id);
		return "index.html";
	}
	
	@GetMapping("/courseForm")
	public String getForm(Model model) {
		model.addAttribute("course", new Dish());
		return "courseForm.html";
	}
	
}
