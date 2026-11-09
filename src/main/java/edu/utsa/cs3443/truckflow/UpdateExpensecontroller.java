package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Expense;
import edu.utsa.cs3443.truckflow.model.ExpenseManager;
import edu.utsa.cs3443.truckflow.model.Revenue;
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

public class UpdateExpensecontroller {

    @FXML
    private Button updateExpensebutton;
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

    private Expense selectedExpense;

    private dataManager data= new dataManager();
    private ExpenseManager expense = new ExpenseManager(data, data.getExpense());
    @FXML
    private ImageView addimage;

    public void initialize() throws IOException{
        Image updateScreen = new Image(getClass().getResource("/edu/utsa/cs3443/truckflow/images/editScreen.png").toExternalForm());
        addimage.setImage(updateScreen);

    }

    public void setExpenses(Expense expense, dataManager manager) {
        this.selectedExpense = expense;
        this.data = manager;

        this.expense = new ExpenseManager(data, data.getExpense());

        // use getters to populate feilds on the fields for the screen

    }


    public void onCancelButton() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");


    }


    public void onUpdateExpenseClicked() throws IOException{

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
        }
        try {
            // Update the existing Expense object
            selectedExpense.setDate(date.getValue());
            selectedExpense.setCategory(categoryText.getText());
            selectedExpense.setDescription(descriptionText.getText());
            selectedExpense.setAmount(Double.parseDouble(amountText.getText()));
            selectedExpense.setPayment_Method(paymentType.getText());
            selectedExpense.setNotes(Notes.getText());

            // Save the updated expenses
            expense.updateExpense(selectedExpense);
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
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) updateExpensebutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

    }





    }

