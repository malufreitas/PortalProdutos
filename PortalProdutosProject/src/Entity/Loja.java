/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;


public class Loja implements Serializable, Comparable<Loja>{
    private String identificador;
    private String nome;
    private int avaliacao;

    public Loja(String identificador, String nome, int avaliacao) {
        this.identificador = identificador;
        this.nome = nome;
        this.avaliacao = avaliacao;
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the avaliacao
     */
    public int getAvaliacao() {
        return avaliacao;
    }

    /**
     * @param avaliacao the avaliacao to set
     */
    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
    
    
    @Override
    public String toString() {
        return "[" + identificador + "]    " + String.format("%-23s", nome) + avaliacao;
    }
    
    
    @Override
    public boolean equals(Object obj) {
	return (obj instanceof Loja && ((Loja) obj).identificador.equals(this.identificador));
    }

    @Override
    public int compareTo(Loja o) {
        if (this.getAvaliacao() < o.getAvaliacao()) {
            return 1;
        } else if (this.getAvaliacao() == o.getAvaliacao()) {
            return 0;
        }else{
            return -1;
        }
    }
    
}
