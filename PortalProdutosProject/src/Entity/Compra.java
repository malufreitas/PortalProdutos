/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;


public class Compra implements Serializable{
   private LojaProduto lojaProduto;
    private double valorPago;
    private Date dataCompra;
    private int quantidade;

    /**
     * @return the lojaProduto
     */
    public LojaProduto getLojaProduto() {
        return lojaProduto;
    }

    /**
     * @param lojaProduto the lojaProduto to set
     */
    public void setLojaProduto(LojaProduto lojaProduto) {
        this.lojaProduto = lojaProduto;
    }

    /**
     * @return the valorPago
     */
    public double getValorPago() {
        return valorPago;
    }

    /**
     * @param valorPago the valorPago to set
     */
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
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
        return quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    
}
