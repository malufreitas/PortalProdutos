/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

/**
 *
 * @author 20161bsi0349
 */
public class LojaProduto implements Serializable {

    public LojaProduto(Produto produto, Loja loja, double valor, int quantidade) {
        this.valor = valor;
        this.produto = produto;
        this.loja = loja;
        this.quantidade = quantidade;
    }

    private double valor;
    private Produto produto;
    private Loja loja;
    private int quantidade;

    /**
     * @return the valor
     */
    public double getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(double valor) {
        this.valor = valor;
    }

    /**
     * @return the produto
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * @param produto the produto to set
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * @return the loja
     */
    public Loja getLoja() {
        return loja;
    }

    /**
     * @param loja the loja to set
     */
    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        if (this.getProduto() instanceof Livro) {
            Livro livro = (Livro) this.getProduto();
            String info = String.format("Autor: %-25s %d Pág.",
                    livro.getAutor(),
                    livro.getNumeroPaginas());

            return String.format("%-4s  %-6s  %-5s  %-25s %-25s", 
                    livro.getCod() + "",
                    loja.getIdentificador(), 
                    this.getValor(), 
                    livro.getNome(),
                    info);

        } else if (this.getProduto() instanceof Eletronico) {
            Eletronico elet = (Eletronico) this.getProduto();
            String info = String.format("Marca: %s Cor: %s Peso: %.2f",
                    elet.getMarca(), elet.getCor(), elet.getPeso());

            return String.format("%-4s  %-6s  %-5s  %-25s %-25s",
                    elet.getCod(), 
                    loja.getIdentificador(), 
                    this.getValor(),
                    elet.getNome(), info);

        } else if (this.getProduto() instanceof ItemCasa) {
            //items de casa possuem material, cor e tipo (cama/mesa/banho/outro).
            ItemCasa ic = (ItemCasa) this.getProduto();
            String info = String.format("Material: %s Cor: %s Tipo: %s",
                    ic.getMaterial(), ic.getCor(), ic.getTipo());

            return String.format("%-4s  %-6s  %-5s  %-25s %-25s",
                    ic.getCod(), 
                    loja.getIdentificador(), 
                    this.getValor(),
                    ic.getNome(), info);
        }
        
        return "";
    }
}
