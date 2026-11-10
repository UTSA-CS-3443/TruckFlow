package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.Fuel;
import edu.utsa.cs3443.truckflow.model.FuelManager;
import edu.utsa.cs3443.truckflow.model.dataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * FuelDashboardController is the controller for {@code fuelDashboard.fxml}, the
 * screen that shows every fuel purchase in a table. From here the user can add,
 * edit, delete and search fuel records, and sees the total spent and the
 * averages (via {@link FuelManager}).
 * <p>
 * The Edit and Delete buttons stay disabled until a row is selected. Delete
 * asks the user to press it a second time to confirm.
 *
 * @author Fuel/Fuel Controller team
 */
public class FuelDashboardController {

    /** Loads and saves the data files. */
    private dataManager data = new dataManager();

    /** Does the fuel work (add, update, delete, totals) on the loaded list. */
    private FuelManager fuelManager = new FuelManager(data, data.getFuel());

    /** Opens the Add Fuel screen. */
    @FXML
    private Button addbutton;

    /** Opens the Update Fuel screen for the selected record. */
    @FXML
    private Button editbutton;

    /** Deletes the selected record (after a second press to confirm). */
    @FXML
    private Button deletebutton;

    /** Opens the Search Fuel screen. */
    @FXML
    private Button SearchButton;

    /** Shows every record again after a search. */
    @FXML
    private Button showAllButton;

    /** Shows messages about what the buttons did. */
    @FXML
    private Label MessageLabel;

    /** Shows the total spent and averages for the records in the table. */
    @FXML
    private Label summaryLabel;

    /** The screen title; clicking it returns to the main menu. */
    @FXML
    private Label FuelLabel;

    /** Table that displays the fuel records. */
    @FXML
    private TableView<Fuel> fuelTable;

    /** Column showing the fuel ID. */
    @FXML
    private TableColumn<Fuel, Integer> idColumn;

    /** Column showing the purchase date. */
    @FXML
    private TableColumn<Fuel, LocalDate> dateColumn;

    /** Column showing the fuel station name. */
    @FXML
    private TableColumn<Fuel, String> stationColumn;

    /** Column showing the price per gallon. */
    @FXML
    private TableColumn<Fuel, Double> priceColumn;

    /** Column showing the gallons bought. */
    @FXML
    private TableColumn<Fuel, Double> gallonsColumn;

    /** Column showing the total cost. */
    @FXML
    private TableColumn<Fuel, Double> totalColumn;

    /** The picture in the corner of the screen. */
    @FXML
    private ImageView addimage;

    /** The record the Delete button is waiting for a second press on, or null. */
    private Fuel recordToConfirmDelete = null;

