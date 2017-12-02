/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portalprodutosproject;

import Entity.Compra;
import Entity.Loja;
import Entity.LojaProduto;
import Entity.Produto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author André Martins
 */
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
        clearScreen();
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
                    PesquisarProdutos();
            }

        }

    }

    private void PesquisarProdutos() {
        System.err.println("Digite o nome do produto, tipo, código ou loja que deseja pesquisar:");
        String search = scan.next().toLowerCase();
        
        List<LojaProduto> productExibition = new ArrayList<>();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
