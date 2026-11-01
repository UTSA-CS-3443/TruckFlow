package edu.utsa.cs3443.truckflow.model;

import java.time.LocalDate;

public class Revenue {



   private LocalDate date;
    private int RevenueID;
    private String Broker_name;
    private  Double Amount_paid;
    private int DeadheadMiles;
    private  Boolean Payment_status;
    private  Double Price_per_mile;
    private static int nextID = 1; // variable for creating a ID for each expense


    public Revenue(LocalDate date, int revenueID, String broker_name, Double amount_paid, int deadheadMiles, Boolean payment_status, Double price_per_mile) {
        this.date = date;
        RevenueID = nextID++;
        Broker_name = broker_name;
        Amount_paid = amount_paid;
        DeadheadMiles = deadheadMiles;
        Payment_status = payment_status;
        Price_per_mile = price_per_mile;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getRevenueID() {
        return RevenueID;
    }

    public void setRevenueID(int revenueID) {
        RevenueID = revenueID;
    }

    public Double getAmount_paid() {
        return Amount_paid;
    }

    public void setAmount_paid(Double amount_paid) {
        Amount_paid = amount_paid;
    }

    public String getBroker_name() {
        return Broker_name;
    }

    public void setBroker_name(String broker_name) {
        Broker_name = broker_name;
    }

    public int getDeadheadMiles() {
        return DeadheadMiles;
    }

    public void setDeadheadMiles(int deadheadMiles) {
        DeadheadMiles = deadheadMiles;
    }

    public Boolean getPayment_status() {
        return Payment_status;
    }

    public void setPayment_status(Boolean payment_status) {
        Payment_status = payment_status;
    }

    public Double getPrice_per_mile() {
        return Price_per_mile;
    }

    public void setPrice_per_mile(Double price_per_mile) {
        Price_per_mile = price_per_mile;
    }


    @Override
    public String toString() {
        return "Revenue{" +
                "date=" + date +
                ", RevenueID=" + RevenueID +
                ", Broker_name='" + Broker_name + '\'' +
                ", Amount_paid=" + Amount_paid +
                ", DeadheadMiles=" + DeadheadMiles +
                ", Payment_status=" + Payment_status +
                ", Price_per_mile=" + Price_per_mile +
                '}';
    }




}
