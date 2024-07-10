package com.codingdojo.groupproject.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="medias")
public class Media {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=5, max=30, message="Name must be between 5 characters and 30 characters.")
	@NotEmpty(message="A name is required.")
	private String name;
	
	@Size(min=5, max=20, message="Category must be between 5 and 20 characters.")
	@NotEmpty(message="Category is required.")
	private String category;
	
	@Size(min=5, max=20, message="Status must be between 5 and 20 characters.")
	@NotEmpty(message="Status is required.")
	private String status;
	
	@ManyToMany(fetch =FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
    	name = "media_tags",
    	joinColumns = @JoinColumn(name = "media_id"),
    	inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
	private List<Tag> tags;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
		name= "favorite_medias",
		joinColumns = @JoinColumn(name="media_id"),
		inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private List<User> users; 
	
	@Column(updatable=false)
	private Date createdAt;
	private Date updatedAt;
 
	public Media() {}
 
	public Media(String name, String category, String status) {
		this.name = name;
		this.category = category;
		this.status = status;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setFavorites(List<User> users) {
		this.users = users;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}
	
	@PrePersist
	protected void onCreate()
	{
		this.createdAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate()
	{
		this.updatedAt = new Date();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	
	
	 
	 
}
