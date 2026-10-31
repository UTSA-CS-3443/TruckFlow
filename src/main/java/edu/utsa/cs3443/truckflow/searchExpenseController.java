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

    public Expense  onSearchRecordButton() throws IOException {
        // get userinput
        LocalDate datepicker1 = dateRange1.getValue();
        LocalDate datepicker2 = dateRange2.getValue();
        String categoryInput = categoryText.getText();
        int expenseIDinput = Integer.parseInt(expenseID.getText()); // important part of the search

        // validate data of the input by passing it all into methods first and allow the if statements to seach
        Expense foundExpense = expenseList.findExpenseID(expenseIDinput); // find the expenseID
        Expense dateRange = expenseList.filterByDateRange(datepicker1,datepicker2);
        Expense categorySearch = expenseList.filterByCategory(categoryInput);

        if(foundExpense!=null){
            // return a highlighted row of the expenseTable
            switchScreenSearch(foundExpense);
            return foundExpense;

        } // use datepicker if no match on the foundExpenseID
        else if (dateRange!=null) {
            switchScreenSearch(dateRange);
            return dateRange;
        }
        else if(categorySearch!=null){
            // search by category method call
            switchScreenSearch(categorySearch);
            return categorySearch;
        }
        return null;
    }

    private void switchScreenSearch(Expense foundExpense) throws  IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);
        FlowMainController controller = fxmlLoader.getController();

        controller.returnedSearchResult(foundExpense);
        Stage stage = (Stage) searchRecordButton.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard Screen");
    }

}
