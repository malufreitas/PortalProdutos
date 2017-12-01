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
public class LojaProduto implements Serializable{

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
}
