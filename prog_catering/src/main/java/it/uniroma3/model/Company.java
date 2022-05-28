package it.uniroma3.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Buffet> buffets;
	
	@OneToMany
	private List<User> users;

	public Company() {
		this.buffets = new ArrayList<Buffet>();
		this.users = new ArrayList<User>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Buffet> getBuffet() {
		return buffets;
	}

	public void setBuffet(List<Buffet> buffet) {
		this.buffets = buffet;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
