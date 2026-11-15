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

    public boolean updateExpense(Expense expense,LocalDate date,String category,String description,double Amount,String payment_Method,
                              String Notes)throws  IOException{


        LocalDate olddata = expense.getDate();
        String oldCategory = expense.getCategory();
        String oldDescription = expense.getDescription();
        double oldAmount = expense.getAmount();
        String oldpaymentMethod = expense.getPayment_Method();
        String oldNotes = expense.getNotes();
        try{
            expense.setDate(date);
            expense.setCategory(category);
            expense.setDescription(description);
            expense.setAmount(Amount);
            expense.setPayment_Method(payment_Method);
            expense.setNotes(Notes);

            dataManager.saveDataToFile();
            return true;
        }catch (IllegalArgumentException | IOException e){
            expense.setDate(olddata);
            expense.setCategory(oldCategory);
            expense.setDescription(oldDescription);
            expense.setAmount(oldAmount);
            expense.setPayment_Method(oldpaymentMethod);
            expense.setNotes(oldNotes);
            throw e;
        }

    }
    public double addExpensesCost(){
        double total =0;
        for(Expense expenses: expense){
            total += expenses.getAmount();
        }
        return total;

    }

    //filter by date range methods
    public ArrayList<Expense> filterByDateRange(LocalDate datepicker1, LocalDate datepicker2){                                            // look into fixing the type of method it calls because its going to print alot
        // filter by date range
        ArrayList<Expense> foundExpenses = new ArrayList<>();
        for (Expense date : this.expense) {
            if (!date.getDate().isBefore(datepicker1) && !date.getDate().isAfter(datepicker2)) {
                // Print the matching expense here
                foundExpenses.add(date);
            }
        }
        return foundExpenses;
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
