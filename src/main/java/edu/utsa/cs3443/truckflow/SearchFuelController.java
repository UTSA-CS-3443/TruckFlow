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
import java.util.ArrayList;

/**
 * SearchFuelController is the controller for {@code SearchFuel.fxml}. The user can
 * search the fuel records by a date range (use the same date twice to find one
 * day), by the fuel station name, by the fuel ID, or any mix of these - a
 * record must match everything that was filled in. The matches are shown in the
 * Fuel dashboard's table.
 *
 * @author Fuel/Fuel Controller team
 */
public class SearchFuelController {

    /** Start of the date range. */
    @FXML
    private DatePicker dateRange1;

    /** End of the date range. */
    @FXML
    private DatePicker dateRange2;

    /** Input for a fuel ID. */
    @FXML
    private TextField fuelID;

    /** Input for (part of) a fuel station name. */
    @FXML
    private TextField stationText;

    /** Goes back to the Fuel dashboard. */
    @FXML
    private Button cancelButton;

    /** Runs the search. */
    @FXML
    private Button searchRecordButton;

    /** Shows validation and "nothing found" messages. */
    @FXML
    private Label errorLabel;

    /** The picture in the corner of the screen. */
    @FXML
    private ImageView addimage;

    /** Loads and saves the data files. */
    private dataManager data = new dataManager();

    /** Does the searching on the loaded list. */
    private FuelManager fuelManager = new FuelManager(data, data.getFuel());

    /**
     * Sets up the screen: shows the picture and loads the fuel records to
     * search. If they cannot be loaded the search is disabled.
     */
    @FXML
    public void initialize() {
        try {
            addimage.setImage(new Image(getClass().getResource(
                    "/edu/utsa/cs3443/truckflow/images/SreachScreen.jpg").toExternalForm()));
        } catch (RuntimeException e) {
            System.out.println("Could not load the picture: " + e.getMessage());
        }
        try {
            data.loadFuel();
        } catch (IOException e) {
            errorLabel.setText("Could not read Data/Fuel.csv, so searching is not available: "
                    + e.getMessage());
            searchRecordButton.setDisable(true);
        }
    }

    /**
     * Handles the Cancel button: returns to the Fuel dashboard.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void CancelButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/fuelDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Fuel Dashboard");
    }

    /**
     * Handles the Search button: checks the input, finds the matching records
     * and shows them in the Fuel dashboard. If the input is wrong, or nothing
     * matches, a message is shown here and the user stays on this screen.
     *
     * @throws IOException if the dashboard screen file cannot be loaded
     */
    @FXML
    public void onSearchRecordButton() throws IOException {
        LocalDate start = dateRange1.getValue();
        LocalDate end = dateRange2.getValue();
        String station = stationText.getText() == null ? "" : stationText.getText().trim();
        String idText = fuelID.getText() == null ? "" : fuelID.getText().trim();

        if (start == null && end == null && station.isEmpty() && idText.isEmpty()) {
            errorLabel.setText("Please enter a date range, a station name or a fuel ID to search for.");
            return;
        }
        if (start != null && end != null && start.isAfter(end)) {
            errorLabel.setText("The start of the date range cannot be after the end.");
            return;
        }
        Integer id = null;
        if (!idText.isEmpty()) {
            try {
                id = Integer.parseInt(idText);
            } catch (NumberFormatException e) {
                errorLabel.setText("The fuel ID must be a whole number.");
                return;
            }
        }

        ArrayList<Fuel> results = fuelManager.retrieveFuelExpense(start, end, station);
        if (id != null) {
            final int wantedId = id;
            results.removeIf(record -> record.getFuelId() != wantedId);
        }
        if (results.isEmpty()) {
            errorLabel.setText("No fuel records matched that search.");
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/fuelDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        FuelDashboardController dashboard = fxmlLoader.getController();
        dashboard.showSearchResults(results, describe(start, end, station, idText));

        Stage stage = (Stage) searchRecordButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Fuel Dashboard");
    }

    /**
     * Builds a short description of the search, such as
     * "dates 2026-09-01 to 2026-09-30, station 'Shell'".
     *
     * @param start   start date or null
     * @param end     end date or null
     * @param station station text (may be empty)
     * @param idText  ID text (may be empty)
     * @return the description
     */
    private String describe(LocalDate start, LocalDate end, String station, String idText) {
        ArrayList<String> parts = new ArrayList<>();
        if (start != null && end != null) {
            parts.add(start.equals(end) ? "date " + start : "dates " + start + " to " + end);
        } else if (start != null) {
            parts.add("dates from " + start);
        } else if (end != null) {
            parts.add("dates up to " + end);
        }
        if (!station.isEmpty()) {
            parts.add("station '" + station + "'");
        }
        if (!idText.isEmpty()) {
            parts.add("ID " + idText);
        }
        return String.join(", ", parts);
    }
}
