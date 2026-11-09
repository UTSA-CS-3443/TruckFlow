package edu.utsa.cs3443.truckflow.model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseManager {
    private ArrayList<Expense> expense;
    private dataManager dataManager;

    public ExpenseManager(dataManager dataManager, ArrayList<Expense> expense) {
        this.dataManager = dataManager;
        this.expense = expense;
    }


    public void addExpense(Expense expenses) throws IOException { // add expenses to the array List
        expense.add(expenses);
        dataManager.saveDataToFile();

    }
    public Expense findExpenseID(int expenseId) { // finds expenseID from the array

        // create for loop to loop and linerarly search the array for the registeration number
        for(Expense Id:expense){
            // if statement to check if found if not return null
            if(Id.getExpenseId()==expenseId) {
                return Id;
            }
        }
        return null;
    }
    // delete expense
    public boolean deleteExpense(int expense)throws IOException{
        for(int i = 0; i< this.expense.size();i++){
            // search through array by expenseID number
            if(this.expense.get(i).getExpenseId()== expense){
                this.expense.remove(i); // seraches for a object when found it removes it
                dataManager.saveDataToFile(); // call the save method so that it saves the new information to file
                return true;

            }
        }
        return false;
    }
    // update expense(FIX THIS !!)
    public void updateExpense(Expense expense)throws  IOException{
        if(this.expense.contains(expense)){
            dataManager.saveDataToFile();
        }
    }
    //filter by date range methods
    public Expense filterByDateRange(LocalDate datepicker1, LocalDate datepicker2){                                            // look into fixing the type of method it calls because its going to print alot
        // filter by date range
        for (Expense date : this.expense) {
            if (!date.getDate().isBefore(datepicker1) && !date.getDate().isAfter(datepicker2)) {
                // Print the matching expense here
                return date;
            }
        }
        return null;
    }
    public Expense filterByCategory(String categoryInput)throws IOException{        // look into fixing the type of method it calls because its going to print alot
        for(int i = 0; i< this.expense.size();i++){
            // search through array by expenseID number
            if (this.expense.get(i).getCategory().equalsIgnoreCase(categoryInput)){
                return this.expense.get(i);
            }
        }
        return null;
    }
}
