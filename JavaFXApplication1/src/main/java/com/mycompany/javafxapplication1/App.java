package com.mycompany.javafxapplication1;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        String path = "/home/ntu-user/App";
        String binPath = "/home/ntu-user/Bin";
        File dir = new File(path);
        File binDir = new File(binPath);
        fileManagementCode fmc = new fileManagementCode();
        fmc.createBins();
        if (!dir.exists()){
            dir.mkdir();
        }
//        if(!binDir.exists()){
//            binDir.mkdir();
//        }
        Stage secondaryStage = new Stage();
        DB myObj = new DB();
        ACLTable acl = new ACLTable();
        FileTable tableDB = new FileTable();
        encryptionKeysTable key = new encryptionKeysTable();
        binTable bin = new binTable();
//        try {
//            tableDB.delTable(tableDB.getTableName());
//            myObj.delTable(myObj.getTableName());
//            acl.delTable(acl.getTableName());
//            key.delTable(key.getTableName());
//            bin.delTable(bin.getTableName());
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            tableDB.createTable(tableDB.getTableName());
            myObj.createTable(myObj.getTableName());
            acl.createTable(acl.getTableName());
            key.createTable(key.getTableName());
            bin.createTable(bin.getTableName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("primary.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Primary View");
            secondaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}