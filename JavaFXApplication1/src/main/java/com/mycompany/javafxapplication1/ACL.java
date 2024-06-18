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
public class ACL {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty fileId;
    private SimpleIntegerProperty userId;
    private SimpleIntegerProperty read;
    private SimpleIntegerProperty write;
    private SimpleStringProperty shared;
    
    ACL(int id, int fileId, int userId, int read, int write, String shared){
        this.id = new SimpleIntegerProperty(id);
        this.fileId = new SimpleIntegerProperty(fileId);
        this.userId = new SimpleIntegerProperty(userId);
        this.read = new SimpleIntegerProperty(read);
        this.write = new SimpleIntegerProperty(write);
        this.shared = new SimpleStringProperty(shared);
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public int getFileId(){
        return fileId.get();
    }
    
    public void setFileId(int fileId){
        this.fileId.set(fileId);
    }
    
    public int getUserId(){
        return userId.get();
    }
    
    public void setUserId(int userId){
        this.userId.set(userId);
    }
    
    public int getRead(){
        return read.get();
    }
    
    public void setRead(int read){
        this.read.set(read);
    }
    
    public int getWrite(){
        return write.get();
    }
    
    public void setWrite(int write){
        this.write.set(write);
    }
    
    public String getShared(){
        return shared.get();
    }
    
    public void setShared(String shared){
        this.shared.set(shared);
    }
}
