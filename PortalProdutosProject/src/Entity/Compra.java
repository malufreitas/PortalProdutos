/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author andre.martins
 */
public class Compra implements Serializable{
    private Produto produto;
    private Loja loja;
    private double valorPago;
    private Date dataCompra;

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
}
