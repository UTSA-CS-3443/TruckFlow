package edu.utsa.cs3443.truckflow;

import edu.utsa.cs3443.truckflow.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import java.io.IOException;

public class LoginCOntroller {

    @FXML
    private ImageView addimage;
    @FXML
    private TextField UserText;
    @FXML
    private TextField passwordText;

    @FXML
    private Button Expenses;
    @FXML
    private Button Revenue;
    @FXML
    private Button login;
@FXML
private Label loginIndicatorLabel;
    private String selectedScreen;

    public void initialize() throws IOException {
        Image addScreen = new Image(getClass().getResource("/edu/utsa/cs3443/truckflow/images/StartScreen.jpg").toExternalForm());
        addimage.setImage(addScreen);
    }

    @FXML
    public void onLoginButtonClick()  throws IOException {

        String username = UserText.getText();
        String password = passwordText.getText();

        if (username.isEmpty() || password.isEmpty()) {
            loginIndicatorLabel.setText("Please enter a valid username and password!");
            return;
        }

        User user = User.loadUserFile(username, password);// call findUser method to check match


        if (user == null) {
            loginIndicatorLabel.setText("Invalid username and/or password!");
            return;
        } else {
            start();
        }
    }

    private void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/MainMenuScreen.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) login.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");
    }

    public void onExpenseButtonClick()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/expenseDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) Expenses.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

    }


    public void onRevenueButton()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                TruckFlow.class.getResource("/edu/utsa/cs3443/truckflow/layout/RevenueDashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1600, 914);

        Stage stage = (Stage) Revenue.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Expense Dashboard");

    }

}
