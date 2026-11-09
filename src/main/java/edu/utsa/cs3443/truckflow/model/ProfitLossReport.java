package edu.utsa.cs3443.truckflow.model;

public interface ProfitLossReport {



    double CalculateTotalRevenue();

    double calculateTotalExpenses();

    double calculateNetProfit();

    String generateProfitLossReport();

}
