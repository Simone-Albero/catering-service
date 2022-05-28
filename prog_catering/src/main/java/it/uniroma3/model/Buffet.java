package it.uniroma3.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.NonNull;

@Entity
public class Buffet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NonNull
	private String name;
	
	@NotBlank
	@NonNull
	private String desc;
	
	@ManyToOne
	private Chef chef;
	
	private List<String> imgs;

	
	
	public Buffet() {
		this.imgs = new ArrayList<String>();
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

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

}
