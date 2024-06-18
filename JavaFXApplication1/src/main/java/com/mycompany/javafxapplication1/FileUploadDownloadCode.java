/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipException;

/**
 *
 * @author ntu-user
 */
public class FileUploadDownloadCode {
    fileManagementCode fmc = new fileManagementCode();
    public String downloadFile(String fileName) throws ZipException{
        String res = "";
        fileStoring remote = new fileStoring();
        String src = "/home/ntu-user/NetBeansProjects/SSCWK/JavaFXApplication1/" + fileName;
        String des = "/home/ntu-user/App/" + fileName;
        Path srcPath = Paths.get(src);
        Path desPath = Paths.get(des);
        try{
            File myObj = new File(fileName);
            String check = remote.checkFilePath(fileName);
            if(check.equals("File already exists.")) {
                fmc.decryptFile(fileName);
                Files.move(srcPath, desPath);
                fmc.deleteChunks(fileName);
                fmc.deleteZipChunks(fileName);
                }         
        } catch (IOException e){
            res = "An error occurred.";
        }
        return res;
    }
    
    public String uploadFile(String file) throws ZipException, IOException, ClassNotFoundException{
        String res = "";
        fileStoring remote = new fileStoring();
        String doubleCheck = remote.checkFilePath(file);
        if(!doubleCheck.equals("File already exists.")){
            String filePath = file;
            File files = new File(filePath);
            String fileName = files.getName();
            String desPath = "/home/ntu-user/NetBeansProjects/SSCWK/JavaFXApplication1/" + fileName;
            Path src = Paths.get(filePath);
            Path des = Paths.get(desPath);
            try{
                if(!des.equals(src)){
                    Files.copy(src, des);
                }
                File myObj = new File(fileName);
                String check = remote.checkFilePath(fileName);
                
                if(!check.equals("File already exists.")){
                    fmc.encryptFile(fileName);
                    fmc.deleteChunks(fileName);
                    fmc.deleteZipChunks(fileName);
                    File del = new File(desPath);
                    del.delete();
                } else{
                    res = "File already exists.";
                }
                       
            } catch(Exception e){
                res = "An error occurred.";
            }
        }else {
            res = "File already exists.";
        }
        return res;
    }
}
