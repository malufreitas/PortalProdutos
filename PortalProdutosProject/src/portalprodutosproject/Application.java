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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
            System.out.println("[3] Realizar uma compra");
            System.out.println("[4] Ver compras realizadas");

            choice = scan.nextLine();

            switch (choice) {
                case "1":
                    pesquisarProdutos();
                    break;
                case "2":
                    verLojas();
                    break;
                case "3":
                    buyItem();
                    break;
                case "4":
                    verCompras();
                    break;
            }
        } while (!choice.toLowerCase().equals("sair"));
    }

    private void pesquisarProdutos() {
        String choice = "";

        clearConsole();
        while (!choice.toLowerCase().equals("voltar")) {
            System.out.println("\nDigite o nome do produto, tipo, código ou loja que deseja pesquisar ou 'voltar': ");
            String search = scan.nextLine().toLowerCase();
            
            if(search.toLowerCase().equals("voltar")) break;

            HashSet<LojaProduto> setLojaProduto = new HashSet<>();

            Integer idProduto = isNumeric(search) ? Integer.parseInt(search) : null;
            if (idProduto != null && mapProdutos.containsKey(idProduto)) {
                setLojaProduto.addAll(buscarLojaProduto(idProduto, true));
            }

            setLojaProduto.addAll(buscarLojaProduto(search, true));

            setLojaProduto.addAll(buscarLojaProdutoTipo(search, true));

            setLojaProduto.addAll(buscarLojaProdutoLoja(search, true));
            
            System.out.printf("%-4s  %-6s  %-5s  %-25s %-25s\n", "Cod", "IdLoja", "Valor", "NomeProduto", "Informações");
            List<LojaProduto> listaExibida = new ArrayList<>(setLojaProduto);
            Collections.sort(listaExibida);
            for (LojaProduto lojaProduto : listaExibida) {
                System.out.println(lojaProduto.toString());
            }

            System.out.println("Deseja comprar algo? (S ou N)");
            String isBuying = scan.nextLine().toUpperCase();

            if (isBuying.equals("S")) {
                buyItem();
            }
        }
    }

    private void verLojas() {
        clearConsole();
        String id = "";
        double total;

        System.out.printf("%-11s  %-14s  %s\n", "IdLoja", "NomeLoja", "Avaliacao");
        Collections.sort(listaLojas);
        for (Loja loja : listaLojas) {
            System.out.println(loja.toString());
        }

        do {
            total = 0.0;
            System.out.println("\nDigite o Identificador da loja que deseja visualizar ou 'voltar': ");
            id = scan.nextLine();

            List<LojaProduto> lojasExibidas = buscarLojaProdutoId(id, false);

            if (lojasExibidas.isEmpty()) {
                System.err.println("Nenhuma loja encontrada.");
            } else {
                clearConsole();
                System.out.printf("\n%-4s  %-6s  %-5s  %-25s %-25s\n", "Cod", "IdLoja", "Valor", "NomeProduto", "Informações");
                Collections.sort(lojasExibidas);
                for (LojaProduto lojaproduto : lojasExibidas){
                    System.out.println(lojaproduto.toString());
                    
                    total += lojaproduto.getValor() * lojaproduto.getQuantidade();
                }

                //valor total da loja, soma produtos  
                System.out.println(String.format("Capital Total da Loja [%s]: %.2f", id.toUpperCase(), total));
            }
        } while (!id.toLowerCase().equals("voltar"));
    }

    /**
     * Busca uma loja conforme o identificador.
     */
    private List<LojaProduto> buscarLojaProdutoId(String id, boolean filtrarQuantidade) {
        List<LojaProduto> listaLp = new ArrayList<>();
        id = id.toUpperCase();
        for (LojaProduto lojaProduto : listaLojaProdutos) {
            if (lojaProduto.getLoja().getIdentificador().equals(id)) {
                if (filtrarQuantidade && lojaProduto.getQuantidade() > 0) {
                    listaLp.add(lojaProduto);
                } else if (!filtrarQuantidade) {
                    listaLp.add(lojaProduto);
                }
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
        if (str.isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Buscar LojaProduto por ID do produto
     *
     * @param idProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProduto(int idProduto, boolean filtrarQuantidade) {
        List<LojaProduto> listaLp = new ArrayList<>();

        for (LojaProduto lp : listaLojaProdutos) {
            if (lp.getProduto().getCod() == idProduto) {
                if (filtrarQuantidade && lp.getQuantidade() > 0) {
                    listaLp.add(lp);
                } else if (!filtrarQuantidade) {
                    listaLp.add(lp);
                }
            }
        }

        return listaLp;
    }

    /**
     * Buscar LojaProduto por nome do produto
     *
     * @param nomeProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProduto(String nomeProduto, boolean filtrarQuantidade) {
        List<LojaProduto> listaLp = new ArrayList<>();

        for (LojaProduto lp : listaLojaProdutos) {
            if (lp.getProduto().getNome().toLowerCase().contains(nomeProduto)) {
                if (filtrarQuantidade && lp.getQuantidade() > 0) {
                    listaLp.add(lp);
                } else if (!filtrarQuantidade) {
                    listaLp.add(lp);
                }
            }
        }

        return listaLp;
    }

    /**
     * Buscar LojaProduto por loja
     *
     * @param nomeProduto
     * @return
     */
    private List<LojaProduto> buscarLojaProdutoTipo(String tipoProduto, boolean filtrarQuantidade) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();
        tipoProduto = tipoProduto.toLowerCase();

        for (LojaProduto lp : listaLojaProdutos) {

            switch (tipoProduto) {
                case "eletronico":
                case "eletrônico":
                    if (lp.getProduto() instanceof Eletronico) {
                        if (filtrarQuantidade && lp.getQuantidade() > 0) {
                            lstLojaProduto.add(lp);
                        } else if (!filtrarQuantidade) {
                            lstLojaProduto.add(lp);
                        }
                    }
                    break;
                case "itemcasa":
                    if (lp.getProduto() instanceof ItemCasa) {
                        if (filtrarQuantidade && lp.getQuantidade() > 0) {
                            lstLojaProduto.add(lp);
                        } else if (!filtrarQuantidade) {
                            lstLojaProduto.add(lp);
                        }
                    }
                    break;
                case "livro":
                    if (lp.getProduto() instanceof Livro) {
                        if (filtrarQuantidade && lp.getQuantidade() > 0) {
                            lstLojaProduto.add(lp);
                        } else if (!filtrarQuantidade) {
                            lstLojaProduto.add(lp);
                        }
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
    private List<LojaProduto> buscarLojaProdutoLoja(String loja, boolean filtrarQuantidade) {
        List<LojaProduto> lstLojaProduto = new ArrayList<>();
        loja = loja.toLowerCase();

        for (LojaProduto lp : listaLojaProdutos) {
            Loja l = lp.getLoja();
            if (l.getIdentificador().toLowerCase().contains(loja) || l.getNome().toLowerCase().contains(loja)) {
                if (filtrarQuantidade && lp.getQuantidade() > 0) {
                    lstLojaProduto.add(lp);
                } else if (!filtrarQuantidade) {
                    lstLojaProduto.add(lp);
                }
            }
        }

        return lstLojaProduto;
    }

    private LojaProduto buscarLojaProduto(String idLoja, int idProduto) {
        for (LojaProduto lojaProduto : buscarLojaProdutoId(idLoja, false)) {
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
        String choice = "";

        try {
            System.out.println("\nDigite o IdLoja IdProduto Quantidade (Nesta ordem) dos produtos que deseja ou 'finalizar' para terminar a transação: ");
            List<LojaProduto> listaCarrinho = new ArrayList<>();
            Map<LojaProduto, Integer> mapProdutoQuantidade = new HashMap<LojaProduto, Integer>();
            double valorFinal = 0.0;
            int quantidadeTotal = 0;

            do {
                System.out.println(quantidadeTotal + " itens no carrinho. Valor Total: " + valorFinal);
                choice = scan.nextLine().toLowerCase();
                String[] item = choice.split(" ");

                try {
                    LojaProduto lp = buscarLojaProduto(item[0], Integer.parseInt(item[1]));
                    int quantidade = Integer.parseInt(item[2]);

                    if (lp == null) {
                        throw new Exception("Produto inválido ou não encontrado!");
                    }
                    if (lp.getQuantidade() < quantidade) {
                        throw new Exception("Estoque insuficiente. " + lp.getQuantidade() + " unidades disponíveis.");
                    }

                    for (int i = 0; i < quantidade; i++) {
                        valorFinal += lp.getValor();
                    }

                    lp.setQuantidade(lp.getQuantidade() - quantidade);
                    quantidadeTotal += quantidade;
                    
                    if(!listaCarrinho.contains(lp)) listaCarrinho.add(lp);
                    
                    if(mapProdutoQuantidade.containsKey(lp))
                        mapProdutoQuantidade.put(lp, mapProdutoQuantidade.get(lp) + quantidade);
                    else mapProdutoQuantidade.put(lp, quantidade);
                }catch(NumberFormatException ex){
                    System.err.println("Produto inválido ou não encontrado!");
                } 
                catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

                System.out.println("\nDigite 'finalizar' para terminar a compra.");
            } while (!choice.toLowerCase().equals("finalizar"));

            clearConsole();
            System.out.println("Carrinho de compras:");
            for (LojaProduto lojaProduto : listaCarrinho) {
                System.out.println(mapProdutoQuantidade.get(lojaProduto.getProduto().getCod()) + "x " + lojaProduto.toString());
            }

            System.out.println("Valor total: " + valorFinal);

            System.out.println("\nConfirmar? 'S' ou 'N'");
            String confirm = scan.nextLine();
            if (listaCarrinho.size() > 0 && confirm.toUpperCase().equals("S")) {
                Compra compraAtual = new Compra();

                compraAtual.setValorFinal(valorFinal);
                compraAtual.setDataCompra(new Date());
                compraAtual.setListaLojaProduto(listaCarrinho);
                compraAtual.setMapProdutoQuantidade(mapProdutoQuantidade);

                listaCompras.add(compraAtual);
                pfs.Save("Compras.dat", listaCompras);
                pfs.Save("Produtos.dat", mapProdutos, listaLojaProdutos);

                System.out.println("\nCompra realizada com sucesso!");
            } else {
                System.out.println("\n\tTransação cancelada.");
                //Roolback
                Object[] prods = pfs.Read("Produtos.dat", mapProdutos, listaLojaProdutos);
                listaLojaProdutos = (ArrayList<LojaProduto>) prods[1];
            }
        } catch (Exception ex) {
            System.out.println("Erro na transação. Operação cancelada!");
            //Roolback
            Object[] prods = pfs.Read("Produtos.dat", mapProdutos, listaLojaProdutos);
            listaLojaProdutos = (ArrayList<LojaProduto>) prods[1];
        }

    }

    private void verCompras() {
        for (Compra compra : listaCompras) {
            System.out.println(compra.toString());
        }
        
        System.out.println("\nPressione enter para continuar.");
        scan.nextLine();
    }
}
