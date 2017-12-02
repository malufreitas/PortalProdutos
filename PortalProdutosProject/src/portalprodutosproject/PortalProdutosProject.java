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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author andre.martins
 */
public class PortalProdutosProject {

    private static List<Loja> listaLojas;
    private static List<LojaProduto> listaLojaProdutos;
    private static Map<Integer, Produto> mapProdutos;
    private static Scanner scan;
    private static PortalFileStream pfs = new PortalFileStream();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        listaLojas = new ArrayList<>();
        leLojas();
        lerProdutos();
       
        Application app = new Application(listaLojas, listaLojaProdutos, mapProdutos);
        app.startApplication();
    }

    /**
     * Lê as lojas do arquivo binário.
     * Caso não exista, lê do txt
     */
    private static void leLojas() {
        listaLojas = (ArrayList<Loja>) pfs.Read("lojas.dat");

        if (listaLojas == null) {
            listaLojas = new ArrayList<>();
            try {
                String arquivo = "resources/lojas.txt";
                scan = new Scanner(new File(arquivo));

                while (scan.hasNextLine()) {
                    String dados[] = scan.nextLine().split(";");

                    Loja loja = new Loja(dados[0], dados[1], Integer.parseInt(dados[2]));
                    listaLojas.add(loja);
                }

                scan.close();

                pfs.Save("lojas.dat", listaLojas);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lê MapProduto e LojaProduto do arquivo binario
     * Caso na exista, lê produtos do txt e cria LojaProduto
     */
    private static void lerProdutos() {
        Object[] prods = pfs.Read("Produtos.dat", mapProdutos, listaLojaProdutos);

        if (prods != null) {
            mapProdutos = (HashMap<Integer, Produto>) prods[0];
            listaLojaProdutos = (ArrayList<LojaProduto>) prods[1];
        } else if (prods == null) {
            listaLojaProdutos = new ArrayList<>();
            mapProdutos = new HashMap<>();

            try {
                String arquivo = "resources/produtos.txt";
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

                pfs.Save("Produtos.dat", mapProdutos, listaLojaProdutos);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
            Produto produtoExistente) {

        Loja loja = buscarLoja(valores[0]);
        LojaProduto lojaProduto = new LojaProduto(produtoExistente, loja,
                Double.parseDouble(valores[5].replace(",", ".")), Integer.parseInt(valores[4]));
        listaLojaProdutos.add(lojaProduto);

        if (!mapProdutos.containsKey(produtoExistente.getCod())) {
            mapProdutos.put(produtoExistente.getCod(), produtoExistente);
        }
    }

    /**
     * Instancia um objeto do tipo Livro e cria uma instacia de LojaProduto
     * idloja[0];codprod[1];tipo[2];nomeprod[3];qtde[4];valor[5];dados
     * específicos do tipo[6]
     *
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
        return mapProdutos.containsKey(cod) ? mapProdutos.get(cod) : null;
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
