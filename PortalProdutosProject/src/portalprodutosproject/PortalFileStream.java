/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import Entity.LojaProduto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andre
 */
public class PortalFileStream {
    private String path = "resources/";
    
    public void Save(String fileName, Object... item){
        try {
            String path = "resources/";
            FileOutputStream outFile = new FileOutputStream(path + fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(outFile);
            objOut.writeObject(item);
            objOut.close();
            outFile.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Object Read(String fileName){
        try {
            FileInputStream inputFile = new FileInputStream(path + fileName);
            ObjectInputStream objIn = new ObjectInputStream(inputFile);
            Object obj = objIn.readObject();
            
            
            return obj;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PortalFileStream.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PortalFileStream.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PortalFileStream.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
