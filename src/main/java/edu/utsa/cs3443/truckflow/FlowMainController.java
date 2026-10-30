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



    // tableView stuff
    @FXML
    private TableView<Expense> expenseTable;

//    @FXML save for later
//    private TableColumn<Expense, Integer> idColumn;

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


    }

    @FXML
    public void editExpense(){
        MessageLabel.setText("Hello from edit"); // makes label apperar on the place that we placed it

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
    public void onEditButtonClick() throws IOException{
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
