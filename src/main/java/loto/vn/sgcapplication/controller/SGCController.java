package loto.vn.sgcapplication.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SGCController {
    @FXML
    private Button btnCalculator;

    @FXML
    private Button btnStatistic;

    @FXML
    public void initialize() {
        btnCalculator.setOnAction(event -> openCalculator());
        btnStatistic.setOnAction(event -> openStatistic());
    }

    @FXML
    public void openCalculator(){
    try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/GCSystem.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Student Grade Calculator");
        stage.show();
    } catch(IOException e){
        showErrorAlert("Error", "Unable to load Article Scene");
    }

    }

    @FXML
    public void openStatistic() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource(".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Statistique Scene");
            stage.show();
        } catch (IOException E) {
            showErrorAlert("Error", "Unable to load Article Scene");       }

    }

        private void showErrorAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
}
