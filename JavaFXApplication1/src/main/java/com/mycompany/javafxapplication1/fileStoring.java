/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.File;

/**
 *
 * @author ntu-user
 */
public class fileStoring {
    private static final String REMOTE_HOST1 = "comp20081-files-container1";
    private static final String REMOTE_HOST2 = "comp20081-files-container2";
    private static final String REMOTE_HOST3 = "comp20081-files-container3";
    private static final String REMOTE_HOST4 = "comp20081-files-container4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ntu-user";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;
    
    public String checkFilePath(String fileName){
        Session jschSession = null;
        String remoteFile = "/root/" + fileName + "1.zip";
        String res = "";
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST1, REMOTE_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;
            channelSftp.stat(remoteFile);
            res = "File already exists.";
            channelSftp.exit();
            
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE){
                res = "File does not exist.";
            }else{
                e.printStackTrace();
            }
        }catch(JSchException ex){
            ex.printStackTrace();
        }finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        return res;
    }
    
    public void sendContainer(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String localFile = "/home/ntu-user/App" + "/" + fileName + ".zip";
        String remoteFile = "/root/" + fileName + ".zip";
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            channelSftp.put(localFile, remoteFile);
            channelSftp.exit();
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void sendAllContainers(String fileName, int i){
        switch (i) {
            case 1:
                sendContainer(fileName + "1", REMOTE_HOST1);
                break;
            case 2:
                sendContainer(fileName + "2", REMOTE_HOST2);
                break;
            case 3:
                sendContainer(fileName + "3", REMOTE_HOST3); 
                break;
            case 4:
                sendContainer(fileName + "4", REMOTE_HOST4);
                break;
            default:
                break;
        }
    }
    
    public void getContainer(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String localFile = "/home/ntu-user/App" + "/" + fileName + ".zip";
        String remoteFile = "/root/" + fileName + ".zip";
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from remote to local server
            channelSftp.get(remoteFile, localFile);            
            channelSftp.exit();
  
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void getAllContainers(String fileName, int i){
        switch (i) {
            case 1:
                getContainer(fileName + "1", REMOTE_HOST1);
                break;
            case 2:
                getContainer(fileName + "2", REMOTE_HOST2);
                break;
            case 3:
                getContainer(fileName + "3", REMOTE_HOST3); 
                break;
            case 4:
                getContainer(fileName + "4", REMOTE_HOST4);
                break;
            default:
                break;
        }
    }
    
    public float fileSize(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String remoteFile = "/root/" + fileName + ".zip";
        float size = 0;
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            SftpATTRS attributes = channelSftp.stat(remoteFile);
            size += attributes.getSize();
            channelSftp.exit();
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        return size;
    }
    
    public float getAllSizes(String fileName, int i){
        float fileSize = 0;
        switch (i) {
            case 1:
                fileSize = fileSize(fileName + "1", REMOTE_HOST1);
                return fileSize;
            case 2:
                fileSize = fileSize(fileName + "2", REMOTE_HOST2);
                return fileSize;
            case 3:
                fileSize = fileSize(fileName + "3", REMOTE_HOST3); 
                return fileSize;
            case 4:
                fileSize = fileSize(fileName + "4", REMOTE_HOST4);
                return fileSize;
            default:
                break;
        }
        return fileSize;
    }
    
    public void deleteFromContainer(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String remoteFile = "/root/" + fileName + ".zip";
        try{
            JSch jsch = new JSch();
            jsch.setKnownHosts("/home/mkyong/.ssh/known_hosts");
            
            jschSession = jsch.getSession(USERNAME, REMOTE_HOST, REMOTE_PORT);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            jschSession.setConfig(config);
            
            jschSession.setPassword(PASSWORD);

            // 10 seconds session timeout
            jschSession.connect(SESSION_TIMEOUT);

            Channel sftp = jschSession.openChannel("sftp");

            // 5 seconds timeout
            sftp.connect(CHANNEL_TIMEOUT);

            ChannelSftp channelSftp = (ChannelSftp) sftp;

            // transfer file from local to remote server
            channelSftp.rm(remoteFile);
            channelSftp.exit();
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void deleteFromAllContainers(String fileName, int i){
        switch (i) {
            case 1:
                deleteFromContainer(fileName + "1", REMOTE_HOST1);
                break;
            case 2:
                deleteFromContainer(fileName + "2", REMOTE_HOST2);
                break;
            case 3:
                deleteFromContainer(fileName + "3", REMOTE_HOST3); 
                break;
            case 4:
                deleteFromContainer(fileName + "4", REMOTE_HOST4);
                break;
            default:
                break;
        }
    } 
}
