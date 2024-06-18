/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 *
 * @author ntu-user
 */
public class TerminalController {
    @FXML
    private Text userText;
    
    @FXML
    private Button submitButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private TextField terminalTextField;
    
    @FXML
    private TextArea terminalTextArea;
    
    @FXML
    private void submitButtonHandler (ActionEvent event) throws IOException, ClassNotFoundException{
        terminalCode obj = new terminalCode();
        String cmd = terminalTextField.getText();
        String res = obj.runCommand(cmd);
        terminalTextArea.setText(res);
        performActionAndLog("Run the terminal", "N/A", "N/A");
    }
    
    @FXML 
    private void backButtonController (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog("Go to user profile view", "N/A", "N/A");
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
    
    private void performActionAndLog(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
