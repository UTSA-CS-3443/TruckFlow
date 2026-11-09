package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class RevenueDashboardController {
    // dashboard buttons
    private dataManager data= new dataManager();
    private RevenueManager revenue = new RevenueManager(data, data.getRevenue());
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
    private TableView<Revenue> RevenueTable;

    @FXML //save for later
    private TableColumn<Revenue, Integer> idColumn;

    @FXML
    private TableColumn<Revenue, LocalDate> dateColumn;

    @FXML
    private TableColumn<Revenue, String> Broker_name;

    @FXML
    private TableColumn<Revenue, String> Amount_paid;

    @FXML
    private TableColumn<Revenue, Double> DeadheadMiles;

    @FXML
    private TableColumn<Revenue, String> Payment_status;

    @FXML
    private TableColumn<Revenue, String> Price_per_mile;

    @FXML
    private ImageView revenueDB;

//    private LocalDate date;
//    private String Broker_name;
//    private  Double Amount_paid;
//    private int DeadheadMiles;
//    private  Boolean Payment_status;
//    private  Double Price_per_mile;
//    private static int nextID = 1; // variable for creating a ID for each expense
    @FXML
    public void initialize() throws IOException {
        Image revenueDBpicture = new Image(getClass().getResource("/edu/utsa/cs3443/truckflow/images/RevenueDb.jpg").toExternalForm());
        revenueDB.setImage(revenueDBpicture);

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("RevenueID"));
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        Broker_name.setCellValueFactory(
                new PropertyValueFactory<>("Broker_name"));

        Amount_paid.setCellValueFactory(
                new PropertyValueFactory<>("Amount_paid"));

        DeadheadMiles.setCellValueFactory(
                new PropertyValueFactory<>("DeadheadMiles"));

        Payment_status.setCellValueFactory(
                new PropertyValueFactory<>("Payment_status"));

        Price_per_mile.setCellValueFactory(
                new PropertyValueFactory<>("Price_per_mile"));

        data.loadRevenue();
        System.out.println("Expenses loaded: "
                + data.getExpense().size());

        RevenueTable.getItems().setAll(data.getRevenue());


    }


    @FXML
    public void deleteRevenue() throws IOException {
        // if the delete button was clicked again delete a expense of the selected table
        if (!clickedButton) {
            clickedButton = true;
            MessageLabel.setText("Please select a Revenue record to delete");// makes label apperar on the place that we placed it
            return;
        }
        int selectedExpense = RevenueTable.getSelectionModel().getSelectedIndex();
        if (selectedExpense ==-1){
            MessageLabel.setText("Please select a Revenue record to delete");// makes label apperar on the place that we placed it
            return;
        }
        int idTableSelection =idColumn.getCellData(selectedExpense);
//         delete the expense record
  // display a message that the expense record was deleted


//        boolean deleted = expense.deleteExpense(idTableSelection);
//
//        if (deleted) {
//
//            MessageLabel.setText("Revenue record successfully deleted!");
//        } else {
//            MessageLabel.setText("Revenue ID was not found.");
//        }




    }

    @FXML
    public void editRevenue() throws IOException {
        // Check if an expense was selected
        if (!clickedButton) {
            clickedButton = true;
            MessageLabel.setText("Please select a Revenue record from the table to update.");// makes label apperar on the place that we placed it
            return;
        }
        // to make user select a expense from table
        Revenue selectedExpense = RevenueTable.getSelectionModel().getSelectedItem();
        if(selectedExpense ==null){
        MessageLabel.setText("Please select a record first ");
        return;
        }
        clickedButton = false;
        onEditButtonClick(selectedExpense); // call the function onEditButtonClick
        // if the edit button was clicked again return a screen with prefilled info of the expense that was selected
    }

    // make the add button switch screens
    public void onAddbuttonClick()throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/addRevenue.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) addbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Add Expense screen");
    }
    public void onEditButtonClick(Revenue selectedExpense) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/updateRevenue.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        UpdateRevenuecontroller controller = fxmlLoader.getController();
        controller.setRevenue(selectedExpense, data);


        Stage stage = (Stage) editbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Expense screen");
    }


    public void onSearchButtonCLick() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/SearchRevenue.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        searchExpenseController searchController = fxmlLoader.getController();

        // Pass this main controller to the search controller
//        searchController.setMainController(this);      // this keyword helps on telling it that we want the FlowMain controller in the searchscreen

        Stage stage = (Stage) editbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Search Expense screen");
    }

//    public boolean returnedSearchResult(Expense foundExpense){
//        if(foundExpense !=null){
//            // return a selected row as a result
//            RevenueTable.getSelectionModel().select(foundExpense);
//            RevenueTable.scrollTo(foundExpense);
//             MessageLabel.setText("Expense was found");
//             System.out.println("Found");
//        }
//        else{
//            MessageLabel.setText("Expense was not found ");
//            System.out.println("NotFound");
//
//        }
//
//        return false;
//    }

}
