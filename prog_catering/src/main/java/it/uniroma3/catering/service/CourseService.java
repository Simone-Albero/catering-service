package it.uniroma3.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.catering.model.Course;
import it.uniroma3.catering.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepo;
	
	public void save(Course course) {
		this.courseRepo.save(course);
	}
	
	public Course findById(Long id) {
		return this.courseRepo.findById(id).get();
	}
	
	public List<Course> findAll(){
		List<Course> courses = new ArrayList<Course>();
		
		for (Course c : this.courseRepo.findAll()) {
			courses.add(c);
		}
		return courses;
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.courseRepo.deleteById(id);
	}
}
