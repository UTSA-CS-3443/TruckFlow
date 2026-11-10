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
 * UpdateFuelController is the controller for {@code updateFuel.fxml}, the screen
 * where the user edits an existing fuel purchase. The dashboard hands over the
 * selected record with {@link #setFuel(Fuel, dataManager)}, the fields are
 * filled in with its current values, and pressing Update checks the new values
 * and saves them through {@link FuelManager}.
 *
 * @author Fuel/Fuel Controller team
 */
public class UpdateFuelController {

    /** Saves the changes. */
    @FXML
    private Button updateFuelbutton;

    /** Goes back to the Fuel dashboard without changing anything. */
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

    /** Input for the total cost (calculated when left empty). */
    @FXML
    private TextField totalCostText;

    /** Shows validation and file error messages. */
    @FXML
    private Label errorLabel;

    /** The picture in the corner of the screen. */
    @FXML
    private ImageView addimage;

    /** The record being edited. */
    private Fuel selectedFuel;

    /** Loads and saves the data files. */
    private dataManager data = new dataManager();

    /** Does the fuel work on the list. */
    private FuelManager fuelManager = new FuelManager(data, data.getFuel());

    /**
     * Sets up the screen by showing its picture.
     */
    @FXML
    public void initialize() {
        try {
            addimage.setImage(new Image(getClass().getResource(
                    "/edu/utsa/cs3443/truckflow/images/editScreen.png").toExternalForm()));
        } catch (RuntimeException e) {
            System.out.println("Could not load the picture: " + e.getMessage());
        }
    }

    /**
     * Receives the record to edit from the dashboard and fills the input
     * fields with its current values.
     *
     * @param fuel    the record the user selected
     * @param manager the data manager that holds the loaded fuel list
     */
    public void setFuel(Fuel fuel, dataManager manager) {
        this.selectedFuel = fuel;
        this.data = manager;
        this.fuelManager = new FuelManager(data, data.getFuel());

        date.setValue(fuel.getDate());
        stationText.setText(fuel.getFuelStationName());
        priceText.setText(String.valueOf(fuel.getPricePerGallon()));
        gallonsText.setText(String.valueOf(fuel.getTotalGallons()));
        totalCostText.setText(String.valueOf(fuel.getTotalCost()));
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
     * Handles the Update button: checks every input and saves the changes.
     * If an input is invalid or the file cannot be written, the record keeps
     * its old values and the problem is shown in the error label.
     *
     * @throws IOException if the dashboard screen file cannot be loaded
     */
    @FXML
    public void onUpdateFuelClicked() throws IOException {
        try {
            LocalDate datepicker = date.getValue();
            if (datepicker == null) {
                throw new IllegalArgumentException("Please enter a valid date.");
            }
            double price = FuelInput.parseNumber(priceText.getText(), "price per gallon");
            double gallons = FuelInput.parseNumber(gallonsText.getText(), "total gallons");
            double total = FuelInput.totalCostOrCalculated(totalCostText.getText(), price, gallons);

            if (!fuelManager.updateFuelRecord(selectedFuel, datepicker, price,
                    stationText.getText(), gallons, total)) {
                errorLabel.setText("That fuel record could not be found. Go back and select it again.");
                return;
            }
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        } catch (IOException e) {
            errorLabel.setText("Could not save the changes to Data/Fuel.csv: " + e.getMessage());
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
