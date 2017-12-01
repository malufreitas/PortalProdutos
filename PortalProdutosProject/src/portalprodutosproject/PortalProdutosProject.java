/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import Entity.Eletronico;
import Entity.ItemCasa;
import Entity.Livro;
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

    private static List<Loja> listaLojas = new ArrayList<>();
    private static List<LojaProduto> listaLojaProdutos = new ArrayList<>();
    private static Scanner scan;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        listaLojas = new ArrayList<>();
        leLojas(listaLojas);

        lerProdutos();
        
        PortalFileStream pfs = new PortalFileStream();
        pfs.Save("listaLojaProdutos.dat", listaLojaProdutos);

        List<LojaProduto> lista = (ArrayList)pfs.Read("listaLojaProdutos.dat");
    }

    private static void leLojas(List<Loja> listaLojas) {
        try {
            String arquivo = ClassLoader.getSystemResource("lojas.txt").getPath();
            scan = new Scanner(new File(arquivo));

            while (scan.hasNextLine()) {
                String dados[] = scan.nextLine().split(";");

                Loja loja = new Loja(dados[0], dados[1], Integer.parseInt(dados[2]));
                listaLojas.add(loja);
            }

            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void lerProdutos() {
        try {
            String arquivo = ClassLoader.getSystemResource("produtos.txt").getPath();
            scan = new Scanner(new File(arquivo));

            Produto prod;
            while (scan.hasNextLine()) {
                String valores[] = scan.nextLine().split(";");

                prod = buscarProduto(Integer.parseInt(valores[1]));
                if (prod != null) {
                    instaciaLojaProduto(valores, prod);
                } else if (valores[2].equals("Livro")) {
                    instanciaLivro(valores);
                } else if (valores[2].equals("Eletronico")) {
                    instanciaEletronico(valores);
                } else if (valores[2].equals("ItemCasa")) {
                    instanciaItemCasa(valores);
                }
            }

            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Caso exista um produto que já foi instanciado antes, busca ele e e cria
     * uma instancia de LojaProduto, setando a loja o produto e o valor
     *
     * @param valores
     * @param listaLojas
     * @param produtoExistente
     * @param listaLojaProduto
     * @throws NumberFormatException
     */
    private static void instaciaLojaProduto(String[] valores,
            Produto produtoExistente) throws NumberFormatException {
        Loja loja = buscarLoja(valores[0]);
        LojaProduto lojaProduto = new LojaProduto(produtoExistente, loja, 
                Double.parseDouble(valores[5].replace(",", ".")), Integer.parseInt(valores[4]));
        listaLojaProdutos.add(lojaProduto);
    }
    
    /**
     * Instancia um objeto do tipo Livro e cria uma instacia de LojaProduto
     * idloja[0];codprod[1];tipo[2];nomeprod[3];qtde[4];valor[5];dados específicos do tipo[6]
     * @param valores
     * @param listaLojas
     * @return
     */
    private static void instanciaLivro(String[] valores) {
        Livro livro = new Livro();
        livro.setCod(Integer.parseInt(valores[1]));
        livro.setNome(valores[3]);
        livro.setAutor(valores[6]);
        livro.setNumeroPaginas(Integer.parseInt(valores[7]));
        
        instaciaLojaProduto(valores, livro);
    }
    
     /**
     * Instancia um objeto do tipo Eletronico e cria uma instacia de LojaProduto
     *
     * @param valores
     * @param listaLojas
     * @return
     */
    private static void instanciaEletronico(String[] valores) {
        // SUBM;  1002;  Eletronico;  Smartphone Moto G5 Plus;  900,00;  Motorola;  155;  Prata
        Eletronico eletronico = new Eletronico();
        eletronico.setCod(Integer.parseInt(valores[1]));
        eletronico.setNome(valores[3]);
        eletronico.setMarca(valores[6]);
        eletronico.setPeso(Double.parseDouble(valores[7]));
        eletronico.setCor(valores[8]);

        instaciaLojaProduto(valores, eletronico);
    }

    /**
     * Instancia um objeto do tipo ItemCasa e cria uma instacia de LojaProduto
     *
     * @param valores
     * @param listaLojas
     * @return
     */
    private static void instanciaItemCasa(String[] valores) {
        // idLoja[0], codProduto[1], tipoProduto[2], nome[3], valorLoja[4], dados específicos do produto[5+].         
        //items de casa possuem material, cor e tipo (cama/mesa/banho/outro).
        ItemCasa itemCasa = new ItemCasa();
        itemCasa.setCod(Integer.parseInt(valores[1]));
        itemCasa.setNome(valores[3]);
        itemCasa.setMaterial(valores[6]);
        itemCasa.setCor(valores[7]);
        itemCasa.setTipo(valores[8]);

        instaciaLojaProduto(valores, itemCasa);
    }
    
    private static Produto buscarProduto(int cod) {
        for (LojaProduto lojaProduto : listaLojaProdutos) {
            if (lojaProduto.getProduto().getCod() == cod) {
                return lojaProduto.getProduto();
            }
        }
        return null;
    }

    private static Loja buscarLoja(String cod) {
        for (Loja loja : listaLojas) {
            if (loja.getIdentificador().equals(cod)) {
                return loja;
            }
        }
        return null;
    }

}
