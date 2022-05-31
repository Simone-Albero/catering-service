package it.uniroma3.catering.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.UniqueElements;

@Entity
public class Dish { //piatto

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	@UniqueElements
	private String name;
	
	@NotBlank
	@NotNull
	private String desc;
	
	@OneToMany
	private List<Ingradient> ingradients;

	public Dish() {
		this.ingradients = new ArrayList<Ingradient>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Ingradient> getIngradients() {
		return ingradients;
	}

	public void setIngradients(List<Ingradient> ingradients) {
		this.ingradients = ingradients;
	}
	
	
}
