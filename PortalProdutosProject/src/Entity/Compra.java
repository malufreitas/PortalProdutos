/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compra implements Serializable {

    private List<LojaProduto> listaLojaProduto;
    private Map<LojaProduto, Integer> mapProdutoQuantidade;
    private double valorFinal;
    private Date dataCompra;

    public Compra() {
        listaLojaProduto = new ArrayList<>();
        mapProdutoQuantidade = new HashMap<LojaProduto, Integer>();
    }

    @Override
    public String toString() {
        String toShow = "\n";
        
        toShow += "Data da compra: " + this.dataCompra.toString() + "\n";
        toShow += "Carrinho de compras:\n";
        
        for (LojaProduto lp : listaLojaProduto) {
            toShow += mapProdutoQuantidade.get(lp) + "x " + lp.toString() + "\n";
        }
        
        toShow += "Quantidade Total: " + getQuantidade() + "\n";
        toShow += "Valor final: " + valorFinal;
        
        return toShow;
    }

    /**
     * @return the listaLojaProduto
     */
    public List<LojaProduto> getListaLojaProduto() {
        return listaLojaProduto;
    }

    /**
     * @param listaLojaProduto the listaLojaProduto to set
     */
    public void setListaLojaProduto(List<LojaProduto> listaLojaProduto) {
        this.listaLojaProduto = listaLojaProduto;
    }

    /**
     * @return the mapProdutoQuantidade
     */
    public Map<LojaProduto, Integer> getMapProdutoQuantidade() {
        return mapProdutoQuantidade;
    }

    /**
     * @param mapProdutoQuantidade the mapProdutoQuantidade to set
     */
    public void setMapProdutoQuantidade(Map<LojaProduto, Integer> mapProdutoQuantidade) {
        this.mapProdutoQuantidade = mapProdutoQuantidade;
    }

    /**
     * @return the valorFinal
     */
    public double getValorFinal() {
        return valorFinal;
    }

    /**
     * @param valorFinal the valorFinal to set
     */
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    /**
     * @return the dataCompra
     */
    public Date getDataCompra() {
        return dataCompra;
    }

    /**
     * @param dataCompra the dataCompra to set
     */
    public void setDataCompra(Date dataCompra) {
        this.dataCompra = dataCompra;
    }

    /**
     * @return the quantidade
     */
    public int getQuantidade() {
        int quantidade = 0;
        
        for (LojaProduto lp : mapProdutoQuantidade.keySet()) {
            quantidade += mapProdutoQuantidade.get(lp);
        }
        
        return quantidade;
    }
}
