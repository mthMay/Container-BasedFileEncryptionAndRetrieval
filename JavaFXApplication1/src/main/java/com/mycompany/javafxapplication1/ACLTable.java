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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ntu-user
 */
public class ACLTable {
    private String DBfileName = "jdbc:sqlite:comp20081.db";
    private int timeout = 30;
    private String dataBaseName = "COMP20081";
    private String dataBaseTableName = "ACL";
    Connection connection = null;
    
    public void createTable(String tableName) throws ClassNotFoundException{
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("create table if not exists " + tableName + "(id integer primary key autoincrement, fileId integer, userId integer, read integer default 1, write integer default 1, shared string, foreign key (fileId) references FileMetadata(id), foreign key (userId) references Users(id))");
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
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void addDataToDB(String fileName, String userName,int read, int write, String shared) throws InvalidKeySpecException, ClassNotFoundException {
        FileTable ft = new FileTable();
        DB db = new DB();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("insert into " + dataBaseTableName + " (fileId, userId, read, write, shared) values('" + ft.getFid(fileName) + "','" + db.getUid(userName) + "', '" + read + "','"+ write +"','" + shared +"')");
        } catch (SQLException ex) {
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void updateRead(String fileName, String userName, String accessUserName) throws ClassNotFoundException{
        FileTable ft = new FileTable();
        DB db = new DB();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("update " + dataBaseTableName + " set write = 0 where fileId = '" + ft.getFid(fileName) + "' and userId = '" + db.getUid(userName) + "' and read = '1' and shared = '" + accessUserName + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
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
      
    public void updateReadWrite(String fileName, String userName, String accessUserName) throws ClassNotFoundException{
        FileTable ft = new FileTable();
        DB db = new DB();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("update " + dataBaseTableName + " set read = 1, write = 1 where fileId = '" + ft.getFid(fileName) + "' and userId = '" + db.getUid(userName) + "' and shared = '" + accessUserName + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public void revokeAccess(String fileName, String userName, String accessUserName) throws ClassNotFoundException{
        FileTable ft = new FileTable();
        DB db = new DB();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            statement.executeUpdate("delete from " + dataBaseTableName + " where fileId = " + ft.getFid(fileName) + " and userId = " + db.getUid(userName) + " and shared = '" + accessUserName +"'" );
        } catch (SQLException ex) {
            Logger.getLogger(ACLTable.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public int checkRead(String fileName, String user, String shared)throws ClassNotFoundException{
        int result = 0;
        FileTable ft = new FileTable();
        DB db = new DB();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select id from " + dataBaseTableName + " where fileId = '" + ft.getFid(fileName) + "'" + " and userId = '" + db.getUid(user) + "' and read = '1' and shared = '" + shared + "'");
            while(rs.next()){
                result = rs.getInt("id");
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
    
    public int checkReadWrite(String fileName, String user, String shared)throws ClassNotFoundException{
        int result = 0;
        FileTable ft = new FileTable();
        DB db = new DB();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select id from " + dataBaseTableName + " where fileId = '" + ft.getFid(fileName) + "'" + " and userId = '" + db.getUid(user) + "' and read = '1' and write = '1' and shared = '" + shared + "'");
            while(rs.next()){
                result = rs.getInt("id");
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
    
    public int checkRow(String fileName, String user, String shared)throws ClassNotFoundException{
        int result = 0;
        FileTable ft = new FileTable();
        DB db = new DB();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select id from " + dataBaseTableName + " where fileId = '" + ft.getFid(fileName) + "'" + " and userId = '" + db.getUid(user) + "' and shared = '" + shared + "'");
            while(rs.next()){
                result = rs.getInt("id");
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
    
    public int checkShare(String fileName, String user)throws ClassNotFoundException{
        int result = 0;
        FileTable ft = new FileTable();
        DB db = new DB();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select id from " + dataBaseTableName + " where fileId = '" + ft.getFid(fileName) + "'" + " and userId = '" + db.getUid(user) + "'");
            while(rs.next()){
                result = rs.getInt("id");
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
    
    public ObservableList<ACL> getDataFromTable(String shared) throws ClassNotFoundException{
        ObservableList<ACL> result = FXCollections.observableArrayList();
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBfileName);
            var statement = connection.createStatement();
            statement.setQueryTimeout(timeout);
            ResultSet rs = statement.executeQuery("select * from " + this.dataBaseTableName + " where shared = '" + shared + "'");
            while(rs.next()){
                result.add(new ACL(rs.getInt("id"),rs.getInt("fileId"), rs.getInt("userId"), rs.getInt("read"), rs.getInt("write"),  rs.getString("shared")));
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
    
}
