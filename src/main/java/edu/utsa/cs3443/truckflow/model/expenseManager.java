package edu.utsa.cs3443.truckflow.model;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class expenseManager {
    // attribute an arrayList
    private ArrayList<Expense> expense = new ArrayList<>();

    // defauly constructor
    public expenseManager() {
        this.expense = new ArrayList<Expense>();
    }
    // getters and setters
    public ArrayList<Expense> getExpense() {
        return expense;
    }

    public void setExpense(ArrayList<Expense> expense) {
        this.expense = expense;
    }

//    private LocalDate date;
//    private String Category;
//    private  String Description;
//    private Double Amount;
//    private  String Payment_Method;
//    private String Notes;

    /*
    // the methods
    public void addExpense(Expense expense, Scanner scan) throws IOException {

        // make it take user input to add things to a arraylist
        System.out.println("Please enter the date (YYYY-MM-DD)");
        LocalDate date = LocalDate.parse(scan.nextLine());

        System.out.println("Please enter the category type of expense");
        String expenseType = scan.nextLine();
        System.out.println("Please enter a description ( optional)");
        String description = scan.nextLine();
        System.out.println("Please enter amount paid");
        double amount = scan.nextDouble();
        System.out.println("Please enter a payment method");
        String paymentMethod = scan.nextLine();
        System.out.println("Please enter a note");
        String notes = scan.nextLine();

    // collect user input add to the arrayList
        Expense newExpense = new Expense(
                date,
                expenseType,
                description,
                amount,
                paymentMethod,
                notes
        );

        this.expense.add(newExpense);
        saveDataToFile();

    }

//    public double sumExpense(){
//         double sumExpense  = 0;
//
//          for(Expense expenseList: expense){
//             sumExpense+=expenseList.getAmount();
//            }
//
//    return sumExpense;
//}

    public boolean deleteExpense(Expense expense)throws IOException{
        for(int i = 0; i< this.expense.size();i++){
            // search through array by expenseID number
            if(this.expense.get(i).getExpenseId()== expense.getExpenseId()){
                this.expense.remove(i); // seraches for a object when found it removes it
                saveDataToFile(); // call the save method so that it saves the new information to file
                return true;
            }
        }
    System.out.println("Expense ID was not found");
        return false;
    }
// search screen start here ---------------------------
    public boolean filterByCategory(Expense expense,Scanner scan)throws IOException{        // look into fixing the type of method it calls because its going to print alot
        System.out.println("Please enter a category to filter:");
        String category = scan.nextLine();

        for(int i = 0; i< this.expense.size();i++){
            // search through array by expenseID number
            if (this.expense.get(i).getCategory().equalsIgnoreCase(category)){
                System.out.println(printSingleExpense(this.expense.get(i))); // prints a table with the categories of the search
                saveDataToFile(); // call the save method so that it saves the new information to file
                return true;
            }
        }
        System.out.println("Expense type was not found");
        return false;
    }

public void filterByDateRange(Scanner scan){                                            // look into fixing the type of method it calls because its going to print alot
        System.out.println("Please enter your starting date");
    String dateRange1 = scan.nextLine();
    LocalDate startDate = LocalDate.parse(dateRange1);

    System.out.println("Please enter your ending date");
    String dateRange2 = scan.nextLine();
    LocalDate endDate = LocalDate.parse(dateRange2);
    // filter by date range
    for (Expense date : this.expense) {
        if (!date.getDate().isBefore(startDate) && !date.getDate().isAfter(endDate)) {
            // Print the matching expense here
            System.out.println(printSingleExpense(date));
        }
    }
}

    public Expense findExpenseID(int expenseId) {
        // create for loop to loop and linerarly search the array for the registeration number
        for(Expense Id:expense){
            // if statement to check if found if not return null
            if(Id.getExpenseId()==expenseId) {
                return Id;
            }
        }
        return null;
    }
    // search screen end here ---------------------------
// update a expense
    public void updateExpense(Scanner scan) throws IOException{
        // let users enter a expenseID

        System.out.println("Please enter a expense ID");
        int id = Integer.parseInt(scan.nextLine());

        for(Expense numid: expense){
            if(numid.getExpenseId() == id) {
                System.out.println(printSingleExpense(numid));
                // ask for conformation of the user
                System.out.println("Are you syre you want to update this expense (0=no 1=yes");
                int result = Integer.parseInt(scan.nextLine());
                if (result == 0) {
                    System.out.println("Update cansled");
                    return; // exit method
                } else if (result != 1) {
                    System.out.println("Invalid choice update cancelled");
                    return;
                }
                // let the user update what they need to update
                System.out.println("Which information would you like to update?");
                System.out.println("1. Date");
                System.out.println("2. Category");
                System.out.println("3. Description");
                System.out.println("4. Amount");
                System.out.println("5. Payment Method");
                System.out.println("6. Notes");
                System.out.println("0. Cancel");

                int choice = Integer.parseInt(scan.nextLine());

                switch (choice) {
                    case 1:
                        System.out.println("Enter the new date (YYYY-MM-DD):");
                        numid.setDate(LocalDate.parse(scan.nextLine()));
                        break;

                    case 2:
                        System.out.println("Enter the new category:");
                        numid.setCategory(scan.nextLine());
                        break;

                    case 3:
                        System.out.println("Enter the new description:");
                        numid.setDescription(scan.nextLine());
                        break;

                    case 4:
                        System.out.println("Enter the new amount:");
                        numid.setAmount(Double.parseDouble(scan.nextLine()));
                        break;

                    case 5:
                        System.out.println("Enter the new payment method:");
                        numid.setPayment_Method(scan.nextLine());
                        break;

                    case 6:
                        System.out.println("Enter the new notes:");
                        numid.setNotes(scan.nextLine());
                        break;

                    case 0:
                        System.out.println("Update cancelled.");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                saveDataToFile();
                System.out.println("Expense updated successfully!");
                System.out.println(printSingleExpense(numid));
                return;

            }

        }
        System.out.println("Expense ID not found.");
    }
    private static String printSingleExpense(Expense expense) {
        StringBuilder result = new StringBuilder();

        result.append("\n");
        result.append("============================================================\n");
        result.append("                     EXPENSE DETAILS                        \n");
        result.append("============================================================\n");

        result.append(String.format("%-20s %s%n", "Expense ID:", expense.getExpenseId()));
        result.append(String.format("%-20s %s%n", "Date:", expense.getDate()));
        result.append(String.format("%-20s %s%n", "Category:", expense.getCategory()));
        result.append(String.format("%-20s %s%n", "Description:", expense.getDescription()));
        result.append(String.format("%-20s $%.2f%n", "Amount:", expense.getAmount()));
        result.append(String.format("%-20s %s%n", "Payment Method:", expense.getPayment_Method()));
        result.append(String.format("%-20s %s%n", "Notes:", expense.getNotes()));

        result.append("============================================================\n");

        return result.toString();
    }
 */

    public void addExpense(Expense expenses) throws IOException { // add expenses to the array List
        expense.add(expenses);
        saveDataToFile();

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

    // update expense

    //filter by category methods

    public  void loadExpenses() throws IOException {

        // // reads data from text files and adds a new expense object for each aid ship found in the file must throw a expection related to I/o
        // create a while loop that reads text files and adds an AidShip object for eacj ship found in the file
        // 1. make a file reader
        // 2. adds an expense object to array and then throws an error exception
        File file = new File("Data/Expenses.csv");
        Scanner scan = new Scanner(file);
        if (scan.hasNextLine()) {
            scan.nextLine(); // Skip header
        }
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] expenseData = line.split(",", -1);

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


    private void saveDataToFile() throws IOException {
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




}
