/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class FileUploadDownloadController {

    @FXML
    private Text userText;

    @FXML
    private TextField fileTextField;

    @FXML
    private Button selectButton;

    @FXML
    private Button uploadButton;

    @FXML
    private Button downloadButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView dataTableView;
    
    @FXML
    private Button refreshButton;
    
    @FXML
    private TableView sharedTableView;

    @FXML
    private void selectButtonHandler(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) selectButton.getScene().getWindow();
        primaryStage.setTitle("Select a File");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            fileTextField.setText((String) selectedFile.getCanonicalPath());
        }
    }

    @FXML
    private void uploadButtonHandler(ActionEvent event) throws ZipException, ClassNotFoundException, IOException {
        FileUploadDownloadCode code = new FileUploadDownloadCode();
        fileManagementCode obj = new fileManagementCode();
        FileTable ft = new FileTable();
        String filePath = fileTextField.getText();
        String res = "";
        if(!filePath.equals("")){
            try {
                String file = filePath;
                File files = new File(file);
                String fileName = files.getName();
                res = code.uploadFile(filePath);
                if (!res.equals("File already exists.")) {
                    dialogue("File uploaded.", "Successful!");
                    performActionAndLog("Upload file");
                    ft.addDataToFileTable(fileName, obj.fileSize(fileName), userText.getText());
                } else {
                    dialogue("File already exists.", "Please Try Again!");
                    performActionAndLog("Tried to upload file");
                }
            } catch (ZipException ze) {
                ze.printStackTrace();
            }
        }else {
            dialogue("You did not choose the file.", "Please Try Again!");
            performActionAndLog("Tried to upload file");
        }
    }

    @FXML
    private void downloadButtonHandler(ActionEvent event) throws ZipException, ClassNotFoundException {
        FileUploadDownloadCode code = new FileUploadDownloadCode();
        String file = fileTextField.getText();
        String user = userText.getText();
        ACLTable acl = new ACLTable();
        FileTable db = new FileTable();
        String owner = db.getOwner(file);
        String res = "";
        if(!file.equals("")){
            try {
                int check = acl.checkReadWrite(file, owner, user);
                if (owner.equals(user)) {
                    res = code.downloadFile(file);
                    if (!res.equals("File already exists.")) {
                        dialogue("File downloaded.", "Successful!");
                        performActionAndLog("Download file");
                    } else {
                        dialogue("File does not exists.", "Please Try Again!");
                        performActionAndLog("Tried to download file");
                    }
                } else if (check == 0) {
                    dialogue("You do not have permission to download the file.", "Please Try Again!");
                    performActionAndLog("Tried to download file");
                } else if (check != 0) {
                    res = code.downloadFile(file);
                    if (!res.equals("File already exists.")) {
                        dialogue("File downloaded.", "Successful!");
                        performActionAndLog("Download file");
                    } else {
                        dialogue("File does not exists.", "Please Try Again!");
                        performActionAndLog("Tried to download file");
                    }
                }

            } catch (ZipException ze) {
                ze.printStackTrace();
            }
        }else {
            dialogue("You did not enter the file name", "Please Try Again!");
            performActionAndLog("Tried to download file");
        }
    }

    @FXML
    private void backButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML 
    private void refreshButtonHandler (ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) refreshButton.getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fileUploadDownload.fxml"));
            performActionAndLog1("Refresh file upload download view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            FileUploadDownloadController controller = loader.getController();
            controller.setUserText(userText.getText());
            controller.initialise();
            controller.initialiseShared();
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("File Upload/Download");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialise() {
        FileTable myObj = new FileTable();
        ObservableList<Metadata> data;
        try {
            data = myObj.getDataFromTable(userText.getText());
            TableColumn fileName = new TableColumn("File Name");
            fileName.setCellValueFactory(
                    new PropertyValueFactory<>("fileName"));

            TableColumn fileSize = new TableColumn("File Size");
            fileSize.setCellValueFactory(
                    new PropertyValueFactory<>("size"));

            TableColumn owner = new TableColumn("Owner");
            owner.setCellValueFactory(
                    new PropertyValueFactory<>("owner"));

            TableColumn creationDate = new TableColumn("Creation Date");
            creationDate.setCellValueFactory(
                    new PropertyValueFactory<>("creationDate"));

            TableColumn modifiedDate = new TableColumn("Modified Date");
            modifiedDate.setCellValueFactory(
                    new PropertyValueFactory<>("modifiedDate"));

            dataTableView.setItems(data);
            dataTableView.getColumns().addAll(fileName, fileSize, owner, creationDate, modifiedDate);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SecondaryController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void initialiseShared(){
        ACLTable myObj = new ACLTable();
        FileTable ft = new FileTable();
        DB db = new DB();
        ObservableList<ACL> data;
        try {
            data = myObj.getDataFromTable(userText.getText());
            
            TableColumn<ACL, String>fileName = new TableColumn<>("File Name");
            fileName.setCellValueFactory(cellData -> {
                int fid = cellData.getValue().getFileId();
                String name = "";
                try{
                    name = ft.getFileName(fid);
                }catch(ClassNotFoundException e){
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, e);
                }
                return new SimpleStringProperty(name);
            });

            TableColumn<ACL, String>userName = new TableColumn<>("Owner");
            userName.setCellValueFactory(cellData -> {
                int uid = cellData.getValue().getUserId();
                String name = "";
                try{
                    name = db.getUserName(uid);
                }catch(ClassNotFoundException e){
                    Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, e);
                }
                return new SimpleStringProperty(name);
            });

            TableColumn shared = new TableColumn("Shared User");
            shared.setCellValueFactory(
                    new PropertyValueFactory<>("shared"));

            sharedTableView.setItems(data);
            sharedTableView.getColumns().addAll(fileName, userName , shared);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
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
        String username = userText.getText();
        String modifyingUser = userText.getText(); 
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
    
    private void performActionAndLog1(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
