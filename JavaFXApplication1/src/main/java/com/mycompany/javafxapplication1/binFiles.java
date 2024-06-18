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
public class binFiles {
    private SimpleStringProperty fileName;
    private SimpleFloatProperty size;
    private SimpleStringProperty owner;
    private SimpleStringProperty deletionDate;
    
    binFiles(String fileName, float size, String owner, String deletionDate){
        this.fileName = new SimpleStringProperty(fileName);
        this.size = new SimpleFloatProperty(size);
        this.owner = new SimpleStringProperty(owner);
        this.deletionDate = new SimpleStringProperty(deletionDate);
    }
    
    public String getFileName(){
        return fileName.get();
    }
    
    public void setFileName(String fileName){
        this.fileName.set(fileName);
    }
    
    public float getSize(){
        return size.get();
    }
    
    public void setSize(float size){
        this.size.set(size);
    }
    
    public String getOwner(){
        return owner.get();
    }
    
    public void setOwner(String owner){
        this.owner.set(owner);
    }
    
    public String getDeletionDate(){
        return deletionDate.get();
    }
    
    public void setDeletionDate(String deletionDate){
        this.deletionDate.set(deletionDate);
    }
}
