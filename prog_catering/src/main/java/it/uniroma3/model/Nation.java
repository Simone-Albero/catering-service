package it.uniroma3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.lang.NonNull;

@Entity
public class Nation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NonNull
	@UniqueElements
	private String name;

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
	
}
