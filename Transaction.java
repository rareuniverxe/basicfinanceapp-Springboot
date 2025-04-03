package com.financeapp;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Transaction {

    @Id @GeneratedValue
    private int id;
    
    @NotNull
    @Min(0)
    private double amount;

    @NotBlank
    @Pattern(regexp = "income|expense", message = "Type must be 'income' or 'expense'")
    private String type;

    @NotBlank
    private String category;
        
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

    public Transaction() {
        // Required by JPA
    }

    public Transaction(int id, double amount, String type, String category, LocalDateTime timestamp) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }    
}
