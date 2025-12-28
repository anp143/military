package com.militaryassetmanagementsystem.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String location;

    @OneToMany(mappedBy = "base", cascade = CascadeType.ALL)
    @JsonIgnore   // 👈 prevent infinite loop
    private List<Asset> assets;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public Base(Long id, String name, String location, List<Asset> assets) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.assets = assets;
	}

	public Base() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Base [id=" + id + ", name=" + name + ", location=" + location + "]";
	}

	// ✅ Base.java
	public String getBaseName() {
	    return name;   // or whatever your base name field is called
	}

    
}
