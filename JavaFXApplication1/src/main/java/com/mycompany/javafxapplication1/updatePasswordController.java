/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class updatePasswordController {
    @FXML
    private Text userText;
    
    @FXML
    private PasswordField oldPassword;
    
    @FXML
    private PasswordField newPassword;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private void updateButtonHandler(ActionEvent event) throws InvalidKeySpecException, ClassNotFoundException, IOException{
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) updateButton.getScene().getWindow();
        DB db = new DB();
        String user = userText.getText();
        String oldPass = oldPassword.getText();
        String newPass = newPassword.getText();
        if(db.validateUser(user, oldPass)){
            FXMLLoader loader = new FXMLLoader();
            db.updatePassword(oldPass, newPass, user);
            dialogue("Password updated", "Successful!");
            performActionAndLog1("Update password", "N/A", "N/A");
            loader.setLocation(getClass().getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Login");
            secondaryStage.show();
            primaryStage.close();
        }else{
            dialogue("Invalid User Name / Password","Please try again!");
            performActionAndLog1("Tried to update password", "N/A", "N/A");
        }
        
    }
   
    @FXML 
    private void backButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog1("Go back to user profile view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            SecondaryController controller = loader.getController();
            controller.setUserText(userText.getText());
            controller.initialise();
            controller.initialiseShared();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("User Profile");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void setUserText(String user) {
        userText.setText(user);
    }
    
    private void dialogue(String headerMsg, String contentMsg) {
        Stage secondaryStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.DARKGRAY);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(headerMsg);
        alert.setContentText(contentMsg);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
    private void performActionAndLog1(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
