/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ntu-user
 */
public class encryptionKeys {
    private SimpleStringProperty fileName;
    private SimpleStringProperty keys;
    private SimpleIntegerProperty id;

    encryptionKeys(int id, String fileName, String keys){
        this.id = new SimpleIntegerProperty(id);
        this.fileName = new SimpleStringProperty(fileName);
        this.keys = new SimpleStringProperty(keys);
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public String getFileName(){
        return fileName.get();
    }
    
    public void setFileName(String fileName){
        this.fileName.set(fileName);
    }
    
    public String getKeys(){
        return keys.get();
    }
    
    public void setKeys(String keys){
        this.keys.set(keys);
    }
}
