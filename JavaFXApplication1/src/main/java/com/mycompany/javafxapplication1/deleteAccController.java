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
import javax.swing.Action;

/**
 *
 * @author ntu-user
 */
public class deleteAccController {
    @FXML
    private Text userText;
     
    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField rePasswordField;
    
    @FXML
    private Button delButton;
    
    @FXML
    private Button backButton;
    
    @FXML 
    private void delButtonHandler(ActionEvent event) throws InvalidKeySpecException, ClassNotFoundException, IOException {
        DB db = new DB();
        String user = userText.getText();
        String pass = passwordField.getText();
        String repass = rePasswordField.getText();
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) delButton.getScene().getWindow();
        if (pass.equals(repass)) {
            if(db.validateUser(user, pass)){
                FXMLLoader loader = new FXMLLoader();
                db.deleteUser(user, pass);
                dialogue("Account deleted", "Successful!");
                performActionAndLog("Delete account", "N/A", "N/A");
                loader.setLocation(getClass().getResource("register.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 640, 480);
                secondaryStage.setScene(scene);
                secondaryStage.setTitle("Register a new User");
                secondaryStage.show();
                primaryStage.close();
                }else{
                    dialogue("Username and Password does not match.","Please try again!");
                    performActionAndLog("Tried to delete account", "N/A", "N/A");
                }  
            } else {
                dialogue("Passwords entered incorrectly.","Please try again!");
                performActionAndLog("Tried to delete account", "N/A", "N/A");
        }
        
    }
    
    @FXML 
    private void backButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("primary.fxml"));
            performActionAndLog("Go to login view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Login");
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
    
    private void performActionAndLog(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
