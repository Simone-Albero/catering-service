package it.uniroma3.catering.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	
	@OneToMany(mappedBy = "buffet")
	@Cascade(CascadeType.DELETE)
	private List<Dish> dishes;
	
	@OneToMany(mappedBy = "buffet")
	@Cascade(CascadeType.ALL)
	private List<Review> reviews;
	
	private String[] imgs;

	public Buffet() {
		this.dishes = new ArrayList<Dish>();
		this.reviews = new ArrayList<Review>();
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
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
	public void addReview(Review review) {
		this.reviews.add(review);
	}

	public String[] getImgs() {
		return imgs;
	}

	public void setImgs(String[] imgs) {
		this.imgs = imgs;
	}

	public void emptyImgs() {
		this.imgs = new String[MAX_IMGS];
	}

	public void removeImg(String img) {
		for(int i = 0; i < this.imgs.length; i++) {
			if(this.imgs[i] != null && this.imgs[i].equals(img)) this.imgs[i]=null;
		}
	}

	public void addDish(@Valid Dish dish) {
		this.dishes.add(dish);
	}
	
	public String getDirectoryName() {
		return this.chef.getDirectoryName()+"/"+ this.name.replaceAll("\\s+","_");
	}
	
	public Float avgRate(){
		Float sum = 0f;
		for(Review r : this.reviews) {
			sum+= r.getRate();
		}
		return (float) Math.round(sum/this.reviews.size());
	}
	
	public Float avgPerCentRate(){
		return (this.avgRate()*100)/5;
	}
}
