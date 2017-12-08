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
        clearConsole();

        listaCompras = (ArrayList<Compra>) pfs.Read("Compras.dat");
        if (listaCompras == null) {
            listaCompras = new ArrayList<>();
            pfs.Save("Compras.dat", listaCompras);
        }
    }

    public void startApplication() {
        String choice = "";
        scan = new Scanner(System.in);

        do {
            clearConsole();
            System.out.println("\tOlá, seja bem vindo a Portal de Produtos.");

            System.out.println("Selecione a opção ou digite 'sair': ");
            System.out.println("[1] Pesquisar por produtos");
            System.out.println("[2] Ver lojas disponíveis");

            choice = scan.nextLine();

            switch (choice) {
                case "1":
                    pesquisarProdutos();
                    break;
                case "2":
                    verLojas();
                    break;
            }
        } while (!choice.toLowerCase().equals("sair"));
    }

    private void pesquisarProdutos() {
        String choice = "";

        while (!choice.toLowerCase().equals("voltar")) {
            clearConsole();
            System.out.println("Digite o nome do produto, tipo, código ou loja que deseja pesquisar: ");
            String search = scan.nextLine().toLowerCase();

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
            
            System.out.println("Deseja comprar algo? (S ou N)");
            String isBuying = scan.nextLine().toUpperCase();

            if (isBuying.equals("S") ) {
                buyItem();
            }

            System.out.println("Digite 'voltar' para retornar ao menu, ou digite qualquer coisa para fazer outra pesquisa por produtos: ");
            choice = scan.next();
        }
    }

    private void verLojas() {
        clearConsole();
        String id = "";
        double total;

        System.out.printf("%-11s  %-14s  %s\n", "IdLoja", "NomeLoja", "Avaliacao");
        for (Loja loja : listaLojas) {
            System.out.println(loja.toString());
        }

        do {
            total = 0.0;
            System.out.println("\nDigite o Identificador da loja que deseja visualizar ou 'voltar': ");
            id = scan.next();

            List<LojaProduto> lojasExibidas = buscarLojaProdutoId(id);

            if (lojasExibidas.isEmpty()) {
                clearConsole();
                System.err.println("Nenhuma loja encontrada.");
            } else {
                clearConsole();
                System.out.printf("\n%-4s  %-6s  %-5s  %-25s %-25s\n", "Cod", "IdLoja", "Valor", "NomeProduto", "Informações");

                for (LojaProduto lojaproduto : lojasExibidas) {
                    System.out.println(lojaproduto.toString());
                    total += lojaproduto.getValor();
                }

                //valor total da loja, soma produtos  
                System.out.println(String.format("Capital Total da Loja [%s]: %.2f", id.toUpperCase(), total));
            }
        } while (!id.toLowerCase().equals("voltar"));
    }

    /**
     * Busca uma loja conforme o identificador.
     */
    private List<LojaProduto> buscarLojaProdutoId(String id) {
        List<LojaProduto> listaLp = new ArrayList<>();
        id = id.toUpperCase();
        for (LojaProduto lojaProduto : listaLojaProdutos) {
            if (lojaProduto.getLoja().getIdentificador().equals(id)) {
                listaLp.add(lojaProduto);
            }
        }
        return listaLp;
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

    private LojaProduto buscarLojaProduto(String idLoja, int idProduto) {
        for (LojaProduto lojaProduto : buscarLojaProdutoId(idLoja)) {
            if (lojaProduto.getProduto().getCod() == idProduto) {
                return lojaProduto;
            }
        }
        return null;
    }

    private void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    private void buyItem() {
        System.out.println("\nDigite o IdLoja IdProduto Quantidade (Nesta ordem)");

        try {
            String[] item = scan.nextLine().split(" ");

            if (item.length == 3 && Integer.parseInt(item[2]) > 0) {
                LojaProduto lp = buscarLojaProduto(item[0], Integer.parseInt(item[1]));
                int quantidade = Integer.parseInt(item[2]);
                double valorFinal = 0.0;
                
                for (int i = 0; i < quantidade; i++) {
                    valorFinal += lp.getValor();
                }
                
                Compra novaCompra = new Compra(lp, quantidade, valorFinal);
                
                listaCompras.add(novaCompra);
                pfs.Save("Compras.dat", listaCompras);
                
                lp.setQuantidade(lp.getQuantidade() - quantidade);  
                pfs.Save("Produtos.dat", mapProdutos, listaLojaProdutos);
                
                System.out.println("Compra realizada!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Erro na transação. Operação cancelada!");
        }

    }
}
