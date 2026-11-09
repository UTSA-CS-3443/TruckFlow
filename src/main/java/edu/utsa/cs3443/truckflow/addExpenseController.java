package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Expense;
import edu.utsa.cs3443.truckflow.model.ExpenseManager;
import edu.utsa.cs3443.truckflow.model.dataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


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
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView addimage;


    private dataManager data= new dataManager();
    private ExpenseManager expense = new ExpenseManager(data, data.getExpense());


    // method for cancel button to go back to the ExpenseDashboard.fxml
    public void initialize() throws IOException {

        data.loadExpenses();

        Image addScreen = new Image(getClass().getResource("/edu/utsa/cs3443/truckflow/images/AddScreens.png").toExternalForm());
        addimage.setImage(addScreen);
// set the image


}
    public void onCancelButton()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");




    }
public void onExpenseAdd() throws IOException {

// start getting the input from the user

    LocalDate datepicker = date.getValue();
    String categoryInput = categoryText.getText();
    String descriptionInput = descriptionText.getText();
    double amountInput = Double.parseDouble(amountText.getText());
    String paymentInput = paymentType.getText();
    String notesInput = Notes.getText();
    // assgin variables to input feilds

    // check if user has inputed all the required inputs to do it later
  if(date.getValue() == null){
      errorLabel.setText("Please enter a valid date");
      return;
  } else if (categoryText.getText().isEmpty()) {
      errorLabel.setText("Please enter a input for a category");
      return;
  } else if (descriptionText.getText().isEmpty()) {
      errorLabel.setText("Pelase enter a input for a description ");
      return;
  } else if (amountText.getText().isEmpty()) {
    errorLabel.setText("Please enter a amount paid ");
    return;
  } else if (paymentType.getText().isEmpty()) {
      errorLabel.setText("Please enter a payment Type that the expense was paid with ");
      return;
  } else {
        // if not null create and add a expense
        Expense newExpense = new Expense(datepicker, categoryInput, descriptionInput, amountInput, paymentInput, notesInput); // call the constructor and pass the assgined variables to it

        // add the new expenses to the arrayList
      expense.addExpense(newExpense);
        addedExpenseClicked();
  }





}

private void addedExpenseClicked()throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) addExpensebutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

}





    }
