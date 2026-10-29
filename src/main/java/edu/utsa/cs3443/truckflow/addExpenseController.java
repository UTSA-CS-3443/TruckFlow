package edu.utsa.cs3443.truckflow;

public class addExpenseController {

// buttons on the screen
    @FXML
    private Button addExpensebutton;
    @FXML
    private Button cancel;
    // input fields
    @FXML
    private DatePicker date;
    @FXML
    private TextField categoryText;
    @FXML
    private TextField descriptionText;
    @FXML
    private TextField amountText;
    @FXML
    private TextField paymentType;
    @FXML
    private TextField Notes;



    // method for cancel button to go back to the ExpenseDashboard.fxml

    public void onCancelButton()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");




    }
public void onExpenseAdd(){

// start getting the input from the user

    LocalDate datepicker = date.getValue();
    String categoryInput = categoryText.getText();
    String descriptionInput = descriptionText.getText();
    String amountInput = amountText.getText();
    String paymentInput = paymentType.getText();
    String notesInput = Notes.getText();
    // assgin variables to input feilds

    // check if user has inputed all the required inputs to do it later

    Expense newExpense = new Expense(datepicker,categoryInput,descriptionInput,Double.parseDouble(amountInput),paymentInput,notesInput); // call the constructor and pass the assgined variables to it

    // add the new expenses to the arrayList
    expenseList.addExpense(newExpense);






}





    }
