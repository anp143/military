package com.militaryassetmanagementsystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;

    private int quantity;

    private LocalDate date;

    private String personnel; // Optional for ASSIGNMENT

    // Many transactions can belong to one asset
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;


    // From base (optional)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_base_id", nullable = true)
    private Base fromBase;

    // To base (for TRANSFER)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_base_id", nullable = true)
    private Base toBase;

    // Constructors
    public Transaction() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getPersonnel() { return personnel; }
    public void setPersonnel(String personnel) { this.personnel = personnel; }

    public Asset getAsset() { return asset; }
    public void setAsset(Asset asset) { this.asset = asset; }

    public Base getFromBase() { return fromBase; }
    public void setFromBase(Base fromBase) { this.fromBase = fromBase; }

    public Base getToBase() { return toBase; }
    public void setToBase(Base toBase) { this.toBase = toBase; }

	
}
