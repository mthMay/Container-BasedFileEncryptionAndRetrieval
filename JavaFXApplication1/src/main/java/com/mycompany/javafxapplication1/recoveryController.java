/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class recoveryController {
    @FXML
    private Text userText;

    @FXML
    private TableView dataTableView;

    @FXML
    private TextField fileTextField;
    
    @FXML 
    private Button recoverButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private void deleteButtonHandler(ActionEvent event) throws ClassNotFoundException{
        fileManagementCode fmc = new fileManagementCode();
        binTable bin = new binTable();
        binStoring remoteBin = new binStoring();
        String fileName = fileTextField.getText();
        String owner = bin.getOwner(fileName);
        String user = userText.getText();
        String checkFile = remoteBin.checkFilePath(fileName);
        if(checkFile.equals("File already exists.") && owner.equals(user)){
            String res = fmc.deleteRemoteFile(fileName);
            if(res.equals("File deleted.")){
                dialogue("File deleted.", "Successful!");
                bin.deleteFile(fileName, user);
                performActionAndLog("File deleted permanently ", fileName);
            }else{
                dialogue("Failed to delete the file", "Please Try Again!");
                performActionAndLog("Tried to delete file permanently ", fileName);
                }
        }else {
            dialogue("File does not exist or you do not have permission to delete the file.", "Please Try Again!");
            performActionAndLog("FTried to delete file permanently ", fileName);
        }
    }
    
    @FXML
    private void recoverButtonHandler(ActionEvent event) throws ClassNotFoundException, ParseException{
        fileManagementCode fmc = new fileManagementCode();
        binTable bin = new binTable();
        FileTable ft = new FileTable();
        binStoring remoteBin = new binStoring();
        fileStoring remote = new fileStoring();
        String fileName = fileTextField.getText();
        String owner = bin.getOwner(fileName);
        String user = userText.getText();
        String checkFile = remoteBin.checkFilePath(fileName);
        String checkFileTable = remote.checkFilePath(fileName);
        if(!checkFileTable.equals("File already exists.")){
            if(checkFile.equals("File already exists.") && owner.equals(user)){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = bin.getDeletionDate(fileName, user);
                java.util.Date parsedDate = dateFormat.parse(dateString);
                Timestamp sqlTimestamp = new Timestamp(parsedDate.getTime());
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                Timestamp thirtyOneDays = limitTimestamp(currentTimestamp);
                boolean withinLimit = sqlTimestamp.after(thirtyOneDays);
                if(withinLimit == true){
                    String res = fmc.recoverFiles(fileName);
                    if (res.equals("File recovered.")){
                        dialogue("File recovered.", "Successful!");
                        bin.deleteFile(fileName, user);
                        ft.addDataToFileTable(fileName, fmc.fileSize(fileName), user);
                        performActionAndLog("File recovered ", fileName);
                    }
                }else {
                    dialogue("File was deleted over 31 days ago.", "Cannot recover!");
                    performActionAndLog("Tried to recover file ", fileName);
                }
            }else {
                dialogue("File does not exist or you do not hava permission to recover the file.", "Please Try Again!");
                performActionAndLog("Tried to recover file ", fileName);
            }
        }else {
            dialogue("You have file with the same name. Cannot recover this file.", "Please Try Again!");
            performActionAndLog("Tried to recover file ", fileName);
        }
    }
    
    @FXML 
    private void backButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog1("Go to user profile view", "N/A", "N/A");
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
    
    @FXML 
    private void refreshButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) refreshButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("recovery.fxml"));
            performActionAndLog1("Refresh the bin view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            recoveryController controller = loader.getController();
            controller.setUserText(userText.getText());
            controller.initialise();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Bin");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void initialise() {
        binTable bin = new binTable();
        ObservableList<binFiles> data;
        try {
            data = bin.getDataFromTable(userText.getText());
            
            TableColumn fileName = new TableColumn("File Name");
            fileName.setCellValueFactory(
                    new PropertyValueFactory<>("fileName"));

            TableColumn fileSize = new TableColumn("File Size");
            fileSize.setCellValueFactory(
                    new PropertyValueFactory<>("size"));

            TableColumn owner = new TableColumn("Owner");
            owner.setCellValueFactory(
                    new PropertyValueFactory<>("owner"));

            TableColumn creationDate = new TableColumn("Deletion Date");
            creationDate.setCellValueFactory(
                    new PropertyValueFactory<>("deletionDate"));

            dataTableView.setItems(data);
            dataTableView.getColumns().addAll(fileName, fileSize, owner, creationDate);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Timestamp limitTimestamp(Timestamp currentTimestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimestamp.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -31);
        return new Timestamp(calendar.getTimeInMillis());
    }
    
    public void setUserText(String user) {
        userText.setText(user);
    }
    
    private void performActionAndLog(String action, String file) throws ClassNotFoundException {
        binTable bin = new binTable();
        String fileName = fileTextField.getText();
        String username = bin.getOwner(file);
        String modifyingUser = userText.getText(); 
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
    
    private void performActionAndLog1(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
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
}
