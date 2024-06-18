    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.sql.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ntu-user
 */
public class binTable {
    private String DBfileName = "jdbc:sqlite:comp20081.db";
    private int timeout = 30;
    private String dataBaseName = "COMP20081";
    private String dataBaseTableName = "Bin";
    Connection connection = null;
    
    public void createTable(String tableName) throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("create table if not exists " + tableName + "(id integer primary key autoincrement, fileName string, size float, owner string, deletionDate date default (date('now')))");
        } catch (SQLException e){
            Logger.getLogger(binTable.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void delTable(String tableName) throws ClassNotFoundException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("drop table if exists " + tableName);
        } catch (SQLException ex) {
            Logger.getLogger(binTable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
    
    public void addDataToFileTable(String fileName, float size, String owner)throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("insert into " + dataBaseTableName + "(fileName, size, owner) values ('" + fileName + "','" + size + "','" + owner + "')");
        }catch(SQLException e){
            Logger.getLogger(binTable.class.getName()).log(Level.SEVERE, null, e);
        } finally{
            try{
                if(connection != null){
                    connection.close();
                }
            } catch(SQLException e){
                Logger.getLogger(binTable.class.getName()).log(Level.SEVERE, null, e);
            } finally {
                try{
                    if (connection != null) {
                        connection.close();
                    }
                } catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    
    public String getOwner(String fileName) throws ClassNotFoundException{
        String owner = "";
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select owner from " + dataBaseTableName + " where fileName = '" + fileName + "'");
            while(rs.next()){
                owner = rs.getString("owner");
            }
        } catch (SQLException e){
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return owner;
    }
    
    public ObservableList<binFiles> getDataFromTable(String userName) throws ClassNotFoundException{
        ObservableList<binFiles> result = FXCollections.observableArrayList();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select * from " + this.dataBaseTableName + " where owner = '" + userName + "'");
            while(rs.next()){
                result.add(new binFiles(rs.getString("fileName"), rs.getFloat("size"), rs.getString("owner"),  rs.getString("deletionDate")));
            }
        } catch(SQLException e){
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try{
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return result;
    }
    
    public String getDeletionDate(String fileName, String owner) throws ClassNotFoundException{
        String res = "";
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select deletionDate from " + dataBaseTableName + " where fileName = '" + fileName + "' and owner = '" + owner + "'");
            while(rs.next()){
                res = rs.getString("deletionDate");
            }
        } catch (SQLException e){
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return res;
    }
    
    public void deleteFile(String fileName, String owner) throws ClassNotFoundException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("delete from " + dataBaseTableName + " where fileName = '" + fileName + "' and owner = '" + owner + "'");
        } catch (SQLException ex) {
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }
    
    public String getTableName() {
        return this.dataBaseTableName;
    }

}
