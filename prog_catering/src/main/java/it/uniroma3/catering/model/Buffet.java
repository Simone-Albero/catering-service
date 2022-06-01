package it.uniroma3.catering.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Buffet {

	private static final int MAX_IMGS = 5;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	private String name;
	
	@NotBlank
	@NotNull
	private String description;
	
	@ManyToOne
	private Chef chef;
	
	@OneToMany
	private List<Dish> dishes;
	
	private String[] imgs;

	public Buffet() {
		this.dishes = new ArrayList<Dish>();
		this.imgs = new String[MAX_IMGS];
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public String[] getImgs() {
		return imgs;
	}

	public void setImgs(String[] imgs) {
		this.imgs = imgs;
	}

	public void emptyImgst() {
		this.imgs = new String[MAX_IMGS];
	}

	public void removeImg(String img) {
		for(int i = 0; i < this.imgs.length; i++) {
			if(this.imgs[i] != null && this.imgs[i].equals(img)) this.imgs[i]=null;
		}
	}
	
	
	
}
