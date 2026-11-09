package edu.utsa.cs3443.truckflow.model;

import java.io.IOException;
import java.time.LocalDate;

public class Expense {

// attributes
    private int expenseId;
    private LocalDate date;
    private String Category;
    private  String Description;
    private Double Amount;
    private  String Payment_Method;
    private String Notes;

    private static int nextID = 1; // variable for creating a ID for each expense

    // constructor


    public Expense(LocalDate date, String category, String description, Double amount, String payment_Method, String notes) {
        this.expenseId = nextID++;
        this.date = date;
        this.Category = category;
        this.Description = description;
        this.Amount = amount;
        this.Payment_Method = payment_Method;
        this. Notes = notes;
    }

    // getters and setters
    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getPayment_Method() {
        return Payment_Method;
    }

    public void setPayment_Method(String payment_Method) {
        Payment_Method = payment_Method;
    }

    public String getNotes() {
        return Notes;
    }

    public static int getNextID() {
        return nextID;
    }

    public static void setNextID(int nextID) {
        Expense.nextID = nextID;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }


// a toString following csv
    @Override
    public String toString() {
        return getDate() + "," + getCategory() + "," + getDescription() + "," +
                getAmount() + "," + getPayment_Method() + "," + getNotes();
    }












}
