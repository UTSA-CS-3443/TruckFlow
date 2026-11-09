package edu.utsa.cs3443.truckflow.model;

import java.io.IOException;
import java.util.ArrayList;

public class RevenueManager implements ProfitLossReport{

    private dataManager dataManager;

    private ArrayList<Revenue> revenue = new ArrayList<>();


    public RevenueManager(dataManager dataManager, ArrayList<Revenue> revenue) {
        this.dataManager = dataManager;
        this.revenue = revenue;
    }

    public void addRevenue(Revenue revenues) throws IOException { // add expenses to the array List
        revenue.add(revenues);
        dataManager.saveRevenueData();

    }


    @Override
    public double CalculateTotalRevenue() {

        return 0;
    }

    @Override
    public double calculateTotalExpenses() {

        return 0;
    }

    @Override
    public double calculateNetProfit() {

        return 0;
    }


    @Override
    public String generateProfitLossReport() {
        return "";
    }



}
