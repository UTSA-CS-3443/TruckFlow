package edu.utsa.cs3443.truckflow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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




public void setExpenses(Expense expense){
    // use getters to populate feilds on the fields for the screen
    date.setValue(expense.getDate());
    categoryText.setText(expense.getCategory());
    descriptionText.setText(expense.getDescription());
    amountText.setText(String.valueOf(expense.getAmount()));
    paymentType.setText(expense.getPayment_Method());
    Notes.setText(expense.getNotes());

}















    public void onCancelButton()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");




    }
}
