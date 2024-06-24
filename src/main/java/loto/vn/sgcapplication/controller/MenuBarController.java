package loto.vn.sgcapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarController {
    @FXML private HBox mainPane;

    @FXML
    public void openUpdateGrade() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/UpdateGradeSystem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Grade");
            stage.show();
            // Close the current stage
            Stage currentStage = getStage();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException E) {
            showErrorAlert("Error", "Unable to load Update Grade Scene");       }
    }

    @FXML
    public void openUpdateStudent() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/UpdateStudentSystem.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update Student");
            stage.show();
            // Close the current stage
            Stage currentStage = getStage();
            if (currentStage != null) {
                currentStage.close();
            }
        } catch (IOException E) {
            showErrorAlert("Error", "Unable to load Update Student Scene");       }
    }

    @FXML
    public void openCalculator() {
         try{
            FXMLLoader calculatorLoader = new FXMLLoader(getClass().getResource("/loto/vn/sgcapplication/GCSystem.fxml"));
            Parent calculatorRoot = calculatorLoader.load();
           Stage calculatorStage = new Stage();
             calculatorStage.setScene(new Scene(calculatorRoot));
             calculatorStage.setTitle("Student Grade Calculator");
             calculatorStage.show();
             // Close the current stage
             Stage currentStage = getStage();
             if (currentStage != null) {
                 currentStage.close();
             }
       } catch (IOException E) {
          showErrorAlert("Error", "Unable to load Student Grade Calculator Scene");
         }
        }




    @FXML
    void exitApplication() {
        Stage currentStage = getStage();
        if (currentStage != null) {
            currentStage.close();
        }
    }

    public void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

    }

    public Stage getStage() {
        return (Stage) mainPane.getScene().getWindow();
    }
}
