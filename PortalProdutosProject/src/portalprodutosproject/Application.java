/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import Entity.Compra;
import Entity.Eletronico;
import Entity.ItemCasa;
import Entity.Livro;
import Entity.Loja;
import Entity.LojaProduto;
import Entity.Produto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class Application {

    private List<Loja> listaLojas;
    private List<LojaProduto> listaLojaProdutos;
    private Map<Integer, Produto> mapProdutos;
    private List<Compra> listaCompras;
    private Scanner scan;
    private PortalFileStream pfs = new PortalFileStream();

    public Application(List<Loja> listaLojas,
            List<LojaProduto> listaLojaProdutos,
            Map<Integer, Produto> mapProdutos) {

        this.listaLojas = listaLojas;
        this.listaLojaProdutos = listaLojaProdutos;
        this.mapProdutos = mapProdutos;
        
        instanciaCompras();
    }

    private void instanciaCompras() {
        listaCompras = (ArrayList<Compra>) pfs.Read("Compras.dat");

        if (listaCompras == null) {
            listaCompras = new ArrayList<>();
            pfs.Save("Compras.dat", listaCompras);
        }
    }

    public void startApplication() {
        String choice = "";

        System.out.println("\tOlá, seja bem vindo a Portal de Produtos.");

        scan = new Scanner(System.in);

        while (!choice.toLowerCase().equals("sair")) {
            System.out.println("Selecione a opção:");
            System.err.println("[1] Pesquisar por produtos");
            System.err.println("[2] Ver lojas disponíveis");

            choice = scan.next();

            switch (choice) {
                case "1":
                    pesquisarProdutos();
                    break;
                case "2":
                    verLojas();
                    break;
            }
            
            System.err.println("Digite 'sair' para sair do menu, ou digite qualquer coisa para continuar no Portal Produtos: ");
            choice = scan.next();
        }

    }

    private void pesquisarProdutos() {
        String choice = "";
        
        while (!choice.toLowerCase().equals("voltar")) {
            System.err.println("Digite o nome do produto, tipo, código ou loja que deseja pesquisar: ");
            String search = scan.next().toLowerCase();

            HashSet<LojaProduto> setLojaProduto = new HashSet<>();

            Integer idProduto = isNumeric(search) ? Integer.parseInt(search) : null;
            if (idProduto != null && mapProdutos.containsKey(idProduto)) {
                setLojaProduto.addAll(buscarLojaProduto(idProduto));
            }

            setLojaProduto.addAll(buscarLojaProduto(search));

            setLojaProduto.addAll(buscarLojaProdutoTipo(search));

            setLojaProduto.addAll(buscarLojaProdutoLoja(search));

            System.out.printf("%-4s  %-6s  %-5s  %-25s %-25s\n", "Cod", "IdLoja", "Valor", "NomeProduto", "Informações");
            for (LojaProduto lojaProduto : setLojaProduto) {
                System.out.println(lojaProduto.toString());
            }
            
            System.err.println("Digite 'voltar' para retornar ao menu, ou digite qualquer coisa para fazer outra pesquisa por produtos: ");
            choice = scan.next();
        }       
    }

    
    private void verLojas() {
        String choice = "";
        double total;
        
        System.out.printf("%-11s  %-14s  %s\n", "IdLoja", "NomeLoja", "Avaliacao");
        for(Loja loja : listaLojas)
        {
            System.out.println(loja.toString());
        }

        while (!choice.toLowerCase().equals("voltar")) {
            total = 0.0;
            System.err.println("Digite o IdLoja da loja que deseja pesquisar: ");
            String id = scan.next().toUpperCase();
            
            //verifica existencia da loja
            Loja loja = buscarLoja(id);
            
            for(LojaProduto lojaproduto : listaLojaProdutos)
            {
                if(lojaproduto.getLoja().equals(loja))
                {
                    System.out.println(lojaproduto.toString());
                    System.out.println(lojaproduto.getValor());
                    total += lojaproduto.getValor();
                }
            }
            
            //valor total da loja, soma produtos  
            System.out.println(String.format("Soma Total Loja [%s]: %.2f", id, total));
            
            System.err.println("Digite 'voltar' para retornar ao menu, ou digite qualquer coisa para fazer outra pesquisa por lojas: ");
            choice = scan.next();
        }        
    }

    
    /** Busca uma loja conforme o identificador. */
    private Loja buscarLoja(String id) 
    {
        for(Loja loja : listaLojas) 
        {
            if (loja.getIdentificador().equals(id))
                return loja;
        }
        return null;
    } 

    
    
    private boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
        
        
    /**
     * Buscar LojaProduto por ID do produto
     *
     * @param idProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProduto(int idProduto) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();

        for (LojaProduto lp : listaLojaProdutos) {
            if (lp.getProduto().getCod() == idProduto) {
                lstLojaProduto.add(lp);
            }
        }

        return lstLojaProduto;
    }

    /**
     * Buscar LojaProduto por nome do produto
     *
     * @param nomeProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProduto(String nomeProduto) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();

        for (LojaProduto lp : listaLojaProdutos) {
            if (lp.getProduto().getNome().toLowerCase().contains(nomeProduto)) {
                lstLojaProduto.add(lp);
            }
        }

        return lstLojaProduto;
    }

    /**
     * Buscar LojaProduto por loja
     *
     * @param nomeProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProdutoTipo(String tipoProduto) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();
        tipoProduto = tipoProduto.toLowerCase();

        for (LojaProduto lp : listaLojaProdutos) {
            switch (tipoProduto) {
                case "eletronico":
                case "eletrônico":
                    if (lp.getProduto() instanceof Eletronico) {
                        lstLojaProduto.add(lp);
                    }
                    break;
                case "itemcasa":
                    if (lp.getProduto() instanceof ItemCasa) {
                        lstLojaProduto.add(lp);
                    }
                    break;
                case "livro":
                    if (lp.getProduto() instanceof Livro) {
                        lstLojaProduto.add(lp);
                    }
                    break;
            }
        }

        return lstLojaProduto;
    }

    /**
     * Buscar LojaProduto por tipo do produto
     *
     * @param nomeProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProdutoLoja(String loja) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();
        loja = loja.toLowerCase();

        for (LojaProduto lp : listaLojaProdutos) {
            Loja l = lp.getLoja();
            if (l.getIdentificador().toLowerCase().contains(loja) || l.getNome().toLowerCase().contains(loja)) {
                lstLojaProduto.add(lp);
            }
        }

        return lstLojaProduto;
    }
}
