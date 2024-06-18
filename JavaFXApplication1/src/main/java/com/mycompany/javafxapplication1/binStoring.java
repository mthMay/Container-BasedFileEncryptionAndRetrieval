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

/**
 *
 * @author ntu-user
 */
public class binStoring {
    private static final String REMOTE_HOST1 = "comp20081-files-container1";
    private static final String REMOTE_HOST2 = "comp20081-files-container2";
    private static final String REMOTE_HOST3 = "comp20081-files-container3";
    private static final String REMOTE_HOST4 = "comp20081-files-container4";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ntu-user";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;
    
    public void createBin(String REMOTE_HOST){
        Session jschSession = null;
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
            channelSftp.mkdir("bin"); 

            
            channelSftp.exit();
            
        } catch (SftpException e) {
            e.printStackTrace();
        }catch(JSchException ex){
            ex.printStackTrace();
        }finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void createBinContainers(int i){
        switch (i) {
            case 1:
                createBin(REMOTE_HOST1);
                break;
            case 2:
                createBin(REMOTE_HOST2);
                break;
            case 3:
                createBin(REMOTE_HOST3);
                break;
            case 4:
                createBin(REMOTE_HOST4);
                break;
            default:
                break;
        }
    }
    
    public String checkFilePath(String fileName){
        Session jschSession = null;
        String remoteFile = "/root/bin/" + fileName + "1.zip";
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
    
    public void sendBin(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String rootRemoteFile = "/root/" + fileName + ".zip";
        String binRemoteFile = "/root/bin/" + fileName + ".zip";
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
            channelSftp.rename(rootRemoteFile, binRemoteFile);
            channelSftp.exit();
            
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void sendAllBins(String fileName, int i){
        switch (i) {
            case 1:
                sendBin(fileName + "1", REMOTE_HOST1);
                break;
            case 2:
                sendBin(fileName + "2", REMOTE_HOST2);
                break;
            case 3:
                sendBin(fileName + "3", REMOTE_HOST3); 
                break;
            case 4:
                sendBin(fileName + "4", REMOTE_HOST4);
                break;
            default:
                break;
        }
    }
    
    public void getBin(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String binRemoteFile = "/root/bin/" + fileName + ".zip";
        String rootRemoteFile = "/root/" + fileName + ".zip";
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
            channelSftp.rename(binRemoteFile, rootRemoteFile);
            channelSftp.exit();
  
        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
    }
    
    public void getAllBins(String fileName, int i){
        switch (i) {
            case 1:
                getBin(fileName + "1", REMOTE_HOST1);
                break;
            case 2:
                getBin(fileName + "2", REMOTE_HOST2);
                break;
            case 3:
                getBin(fileName + "3", REMOTE_HOST3); 
                break;
            case 4:
                getBin(fileName + "4", REMOTE_HOST4);
                break;
            default:
                break;
        }
    }
    
    public float fileSize(String fileName, String REMOTE_HOST){
        Session jschSession = null;
        String remoteFile = "/root/bin/" + fileName + ".zip";
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
        String remoteFile = "/root/bin/" + fileName + ".zip";
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
