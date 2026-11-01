package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Expense;
import edu.utsa.cs3443.truckflow.model.Revenue;
import edu.utsa.cs3443.truckflow.model.expenseManager;
import edu.utsa.cs3443.truckflow.model.revenueManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;


public class addRevenueController {

// buttons on the screen
    @FXML
    private Button addRevenueButton;
    @FXML
    private Button cancel;
    // input fields
    @FXML
    private DatePicker date;
    @FXML
    private TextField BrokerName;
    @FXML
    private TextField amountPaidField;

    @FXML
    private TextField DeadHeadMiles;
    @FXML
    private CheckBox paidcheck;
    @FXML
    private TextField PriceperMile;

    @FXML
    private Label errorLabel;

    private revenueManager revenueList= new revenueManager();


    // method for cancel button to go back to the ExpenseDashboard.fxml
    public void initialize() throws IOException {
        revenueList.loadExpenses();

}

    public void onCancelButton()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");
    }
public void onRevenueAdd() throws IOException {

// start getting the input from the user

    LocalDate datepicker = date.getValue();
    String brokerInput = BrokerName.getText();
    Double amountInput = Double.parseDouble(amountPaidField.getText());
    int deadheadMilesInput = Integer.parseInt(DeadHeadMiles.getText());
    Boolean paymentStatus = paidcheck.isSelected();
    Double pricePerMile = Double.parseDouble(PriceperMile.getText());




    // assgin variables to input feilds

    // check if user has inputed all the required inputs to do it later
  if(date.getValue() == null){
errorLabel.setText("Please enter a date");
      return;
  } else if (BrokerName.getText().isEmpty()) {
      errorLabel.setText("Please enter the Name of a brokker ");
      return;
  } else if (amountPaidField.getText().isEmpty()) {
      errorLabel.setText("Please enter the amount paid ");
      return;
  } else if (DeadHeadMiles.getText().isEmpty()) {
      errorLabel.setText("Please enter the deadhead miles driven ");
    return;
  } else if (PriceperMile.getText().isEmpty()) {
      errorLabel.setText("Please enter the price per mile of what was driven ");
  }
  else {
        // if not null create and add a expense
      Revenue newRevenue = new Revenue(datepicker, brokerInput, amountInput, deadheadMilesInput, paymentStatus, pricePerMile); // call the constructor and pass the assgined variables to it

        // add the new expenses to the arrayList
      revenueList.addRevenue(newRevenue);
      addedRevenueClicked();
  }


}

private void addedRevenueClicked()throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) addRevenueButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

}





    }
