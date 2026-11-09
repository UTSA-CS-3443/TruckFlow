package edu.utsa.cs3443.truckflow.model;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class dataManager {
    // attribute an arrayList
    private ArrayList<Expense> expense = new ArrayList<>();
    private ArrayList<Revenue> revenue = new ArrayList<>();


    // defauly constructor
    public dataManager() {
        this.expense = new ArrayList<Expense>();
        this.revenue = new ArrayList<Revenue>();
    }
    // getters and setters
    public ArrayList<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Expense> expense) {
        this.expense = expense;
    }


    // the constructor as defualt


    // generate getters and setters for the arraylist
    public ArrayList<Revenue> getRevenue() {
        return revenue;
    }
    public void setRevenue(ArrayList<Revenue> revenue) {
        this.revenue = revenue;
    }






    public  void loadExpenses() throws IOException {

        // // reads data from text files and adds a new expense object for each aid ship found in the file must throw a expection related to I/o
        // create a while loop that reads text files and adds an AidShip object for eacj ship found in the file
        // 1. make a file reader
        // 2. adds an expense object to array and then throws an error exception
      expense.clear(); // clear the expense arraylist everytime it is called to prevent duplicates in the view table
        File file = new File("Data/Expenses.csv");
        Scanner scan = new Scanner(file);
        if (scan.hasNextLine()) {
            scan.nextLine(); // Skip header
        }
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] expenseData = line.split(",", -1);
            if (line.trim().isEmpty()) {
                continue;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // add a if statement to check if no column is empty
            if (expenseData.length < 6 || expenseData[0].trim().isEmpty()) {
                continue;
            }
            Expense newExpense = new Expense(
                    LocalDate.parse(expenseData[0], formatter), // Date
                    expenseData[1],                             // Category
                    expenseData[2],                             // Description
                    Double.parseDouble(expenseData[3]),         // Amount
                    expenseData[4],                             // Payment Method
                    expenseData[5]                              // Notes
            );

            expense.add(newExpense);
        }

        scan.close();
    }

    public  void loadRevenue() throws IOException {

        // // reads data from text files and adds a new expense object for each aid ship found in the file must throw a expection related to I/o
        // create a while loop that reads text files and adds an AidShip object for eacj ship found in the file
        // 1. make a file reader
        // 2. adds an expense object to array and then throws an error exception
        revenue.clear(); // clear the expense arraylist everytime it is called to prevent duplicates in the view table
        File file = new File("Data/Revenue.csv");
        Scanner scan = new Scanner(file);
        if (scan.hasNextLine()) {
            scan.nextLine(); // Skip header
        }
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] revenueData = line.split(",", -1);
            if (line.trim().isEmpty()) {
                continue;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // add a if statement to check if no column is empty
            if (revenueData.length < 6 || revenueData[0].trim().isEmpty()) {
                continue;
            }
            Revenue newRevenue = new Revenue(
                    LocalDate.parse(revenueData[0], formatter), // Date
                    revenueData[1],                             // Broker_name
                    Double.parseDouble(revenueData[2]),         // Amount_paid
                    Integer.parseInt(revenueData[3]),           // DeadheadMiles
                    Boolean.parseBoolean(revenueData[4]),       // Payment_status
                    Double.parseDouble(revenueData[5])          // Price_per_mile
            );

            revenue.add(newRevenue);
        }

        scan.close();
    }

    public void saveDataToFile() throws IOException {
        // first make method retreive aidship object information by looping through the array
        // create instance of fileWriter
        FileWriter out = new FileWriter("Data/Expenses.csv");
        out.write("Date,Category,Description,Amount,Payment Method,Notes\n");
        for(int i = 0; i<expense.size();i++){
            Expense currentExpense  = expense.get(i);
            // outfile
            out.write(currentExpense .toString() +"\n");
        }
        out.close();
    }


    public void saveRevenueData() throws IOException {
        // first make method retreive aidship object information by looping through the array
        // create instance of fileWriter
        FileWriter out = new FileWriter("Data/Revenue.csv");
        out.write("Date,BrokerName,AmountPaid,DeadheadMiles,PaymentStatus,PricePerMile\n");
        for(int i = 0; i<revenue.size();i++){
            Revenue currentRevenue  = revenue.get(i);
            // outfile
            out.write(currentRevenue.toString() +"\n");
        }
        out.close();
    }

}
