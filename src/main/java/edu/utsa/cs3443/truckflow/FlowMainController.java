package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Expense;
import edu.utsa.cs3443.truckflow.model.expenseManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class FlowMainController {
    // dashboard buttons
    private static final expenseManager coordinator = new expenseManager();
    @FXML
    private Button addbutton;
    @FXML
    private Button editbutton;
    @FXML
    private  Button deletebutton;

    @FXML
    private  Button SearchButton;

    @FXML
    private Label MessageLabel; // message label to display button messages

private boolean clickedButton = false;

    // tableView stuff
    @FXML
    private TableView<Expense> expenseTable;

    @FXML //save for later
    private TableColumn<Expense, Integer> idColumn;

    @FXML
    private TableColumn<Expense, LocalDate> dateColumn;

    @FXML
    private TableColumn<Expense, String> categoryColumn;

    @FXML
    private TableColumn<Expense, String> descriptionColumn;

    @FXML
    private TableColumn<Expense, Double> amountColumn;

    @FXML
    private TableColumn<Expense, String> paymentMethod;

    @FXML
    private TableColumn<Expense, String> Notes;

    @FXML
    public void initialize() throws IOException {
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        categoryColumn.setCellValueFactory(
                new PropertyValueFactory<>("category"));

        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("description"));

        amountColumn.setCellValueFactory(
                new PropertyValueFactory<>("amount"));

        paymentMethod.setCellValueFactory(
                new PropertyValueFactory<>("payment_Method"));

        Notes.setCellValueFactory(
                new PropertyValueFactory<>("notes"));

        coordinator.loadExpenses();
        System.out.println("Expenses loaded: "
                + coordinator.getExpense().size());
        expenseTable.getItems().setAll(coordinator.getExpense());
    }


    @FXML
    public void deleteExpense(){
        MessageLabel.setText("Hello from delete"); // makes label apperar on the place that we placed it

        // if the delete button was clicked again delete a expense of the selected table
        if (!clickedButton) {
            clickedButton = true;
            MessageLabel.setText("Please select a expense record to delete");// makes label apperar on the place that we placed it
            return;
        }
        int selectedExpense = expenseTable.getSelectionModel().getSelectedIndex();
        if (selectedExpense ==-1){
            MessageLabel.setText("Please select a expense record to delete");// makes label apperar on the place that we placed it
            return;
        }
        int idTableSelection =idColumn.getCellData(selectedExpense);
//         delete the expense record
  // display a message that the expense record was deleted
        boolean deleted = coordinator.deleteExpense(idTableSelection);

        if (deleted) {

            MessageLabel.setText("Expense successfully deleted!");
        } else {
            MessageLabel.setText("Expense ID was not found.");
        }




    }

    @FXML
    public void editExpense() throws IOException {
        // Check if an expense was selected
        if (!clickedButton) {
            clickedButton = true;
            MessageLabel.setText("Please select an expense from the table to update.");// makes label apperar on the place that we placed it
            return;
        }
        // to make user select a expense from table
        Expense selectedExpense = expenseTable
                .getSelectionModel()
                .getSelectedItem();
        if(selectedExpense ==null){
        MessageLabel.setText("Please select a record first ");
        return;
        }
        clickedButton = false;
        onEditButtonClick(selectedExpense); // call the function onEditButtonClick
        // if the edit button was clicked again return a screen with prefilled info of the expense that was selected

        // call the function onEditButtonClick

    }

    // make the add button switch screens
    public void onAddbuttonClick()throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/addExpense.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) addbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Add Expense screen");
    }
    public void onEditButtonClick(Expense selectedExpense) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/updateExpense.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        UpdateExpensecontroller controller = fxmlLoader.getController();
        controller.setExpenses(selectedExpense, coordinator);


        Stage stage = (Stage) editbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");
    }


    public void onSearchButtonCLick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/updateExpense.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) editbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Search Expense screen");
    }

}
