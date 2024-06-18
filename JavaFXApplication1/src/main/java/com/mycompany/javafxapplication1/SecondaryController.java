package com.mycompany.javafxapplication1;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SecondaryController {

    @FXML
    private Text userText;

    @FXML
    private TableView dataTableView;
    
    @FXML 
    private TableView sharedTableView;

    @FXML
    private Button secondaryButton;

    @FXML
    private Button refreshBtn;

    @FXML
    private Button terminalButton;

    @FXML
    private Button fileButton;

    @FXML
    private Button updatePassButton;

    @FXML
    private Button delAccButton;

    @FXML
    private Button acButton;
    
    @FXML
    private Button uploadDownload;
    
    @FXML
    private Button binButton;

    @FXML
    private void RefreshBtnHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) refreshBtn.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog("Refresh user profile view", "N/A", "N/A");
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
    private void switchToPrimary(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) secondaryButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("primary.fxml"));
            performActionAndLog("Go back to login view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Login");
            secondaryStage.show();
            primaryStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void fileButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) fileButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fileManagement.fxml"));
            performActionAndLog("Go to file manager view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            fileManagementController controller = loader.getController();
            controller.setUserText(user);
            secondaryStage.setTitle("File Manager");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void uploadDownloadHandler(ActionEvent event){
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) fileButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("fileUploadDownload.fxml"));
            performActionAndLog("Go to file upload download view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            FileUploadDownloadController controller = loader.getController();
            controller.setUserText(user);
            controller.initialise();
            controller.initialiseShared();
            secondaryStage.setTitle("File Upload/Download");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void acButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) acButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("ACL.fxml"));
            performActionAndLog("Go to access control view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            ACLController controller = loader.getController();
            controller.setUserText(user);
            secondaryStage.setTitle("Access Control");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void binButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) acButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("recovery.fxml"));
            performActionAndLog("Go to bin view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            recoveryController controller = loader.getController();
            controller.setUserText(user);
            controller.initialise();
            secondaryStage.setTitle("Bin");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updatePassButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) updatePassButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("updatePass.fxml"));
            performActionAndLog("Go to update password view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            updatePasswordController controller = loader.getController();
            controller.setUserText(user);
            secondaryStage.setTitle("Update Password");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void delAccButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) delAccButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("deleteAcc.fxml"));
            performActionAndLog("Go to account deletion view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            deleteAccController controller = loader.getController();
            controller.setUserText(user);
            secondaryStage.setTitle("Account Deletion");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void terminalButtonHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) terminalButton.getScene().getWindow();
        try {
            String user = userText.getText();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("terminal.fxml"));
            performActionAndLog("Go to terminal view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            TerminalController controller = loader.getController();
            controller.setUserText(user);
            secondaryStage.setTitle("Terminal");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
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
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setUserText(String user) {
        userText.setText(user);
    }
    
    private void performActionAndLog(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userText.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