    /**
     * Sets up the screen: loads the picture, connects the table columns to the
     * Fuel getters, loads the fuel file and fills the table. Problems reading
     * the file are shown in the message label instead of crashing.
     */
    @FXML
    public void initialize() {
        try {
            addimage.setImage(new Image(getClass().getResource(
                    "/edu/utsa/cs3443/truckflow/images/FuelDB.jpg").toExternalForm()));
        } catch (RuntimeException e) {
            System.out.println("Could not load the fuel picture: " + e.getMessage());
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("fuelId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        stationColumn.setCellValueFactory(new PropertyValueFactory<>("fuelStationName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerGallon"));
        gallonsColumn.setCellValueFactory(new PropertyValueFactory<>("totalGallons"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        priceColumn.setCellFactory(column -> moneyCell("$%.3f"));
        gallonsColumn.setCellFactory(column -> moneyCell("%.2f"));
        totalColumn.setCellFactory(column -> moneyCell("$%.2f"));

        // Edit and Delete only light up once a record is selected.
        editbutton.setDisable(true);
        deletebutton.setDisable(true);
        fuelTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean nothingSelected = newValue == null;
            editbutton.setDisable(nothingSelected);
            deletebutton.setDisable(nothingSelected);
            recordToConfirmDelete = null; // choosing another row cancels a pending delete
        });

        loadAndShowAll();
    }

    /**
     * Makes a table cell that shows a number with the given format (for
     * example "$%.2f"), or nothing for an empty row.
     *
     * @param format the format String
     * @return the new table cell
     */
    private TableCell<Fuel, Double> moneyCell(String format) {
        return new TableCell<Fuel, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : String.format(format, value));
            }
        };
    }

    /**
     * Reloads the fuel file and shows every record in the table. If the file
     * cannot be read, the table is left empty and the problem is displayed.
     */
    private void loadAndShowAll() {
        try {
            data.loadFuel();
            fuelTable.getItems().setAll(data.getFuel());
            showSummary(data.getFuel());
            if (data.getSkippedFuelRows() > 0) {
                MessageLabel.setText(data.getSkippedFuelRows()
                        + " damaged row(s) in Data/Fuel.csv could not be read and were skipped.");
            }
        } catch (IOException e) {
            fuelTable.getItems().clear();
            showSummary(data.getFuel());
            MessageLabel.setText("Could not read Data/Fuel.csv: " + e.getMessage());
        }
    }

    /**
     * Updates the summary line (number of records, total spent, total
     * gallons, average cost per fill-up and average price per gallon) for the
     * given records.
     *
     * @param records the records currently shown in the table
     */
    private void showSummary(List<Fuel> records) {
        summaryLabel.setText(String.format(
                "Records: %d   |   Total spent: $%.2f   |   Total gallons: %.2f   |   "
                        + "Average per fill-up: $%.2f   |   Average price per gallon: $%.3f",
                records.size(),
                fuelManager.calculateTotalFuelCost(records),
                fuelManager.calculateTotalGallons(records),
                fuelManager.calculateAverageFuelCost(records),
                fuelManager.calculateAveragePricePerGallon(records)));
    }

    /**
     * Shows the results of a search in the table instead of every record.
     * Called by {@link SearchFuelController} after it opens this screen.
     *
     * @param results     the records that matched the search
     * @param description a short description of what was searched for
     */
    public void showSearchResults(List<Fuel> results, String description) {
        fuelTable.getItems().setAll(results);
        showSummary(results);
        MessageLabel.setText(results.size() + " record(s) found for " + description
                + ". Click 'Show All' to see every record again.");
    }

    /**
     * Handles the Show All button: reloads the file and shows every record.
     */
    @FXML
    public void onShowAllClick() {
        MessageLabel.setText("");
        loadAndShowAll();
    }

    /**
     * Handles the Delete button. The first press asks the user to confirm; a
     * second press on the same record deletes it and shows the updated table.
     */
    @FXML
    public void deleteFuel() {
        Fuel selected = fuelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            MessageLabel.setText("Please select a fuel record to delete.");
            return;
        }
        if (recordToConfirmDelete != selected) {
            recordToConfirmDelete = selected;
            MessageLabel.setText("Press Delete again to confirm deleting fuel record #"
                    + selected.getFuelId() + " (" + selected.getDate() + ", "
                    + selected.getFuelStationName() + "). Select another row to cancel.");
            return;
        }
        recordToConfirmDelete = null;
        int deletedId = selected.getFuelId();
        try {
            if (fuelManager.deleteFuelRecord(deletedId)) {
                loadAndShowAll(); // reload so the IDs are renumbered and the table is current
                MessageLabel.setText("Fuel record #" + deletedId + " successfully deleted!");
            } else {
                MessageLabel.setText("Fuel ID " + deletedId + " was not found.");
            }
        } catch (IOException e) {
            MessageLabel.setText("Could not save the change, so nothing was deleted: " + e.getMessage());
        }
    }

    /**
     * Handles the Edit button: opens the Update Fuel screen with the selected
     * record's information already filled in.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void editFuel() throws IOException {
        Fuel selected = fuelTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            MessageLabel.setText("Please select a fuel record to update.");
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/updateFuel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        UpdateFuelController controller = fxmlLoader.getController();
        controller.setFuel(selected, data);

        Stage stage = (Stage) editbutton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Update Fuel screen");
    }

    /**
     * Handles the Add button: opens the Add Fuel screen.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void onAddbuttonClick() throws IOException {
        switchScreen(addbutton, "addFuel.fxml", "Add Fuel screen");
    }

    /**
     * Handles the Search button: opens the Search Fuel screen.
     *
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void onSearchButtonCLick() throws IOException {
        switchScreen(SearchButton, "SearchFuel.fxml", "Search Fuel screen");
    }

    /**
     * Handles a click on the screen title: returns to the main menu.
     *
     * @param event the mouse click
     * @throws IOException if the screen file cannot be loaded
     */
    @FXML
    public void onLabelClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/MainMenuScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Main Menu");
    }

    /**
     * Loads another screen from the layout folder and shows it in this window.
     *
     * @param source   a control on this screen (used to find the window)
     * @param fxmlName the FXML file name
     * @param title    the window title for the new screen
     * @throws IOException if the screen file cannot be loaded
     */
    private void switchScreen(Button source, String fxmlName, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/edu/utsa/cs3443/truckflow/layout/" + fxmlName));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        Stage stage = (Stage) source.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
    }
}
