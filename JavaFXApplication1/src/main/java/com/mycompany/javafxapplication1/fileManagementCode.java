/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.enums.EncryptionMethod;

/**
 *
 * @author ntu-user
 */
public class fileManagementCode {
    int numberOfChunks = 4;
    String defaultPath = "/home/ntu-user/NetBeansProjects/SSCWK/JavaFXApplication1/";
    String appPath = "/home/ntu-user/App/";
    public String readFile(String fileName) throws ZipException, FileNotFoundException{
        String res = "";
        try{
            decryptFile(fileName);
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNextLine()){
                String data = myReader.nextLine();
                res += data + "\n";
                myObj.delete();
                deleteChunks(fileName);
                deleteZipChunks(fileName);
            }  
    }catch(IOException e){
        res = "An error occured.";
    }
        return res;
    }
    
    public String createFile(String fileName, String content){
        String res = "";
        fileStoring remote = new fileStoring();
        try{
            File myObj = new File(fileName);
            String check = remote.checkFilePath(fileName);
            if(check.equals("File already exists.")) {
                res = "File already exists.";
            } else if(myObj.createNewFile()){
                encryptFile(fileName);
                res = updateFile(fileName, content);
                myObj.delete();
            }         
        } catch (IOException e){
            res = "An error occurred.";
        }
        return res;
    }
    
    public String updateFile(String fileName, String content){
        String res = "";
        try{
            decryptFile(fileName);
            FileWriter myObj = new FileWriter(fileName);
            myObj.write(content);
            myObj.close();
            encryptFile(fileName);
            res = readFile(fileName);
            deleteChunks(fileName);
            deleteZipChunks(fileName);
            res = "File updated.";
        } catch(IOException e){
            res = "An error occurred.";
        }
        return res;
    }
    
    public String deleteRemoteFile(String fileName){
        String res = "";
        binStoring remoteBin = new binStoring();
        for (int i = 1; i <= numberOfChunks; i++){
            remoteBin.deleteFromAllContainers(fileName, i);
        }
        res = "File deleted.";
        return res;
    }
    
    public String deleteFile(String fileName){
        String res = "";
        binStoring remoteBin = new binStoring();
        for(int i = 1; i <= numberOfChunks; i++){
            remoteBin.sendAllBins(fileName, i);
        }
        res = "File deleted.";
        return res;
    }
    
    public String recoverFiles(String fileName){
        String res = "";
        binStoring remoteBin = new binStoring();
        for(int i = 1; i <= numberOfChunks; i++){
            remoteBin.getAllBins(fileName, i);
        }
        res = "File recovered.";
        return res;
    }
    
    public void deleteZipChunks(String fileName){
        for(int i = 1; i <= numberOfChunks; i++){
            File myObj = new File(appPath + fileName + i + ".zip");
            myObj.delete();
        }
    }
    
    public void deleteChunks(String fileName){
        for(int i = 1; i <= numberOfChunks; i++){
            File myObj = new File(defaultPath + fileName + i);
            myObj.delete();
        }
    }
    
    
    public float fileSize(String fileName){
        fileStoring remote = new fileStoring();
        float singleSize;
        float totalSize = 0;
        for (int i = 0; i <= numberOfChunks; i++){
            singleSize = remote.getAllSizes(fileName, i);
            totalSize += singleSize;
        }
        return totalSize;
    }
    
    public float fileSizeBin(String fileName){
        binStoring remoteBin = new binStoring();
        float singleSize;
        float totalSize = 0;
        for (int i = 0; i <= numberOfChunks; i++){
            singleSize = remoteBin.getAllSizes(fileName, i);
            totalSize += singleSize;
        }
        return totalSize;
    }

    public void encryptFile(String fileName) throws ZipException{
        ZipParameters zipParameters = new ZipParameters();
        zipParameters.setEncryptFiles(true);
        zipParameters.setCompressionLevel(CompressionLevel.MAXIMUM);
        zipParameters.setEncryptionMethod(EncryptionMethod.AES);
        encryptionKeysTable keys = new encryptionKeysTable();
        String path = defaultPath + fileName;
        DB db = new DB();
        try{
            FileInputStream fis = new FileInputStream(path);
            int chunkSize = (int) Math.ceil((double) fis.available() / (double) numberOfChunks);
            byte[] bytes = new byte[chunkSize];
            for (int i = 1; i <= numberOfChunks; i++){
                String file = fileName + i;
                String key = db.generateSecurePassword(file);
                String outputFile = defaultPath + fileName + i;
                try{
                    FileOutputStream fos = new FileOutputStream(outputFile);
                
                    int byteRead;
                    int totalBytesRead = 0;
                
                    while(totalBytesRead < chunkSize && (byteRead = fis.read(bytes)) != -1){
                        fos.write(bytes, 0, byteRead);
                        totalBytesRead += byteRead;
                    } 
                    
                
                }catch(Exception e){
                    e.printStackTrace();
                }
                
                ZipFile zipFile = new ZipFile(appPath + file + ".zip", key.toCharArray());
                zipFile.addFile(new File(defaultPath + file), zipParameters);
                int check = keys.checkRow(file, key);
                if(check == 0){
                    keys.addDataToDB(file, key);
                }
            }
            
            for (int y = 1; y <= numberOfChunks; y++){
                fileStoring remote = new fileStoring();
                remote.sendAllContainers(fileName, y);
            }
        }catch (Exception e){
            e.printStackTrace();  
        }
    }

    public void decryptFile(String fileName) throws ZipException{
        fileStoring remote = new fileStoring();
        encryptionKeysTable keys = new encryptionKeysTable();
        String outputFile = defaultPath + fileName;
        try{
            for(int i = 1; i <= numberOfChunks; i++){
                remote.getAllContainers(fileName, i);
            }
            for (int y = 1; y <= numberOfChunks; y++){
                String file = fileName + y;
                String pass = keys.getKey(file);
                ZipFile zipFile = new ZipFile(appPath + file+ ".zip", pass.toCharArray());
                zipFile.extractAll(defaultPath);
            }
            List<String> chunkFiles = new ArrayList<>();
            for(int i = 1; i <= numberOfChunks; i++){
                String path = defaultPath + fileName + i;
                chunkFiles.add(path);             
            }
            try(FileOutputStream fos = new FileOutputStream(outputFile)){
                for(String path : chunkFiles){
                    try(FileInputStream fis = new FileInputStream(path)){
                        byte[] bytes = new byte[1024];
                        int readByte;
                        while((readByte = fis.read(bytes)) != -1){
                            fos.write(bytes, 0, readByte);
                        }
                        
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }  
    }

    public void createBins(){
        binStoring remoteBin = new binStoring();
        for (int i= 1; i <= numberOfChunks; i++){
            remoteBin.createBinContainers(i);
        }
    }
}
