package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateRevenuecontroller {

    @FXML
    private Button updateExpensebutton;
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

    private Revenue selectedReveunue;

    private dataManager data= new dataManager();
    private RevenueManager revenue = new RevenueManager(data, data.getRevenue());

    @FXML
    private ImageView addimage;

    public void initialize() throws IOException{
        Image updateScreen = new Image(getClass().getResource("/edu/utsa/cs3443/truckflow/images/editScreen.png").toExternalForm());
        addimage.setImage(updateScreen);

    }
    public void setRevenue(Revenue revenue, dataManager manager) {
        this.selectedReveunue = revenue;
        this.data = manager;

        this.revenue = new RevenueManager(data,data.getRevenue());

    }


    public void onCancelButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/RevenueDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");


    }


    public void onUpdateExpenseClicked() throws IOException{

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
        try {
            // Update the existing Expense object
            selectedReveunue.setDate(date.getValue());
            selectedReveunue.setBroker_name(BrokerName.getText());
            selectedReveunue.setAmount_paid(Double.parseDouble(amountPaidField.getText()));
            selectedReveunue.setDeadheadMiles(Integer.parseInt(DeadHeadMiles.getText()));
            selectedReveunue.setPayment_status(paidcheck.isSelected());
            selectedReveunue.setPrice_per_mile(Double.parseDouble(PriceperMile.getText()));

            // Save the updated expenses
//            revenueList.updateExpense(selectedExpense);
            // add a label to know user that the expense has been updated here and then call the method
            switchUpdateScreen();



        } catch (NumberFormatException e) {
            errorLabel.setText("Please enter a valid amount.");
        } catch (IOException e) {
            errorLabel.setText("Unable to save the updated expense.");
            e.printStackTrace();
        }
    }

    private void switchUpdateScreen()throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/RevenueDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) updateExpensebutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

    }





    }

