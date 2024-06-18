/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javafxapplication1;

import java.util.List;
import java.util.Arrays;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
/**
 *
 * @author ntu-user
 */
public class terminalCode {
    public String runCommand(String cmd) throws IOException {
        String res = "";
        String[] validCommand = {"mv", "cp", "ls", "mkdir", "ps", "whoami","tree", "nano"};
        List commandList = Arrays.asList(validCommand);
        String[] cmds = cmd.split(" ");
        if(commandList.contains(cmds[0])){
            var processBuilder = new ProcessBuilder();

            if(cmd.equals("nano")){
                processBuilder.command("terminator", "-e", "nano");
            }
            else{
                processBuilder.command(cmds);
            }

            var process = processBuilder.start();
            
            try(var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;

                if((line = reader.readLine()) != null){
                    res += line;
                    while ((line = reader.readLine()) != null){
                        res += line + "\n";
                    } 
                } else {
                    var error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                    while((line = error.readLine()) != null) {
                        res += line + "\n";
                    }
                }
            }
        } else {
            res = "Command not recognised.";
        }
        return res;
    } 
}
