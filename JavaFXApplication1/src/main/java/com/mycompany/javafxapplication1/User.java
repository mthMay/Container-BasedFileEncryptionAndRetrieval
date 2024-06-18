/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ntu-user
 */
public class User {
    private SimpleIntegerProperty id;
    private SimpleStringProperty user;
    private SimpleStringProperty pass;

    User(int id, String user, String pass) {
        this.id = new SimpleIntegerProperty(id);
        this.user = new SimpleStringProperty(user);
        this.pass = new SimpleStringProperty(pass);
    }
    
    public int getId(){
        return id.get();
    }
    
    public void setId(int id){
        this.id.set(id);
    }

    public String getUser() {
        return user.get();
    }

    public void setUser(String user) {
        this.user.set(user);
    }

    public String getPass() {
        return pass.get();
    }

    public void setPass(String pass) {
        this.pass.set(pass);
    }
}
