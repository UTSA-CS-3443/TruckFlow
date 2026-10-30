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
            expenseList.updateExpense(selectedExpense);



    }
}
