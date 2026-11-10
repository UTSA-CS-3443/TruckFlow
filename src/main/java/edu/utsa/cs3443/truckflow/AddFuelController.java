package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Fuel;
import edu.utsa.cs3443.truckflow.model.FuelManager;
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

/**
 * AddFuelController is the controller for {@code addFuel.fxml}, the screen where
 * the user types in a new fuel purchase. The input is checked, the purchase is
 * added through {@link FuelManager} (which saves it to the file), and the user
 * returns to the Fuel dashboard where the new record appears in the table.
 *
 * @author Fuel/Fuel Controller team
 */
public class AddFuelController {

    /** Adds the purchase. */
    @FXML
    private Button addFuelbutton;

    /** Goes back to the Fuel dashboard without adding anything. */
    @FXML
    private Button cancel;

    /** Input for the purchase date. */
    @FXML
    private DatePicker date;

    /** Input for the fuel station name. */
    @FXML
    private TextField stationText;

    /** Input for the price per gallon. */
    @FXML
    private TextField priceText;

    /** Input for the number of gallons. */
    @FXML
    private TextField gallonsText;

    /** Input for the total cost (optional - calculated when left empty). */
    @FXML
    private TextField totalCostText;

    /** Shows validation and file error messages. */
    @FXML
    private Label errorLabel;

    /** The picture in the corner of the screen. */
    @FXML
    private ImageView addimage;

    /** Loads and saves the data files. */
    private dataManager data = new dataManager();

    /** Does the fuel work on the loaded list. */
    private FuelManager fuelManager = new FuelManager(data, data.getFuel());

    /**
     * Sets up the screen: shows the picture, fills in today's date, and loads
     * the existing fuel records. The records must be loaded first because
     * saving writes the whole list - if they cannot be loaded, adding is
     * disabled so the existing file can never be overwritten with just one record.
     */
    @FXML
    public void initialize() {
        try {
            addimage.setImage(new Image(getClass().getResource(
                    "/edu/utsa/cs3443/truckflow/images/AddFuelScreen.jpg").toExternalForm()));
        } catch (RuntimeException e) {
            System.out.println("Could not load the fuel picture: " + e.getMessage());
        }
        date.setValue(LocalDate.now());
        try {
            data.loadFuel();
        } catch (IOException e) {
            errorLabel.setText("Could not read Data/Fuel.csv, so records cannot be added right now: "
                    + e.getMessage());
            addFuelbutton.setDisable(true);
        }
    }

    /**
     * Handles the Cancel button: returns to the Fuel dashboard.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void onCancelButton() throws IOException {
        showDashboard();
    }

    /**
     * Handles the Add Fuel button: checks every input, adds the purchase and
     * returns to the dashboard. A problem with the input or the file is shown
     * in the error label and nothing is added.
     *
     * @throws IOException if the dashboard screen file cannot be loaded
     */
    @FXML
    public void onFuelAdd() throws IOException {
        try {
            LocalDate datepicker = date.getValue();
            if (datepicker == null) {
                throw new IllegalArgumentException("Please enter a valid date.");
            }
            String station = stationText.getText() == null ? "" : stationText.getText().trim();
            if (station.isEmpty()) {
                throw new IllegalArgumentException("Please enter the fuel station name.");
            }
            double price = FuelInput.parseNumber(priceText.getText(), "price per gallon");
            double gallons = FuelInput.parseNumber(gallonsText.getText(), "total gallons");
            double total = FuelInput.totalCostOrCalculated(totalCostText.getText(), price, gallons);

            fuelManager.addFuelExpense(new Fuel(datepicker, price, station, gallons, total));
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        } catch (IOException e) {
            errorLabel.setText("Could not save the fuel record to Data/Fuel.csv: " + e.getMessage());
            return;
        }
        showDashboard();
    }

    /**
     * Shows the Fuel dashboard in this window.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    private void showDashboard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/fuelDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Fuel Dashboard");
    }
}
