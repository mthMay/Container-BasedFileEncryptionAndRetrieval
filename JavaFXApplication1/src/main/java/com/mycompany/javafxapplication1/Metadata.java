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
public class Metadata {
    private SimpleIntegerProperty id;
    private SimpleStringProperty fileName;
    private SimpleFloatProperty size;
    private SimpleStringProperty owner;
    private SimpleStringProperty creationDate;
    private SimpleStringProperty modifiedDate;
    
    Metadata(int id, String fileName, float size, String owner, String creationDate, String modifiedDate){
        this.id = new SimpleIntegerProperty(id);
        this.fileName = new SimpleStringProperty(fileName);
        this.size = new SimpleFloatProperty(size);
        this.owner = new SimpleStringProperty(owner);
        this.creationDate = new SimpleStringProperty(creationDate);
        this.modifiedDate = new SimpleStringProperty(modifiedDate);
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
    
    public String getCreationDate(){
        return creationDate.get();
    }
    
    public void setCreationDate(String creationDate){
        this.creationDate.set(creationDate);
    }
    
    public String getModifiedDate(){
        return modifiedDate.get();
    }
    
    public void setModifiedDate(String modifiedDate){
        this.modifiedDate.set(modifiedDate);
    }
}
