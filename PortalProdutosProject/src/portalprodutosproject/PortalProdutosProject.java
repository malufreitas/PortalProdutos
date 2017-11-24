/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import Entity.Loja;
import Entity.LojaProduto;
import Entity.Produto;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author andre.martins
 */
public class PortalProdutosProject {

    private static List<Loja> listaLojas;
    private static List<LojaProduto> listaLojaProdutos;
    private static Scanner scan;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        listaLojas = new ArrayList<>();
        leLojas(listaLojas);
        
        listaLojaProdutos = lerProdutos(listaLojas);
        
        
    }

    private static void leLojas(List<Loja> listaLojas) {
        try{
            String arquivo = ClassLoader.getSystemResource("lojas.txt").getPath();
            scan = new Scanner(new File(arquivo));
            
            while(scan.hasNextLine()){
                String dados[] = scan.nextLine().split(";");
                
                Loja loja = new Loja(dados[0], dados[1], Integer.parseInt(dados[2]));
                listaLojas.add(loja);
            }
            
            scan.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private static List<LojaProduto> lerProdutos(List<Loja> listaLojas) {
        try{
            String arquivo = ClassLoader.getSystemResource("produtos.txt").getPath();
            scan = new Scanner(new File(arquivo));
            
            List<LojaProduto> listaLojaProduto = new ArrayList<>();
            
            Produto prod;
            while(scan.hasNextLine()){
                String dados[] = scan.nextLine().split(";");
                
                prod = buscarProduto(Integer.parseInt(dados[1]));
            }
            
            scan.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        
        return null;
    }

    private static Produto buscarProduto(int cod) {
        for (LojaProduto lojaProduto : listaLojaProdutos) {
            if(lojaProduto.getProduto().getCod() == cod){
                return lojaProduto.getProduto();
            }
        }
        return null;
    }
    
    private static Loja buscarLoja(String cod) {
        for (Loja loja : listaLojas) {
            if(loja.getIdentificador().equals(cod)){
                return loja;
            }
        }
        return null;
    }
    
    
    
}
