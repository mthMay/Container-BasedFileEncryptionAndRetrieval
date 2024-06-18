/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ntu-user
 */
public class FileTable {
    private String DBfileName = "jdbc:sqlite:comp20081.db";
    private int timeout = 30;
    private String dataBaseName = "COMP20081";
    private String dataBaseTableName = "FileMetadata";
    Connection connection = null;
    
    public void createTable(String tableName) throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("create table if not exists " + tableName + "(id integer primary key autoincrement, fileName string, size float, owner string, creationDate date default (date('now')), modifiedDate date default (date('now')))");
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
    
    public void updateTable(String fileName, float size) throws ClassNotFoundException {
        try {
            // create a database connection
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("update " + dataBaseTableName + " set size = '" + size + "', modifiedDate = date('now') where fileName = '" + fileName + "'");
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
    
    public void addDataToFileTable(String fileName, float size, String owner)throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("insert into " + dataBaseTableName + "(fileName, size, owner, modifiedDate) values ('" + fileName + "','" + size + "','" + owner + "',date('now'))");
        }catch(SQLException e){
            Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, e);
        } finally{
            try{
                if(connection != null){
                    connection.close();
                }
            } catch(SQLException e){
                Logger.getLogger(FileTable.class.getName()).log(Level.SEVERE, null, e);
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
    
    public ObservableList<Metadata> getDataFromTable(String userName) throws ClassNotFoundException{
        ObservableList<Metadata> result = FXCollections.observableArrayList();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select * from " + this.dataBaseTableName + " where owner = '" + userName + "'");
            while(rs.next()){
                result.add(new Metadata(rs.getInt("id"),rs.getString("fileName"), rs.getFloat("size"), rs.getString("owner"), rs.getString("creationDate"),  rs.getString("modifiedDate")));
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
    
    public String getTableName() {
        return this.dataBaseTableName;
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
    
    public int getFid(String fileName) throws ClassNotFoundException{
        int fid = 0;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select id from " + dataBaseTableName + " where fileName = '" + fileName + "'");
            while(rs.next()){
                fid = rs.getInt("id");
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
        return fid;
    }
    
    public String getFileName(int fid) throws ClassNotFoundException{
        String fileName = "";
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select fileName from " + dataBaseTableName + " where id = '" + fid + "'");
            while(rs.next()){
                fileName = rs.getString("fileName");
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
        return fileName;
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
}

