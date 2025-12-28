package com.militaryassetmanagementsystem.model;

import jakarta.persistence.*;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    // ✅ quantity column already exists in DB
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "base_id")
    private Base base;

    // 🔹 Constructors
    public Asset() {}

    public Asset(Long id, String name, String type, int quantity, Base base) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.base = base;
    }

    // 🔹 Getters & Setters
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    // 🔹 toString()
    @Override
    public String toString() {
        return "Asset [id=" + id +
               ", name=" + name +
               ", type=" + type +
               ", quantity=" + quantity +
               ", base=" + (base != null ? base.getName() : "null") + "]";
    }

    public String getAssetName() {
        return name;   // or whatever your asset name field is called
    }
}
