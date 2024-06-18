/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author ntu-user
 */
public class AuditTrail {
     private static final String AUDIT_LOG_FILE = "audit_log.txt";
    public static void logAccess(String username, String fileName, String action, String modifyinguser) {
        String logEntry = buildLogEntry(username, fileName, action,modifyinguser);
        writeLogToFile(logEntry);
    }

    private static String buildLogEntry(String username,String fileName, String action,String modifyuser) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTimestamp = timestamp.format(formatter);
        return formattedTimestamp + " - Owner: " + username + " - File: " + fileName + " - Action: " + action + " - modified by: " + modifyuser;
    }

    private static void writeLogToFile(String logEntry) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(AUDIT_LOG_FILE, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.out.print("no log recorded");
        }
    }
}

