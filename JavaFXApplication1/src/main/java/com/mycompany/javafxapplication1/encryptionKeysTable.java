/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.lingala.zip4j.model.ZipParameters;

/**
 *
 * @author ntu-user
 */
public class encryptionKeysTable {
    private String DBfileName = "jdbc:sqlite:comp20081.db";
    private int timeout = 30;
    private String dataBaseName = "COMP20081";
    private String dataBaseTableName = "Keys";
    Connection connection = null;
    
    public void createTable(String tableName) throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("create table if not exists " + tableName + "(id integer primary key autoincrement, fileName string, keys string)");
        } catch (SQLException e){
            Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void addDataToDB(String fileName, String key) throws ClassNotFoundException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("insert into " + dataBaseTableName + " (fileName, keys) values('" + fileName + "','" + key + "')");
        } catch (SQLException ex) {
            Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void deleteKey(String fileName) throws ClassNotFoundException{
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("delete from " + dataBaseTableName + " where fileName = '" + fileName + "'" );
        } catch (SQLException ex) {
            Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public String getKey(String fileName) throws ClassNotFoundException, SQLException{
        String res = "";
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select * from " + this.dataBaseTableName);
            while(rs.next()){
                if(fileName.equals(rs.getString("fileName"))){
                    res = rs.getString("keys");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(encryptionKeysTable.class.getName()).log(Level.SEVERE, null, ex);
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
        return res;
    }
     
    public int checkRow(String fileName, String key)throws ClassNotFoundException{
        int result = 0;
        FileTable ft = new FileTable();
        DB db = new DB();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select count(id) as count from " + dataBaseTableName + " where fileName = '" + fileName + "'" + " and keys = '" + key + "'");
            while(rs.next()){
                result = rs.getInt("count");
            }
        } catch (SQLException e){
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        } 
        return result;
    }
    
    public String getTableName() {
        return this.dataBaseTableName;
    }
}
