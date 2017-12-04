/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author andre
 */
public class PortalFileStream {
    private final String path = "resources/";

    /**
     * Salva um objeto em um arquivo específico
     * @param fileName Nome do arquivo a ser criado
     * @param item Item a ser salvo
     */
    public void Save(String fileName, Serializable item){
        try {
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
    
    /**
     * Salva vários objetos em um único arquivo específico
     * @param fileName Nome do arquivo a ser criado
     * @param items Array de itens a serem salvos
     */
    public void Save(String fileName, Object... items){
        try {
            FileOutputStream outFile = new FileOutputStream(path + fileName);
            ObjectOutputStream objOut = new ObjectOutputStream(outFile);
            
            for(int i = 0; i < items.length; i++){
                objOut.writeObject(items[i]);
            }
            
            objOut.close();
            outFile.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Lê um arquivo que contém um único objeto salvo.
     * @param fileName Nome do arquivo
     * @return Objeto presente no arquivo
     */
    public Object Read(String fileName){
        try {
            FileInputStream inputFile = new FileInputStream(path + fileName);
            ObjectInputStream objIn = new ObjectInputStream(inputFile);
            Object obj = objIn.readObject();
            return obj;
        } catch (FileNotFoundException ex) {
            System.err.println("Erro: arquivo " + fileName + " não encontrado.");
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
    /**
     * Lê um arquivo que contém vários objetos salvos na ordem de "targets"
     * @param fileName Nome do arquivo 
     * @param targets Variáveis "Alvo" de cada objeto lido
     * @return 
     */
    public Object[] Read(String fileName, Object... targets){
        try {
            FileInputStream inputFile = new FileInputStream(path + fileName);
            ObjectInputStream objIn = new ObjectInputStream(inputFile);
            Object[] result = new Object[targets.length]; 
            Object obj;
            
            for(int i = 0; i < targets.length; i++){    
                obj = objIn.readObject();
                Class objClass = obj.getClass();
                result[i] = objClass.cast(obj);
            }
            
            return result;
        } catch (FileNotFoundException ex) {
            System.err.println("Erro: arquivo " + fileName + " não encontrado.");
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    
}
