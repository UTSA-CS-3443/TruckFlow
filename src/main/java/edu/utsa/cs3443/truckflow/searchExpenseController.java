package edu.utsa.cs3443.truckflow;

public class searchExpenseController {
// start with class attributes
    @FXML
    private DatePicker dateRange1;
    @FXML
    private DatePicker dateRange2;

    @FXML
    private TextField expenseID;
    @FXML
    private TextField categoryText;

    @FXML
    private Button cancelButton;
    @FXML
    private Button searchRecordButton;
    

    // make method for the cancel button

    public void CancelButtonClick()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard Screen");




    }

}
