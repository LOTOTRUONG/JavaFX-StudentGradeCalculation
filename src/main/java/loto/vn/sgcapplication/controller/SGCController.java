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
    private Button btnCalculator, btnStatistic,btnUpdateGrade, btnUpdateStudent;

    @FXML
    public void initialize() {
        btnCalculator.setOnAction(event -> openCalculator());
        btnStatistic.setOnAction(event -> openStatistic());
        btnUpdateGrade.setOnAction(event -> openUpdateGrade());
        btnUpdateStudent.setOnAction(event -> openUpdateStudent());
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
        showErrorAlert("Error", "Unable to load Student Grade Calculator Scene");
    }

    }

    @FXML
    public void openUpdateGrade(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/UpdateGradeSystem.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Grade");
            stage.show();
        } catch(IOException e){
            showErrorAlert("Error", "Unable to load Update Grade Scene");
        }
    }

    @FXML
    public void openUpdateStudent(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/UpdateStudentSystem.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Student");
            stage.show();
        } catch(IOException e){
            showErrorAlert("Error", "Unable to load Update Student Scene");
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
            showErrorAlert("Error", "Unable to load Statistique Scene Scene");       }

    }

        private void showErrorAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }
}
