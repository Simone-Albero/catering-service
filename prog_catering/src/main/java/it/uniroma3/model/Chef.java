package it.uniroma3.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;

@Entity
public class Chef {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NonNull
	private String name;
	
	@NotBlank
	@NonNull
	private String surname;
	
	@ManyToOne
	private Nation nation;
	
	@OneToMany(mappedBy = "chef")
	private List<Buffet> buffet;

	public Chef() {
		this.buffet = new ArrayList<Buffet>();
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	public List<Buffet> getBuffet() {
		return buffet;
	}

	public void setBuffet(List<Buffet> buffet) {
		this.buffet = buffet;
	}
	
}
