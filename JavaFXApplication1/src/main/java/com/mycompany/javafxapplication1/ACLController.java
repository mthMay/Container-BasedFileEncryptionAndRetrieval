/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class ACLController {
    @FXML
    private Text userText;
    
    @FXML
    private TextField fileTextField;
    
    @FXML
    private TextField sharedTextField;
    
    @FXML
    private RadioButton readRadio;
    
    @FXML
    private RadioButton readWriteRadio;
    
    @FXML
    private RadioButton revokeRadio;
    
    private ToggleGroup radio;
    
    @FXML 
    private Button submitButton;
    
    @FXML 
    private Button backButton;
    
    @FXML 
    private void initialize(){
        radio = new ToggleGroup();
        readRadio.setToggleGroup(radio);
        readWriteRadio.setToggleGroup(radio);
        revokeRadio.setToggleGroup(radio);
    }
    
    @FXML
    private String radioBtnSelection(){
        String selectedValue = "";
        RadioButton selectedRadioButton = (RadioButton) radio.getSelectedToggle();
        if(selectedRadioButton != null) {
            selectedValue = selectedRadioButton.getText();
        }
        return selectedValue;
    }
    
    @FXML 
    private void submitButtonHandler (ActionEvent event) throws ClassNotFoundException, InvalidKeySpecException, IOException, SQLException{
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) submitButton.getScene().getWindow();
        ACLTable acl = new ACLTable();
        DB db = new DB();
        FileTable ft = new FileTable();
        fileStoring remote = new fileStoring();
        FXMLLoader loader = new FXMLLoader();
        String user = userText.getText();
        String file = fileTextField.getText();
        String shared = sharedTextField.getText();
        String owner = ft.getOwner(file);
        String userCheck = db.checkUsers(shared);
        String selected = radioBtnSelection();
        String checkPath = remote.checkFilePath(file);
        try{
            if(checkPath.equals("File already exists.") && (owner.equals(user))){
                if(userCheck.equals("User already exists.")){
                    if(selected.equals("Read only")){
                        int check = acl.checkRow(file, user, shared);
                        if(check == 0){
                            acl.addDataToDB(file, user, 1, 0, shared);
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Give read only permission to user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }else{
                            acl.updateRead(file, user, shared);
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Give read only permission to user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }
                    } else if(selected.equals("Read & Write")){
                        int check = acl.checkRow(file, user, shared);
                        if(check == 0){
                            acl.addDataToDB(file, user, 1, 1, shared);
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Give read and write permission to user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }else{
                            acl.updateReadWrite(file, user, shared);
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Give read and write permission to user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }
                    }else if(selected.equals("Revoke Access")){
                        int check = acl.checkRow(file, user, shared);
                        if(check == 0){
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Revoke permissions from user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }else{
                            acl.revokeAccess(file, user, shared);
                            dialogue("Access updated.", "Successful!");
                            performActionAndLog("Revoke permissions from user " + shared);
                            loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                        }
                    }else if(selected.equals("")){
                        dialogue("Did not choose the access.", "Please Try again!");
                        performActionAndLog("Tried to give permission to user" + shared);
                        loader.setLocation(getClass().getResource("secondary.fxml"));
                            Parent root = loader.load();
                            Scene scene = new Scene(root, 640, 480);
                            SecondaryController controller = loader.getController();
                            controller.setUserText(userText.getText());
                            secondaryStage.setScene(scene);
                            secondaryStage.setTitle("User");
                            secondaryStage.show();
                            primaryStage.close();
                    }
                }else {
                    performActionAndLog("Tried to give permission to user " + shared);
                    dialogue("User you want to share does not exists.", "Please Try again!");
                }
            }else{
                performActionAndLog("Tried to give permission to user " + shared);
                dialogue("File you want to share does not exists.", "Please Try again!");
            }    
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    @FXML 
    private void backButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog1("Go back to user profile", "N/A", "N/A");
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
    
    public void setUserText(String user) {
        userText.setText(user);
    }
    
    private void performActionAndLog(String action) throws ClassNotFoundException {
        FileTable db = new FileTable();
        String fileName = fileTextField.getText();
        String modifyingUser = userText.getText(); 
       
        AuditTrail.logAccess(modifyingUser,fileName, action, modifyingUser);
    }
    
    private void performActionAndLog1(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
