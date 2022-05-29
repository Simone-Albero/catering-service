package it.uniroma3.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

}
