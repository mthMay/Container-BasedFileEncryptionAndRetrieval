/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author ntu-user
 */
public class fileManagementController {

    @FXML
    private Text userText;

    @FXML
    private TextField fileNameTextField;

    @FXML
    private Button createButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private Button backButton;

    @FXML
    private Button readButton;

    @FXML
    private void createButtonController(ActionEvent event) throws IOException, ClassNotFoundException, InvalidKeySpecException {
        fileManagementCode obj = new fileManagementCode();
        SecondaryController sc = new SecondaryController();
        FileTable db = new FileTable();
        String fileName = fileNameTextField.getText();
        if (fileName.equals("")) {
            dialogue("File name is empty.", "Please Try Again!");
        } else {
            String res = obj.createFile(fileName, contentTextArea.getText());
            if (!res.equals("File already exists.")) {
                dialogue("File created", "Successful!");
                db.addDataToFileTable(fileName, obj.fileSize(fileName), userText.getText());
                performActionAndLog("Create file", fileName);
            } else {
                dialogue("File already exists.", "Try again!");
                performActionAndLog("Tried to create file", fileName);
            }
        }

    }

    @FXML
    private void updateButtonController(ActionEvent event) throws IOException, ClassNotFoundException {
        fileManagementCode obj = new fileManagementCode();
        FileTable db = new FileTable();
        ACLTable acl = new ACLTable();
        fileStoring remote = new fileStoring();
        String fileName = fileNameTextField.getText();
        String owner = db.getOwner(fileName);
        String shared = userText.getText();
        String checkPath = remote.checkFilePath(fileName);
        int check = acl.checkReadWrite(fileName, owner, shared);
        if (checkPath.equals("File already exists.")) {
            if (owner.equals(shared)) {
                String res = obj.updateFile(fileName, contentTextArea.getText());
                if (res.equals("File updated.")) {
                    dialogue("File updated", "Successful!");
                    db.updateTable(fileName, obj.fileSize(fileName));
                    performActionAndLog("Update file", fileName);
                } else {
                    dialogue("Something went wrong", "Try again!");
                    performActionAndLog("Tried to update file", fileName);
                }
            } else if (check == 0) {
                dialogue("User does not have permission to update the file", "Sorry!");
                performActionAndLog("Tried to update file.", fileName);
            } else if (check != 0) {
                String res = obj.updateFile(fileName, contentTextArea.getText());
                if (res.equals("File updated.")) {
                    dialogue("File updated", "Successful!");
                    db.updateTable(fileName, obj.fileSize(fileName));
                    performActionAndLog("Update file", fileName);
                } else {
                    dialogue("Something went wrong", "Try again!");
                    performActionAndLog("Tried to update file", fileName);
                }
            }
        } else {
            dialogue("File does not exist.", "Try again!");
            performActionAndLog("Tried to update file", fileName);
        }
    }

    @FXML
    private void deleteButtonController(ActionEvent event) throws IOException, ClassNotFoundException {
        fileManagementCode obj = new fileManagementCode();
        FileTable db = new FileTable();
        ACLTable acl = new ACLTable();
        binTable bin = new binTable();
        binStoring remoteBin = new binStoring();
        fileStoring remote = new fileStoring();
        String fileName = fileNameTextField.getText();
        String owner = db.getOwner(fileName);
        String shared = userText.getText();
        String checkPath = remote.checkFilePath(fileName);
        int check = acl.checkShare(fileName, owner);
        String binCheck = remoteBin.checkFilePath(fileName);
        if (checkPath.equals("File already exists.")) {
            if (!binCheck.equals("File already exists.")) {
                if ((check == 0) && (owner.equals(shared))) {
                    String res = obj.deleteFile(fileName);
                    if (res.equals("File deleted.")) {
                        dialogue("File deleted", "Successful!");
                        db.deleteFile(fileName, owner);
                        bin.addDataToFileTable(fileName, obj.fileSizeBin(fileName), owner);
                        performActionAndLog("Delete file", fileName);
                    } else {
                        dialogue("Failed to delete the file", "Please Try Again!");
                        performActionAndLog("Tried to delete file", fileName);
                    }
                } else {
                    dialogue("Cannot delete the file. Either you do not have permission or your file is being shared with other user.", "Sorry!");
                    performActionAndLog("Tried to delete file", fileName);
                }
            } else {
                dialogue("Cannot delete the file. File with the same name already exist in bin. Please delete the file in the bin first.", "Sorry!");
                performActionAndLog("Tried to delete file", fileName);
            }
        } else {
            dialogue("File does not exist.", "Try again!");
            performActionAndLog("Tried to delete file", fileName);
        }
    }

    @FXML
    private void readButtonController(ActionEvent event) throws IOException, ClassNotFoundException {
        fileManagementCode obj = new fileManagementCode();
        FileTable db = new FileTable();
        ACLTable acl = new ACLTable();
        fileStoring remote = new fileStoring();
        String fileName = fileNameTextField.getText();
        String owner = db.getOwner(fileName);
        String shared = userText.getText();
        String checkPath = remote.checkFilePath(fileName);
        int check = acl.checkRead(fileName, owner, shared);
        if (checkPath.equals("File already exists.")) {
            if (owner.equals(shared)) {
                String res = obj.readFile(fileName);
                if (!res.equals("An error occured.")) {
                    dialogue("File read.", "Successful!");
                    contentTextArea.setText(res);
                    performActionAndLog("Read file", fileName);
                } else {
                    dialogue("Something went wrong", "Try again!");
                    performActionAndLog("Tried to read file", fileName);
                }
            } else if (check == 0) {
                dialogue("User does not have permission to read the file", "Sorry!");
                performActionAndLog("Tried to read file", fileName);
            } else if (check != 0) {
                String res = obj.readFile(fileName);
                if (!res.equals("An error occured.")) {
                    dialogue("File read.", "Successful!");
                    contentTextArea.setText(res);
                    performActionAndLog("Read file", fileName);
                } else {
                    dialogue("Something went wrong", "Try again!");
                    performActionAndLog("Tried to read file", fileName);
                }
            }
        } else {
            dialogue("File does not exist.", "Try again!");
            performActionAndLog("Tried to read file ", fileName);
        }
    }

    @FXML
    private void backButtonController(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) backButton.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondary.fxml"));
            performActionAndLog1("Go to user profile view", "N/A", "N/A");
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            SecondaryController controller = loader.getController();
            controller.setUserText(userText.getText());
            secondaryStage.setScene(scene);
            controller.initialise();
            controller.initialiseShared();
            secondaryStage.setTitle("User Profile");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void performActionAndLog(String action, String file) throws ClassNotFoundException {
        FileTable db = new FileTable();
        String fileName = fileNameTextField.getText();
        String username = db.getOwner(file);
        String modifyingUser = userText.getText();

        AuditTrail.logAccess(username, fileName, action, modifyingUser);
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

        AuditTrail.logAccess(username, fileName, action, modifyingUser);
    }
}
