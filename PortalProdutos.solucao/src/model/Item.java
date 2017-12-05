package model;

/** Representa os itens de produto de uma loja. */
public class Item {
	private Produto	produto;
	private Loja	loja;
	private double	valor;

	public Item(Produto produto, Loja loja, double valor) {
		this.produto = produto;
		this.loja = loja;
		this.valor = valor;
	}

	/** Retorna o Produto. */
	public Produto getProduto() {
		return produto;
	}

	/** Retorna a Loja. */
	public Loja getLoja() {
		return loja;
	}

	/** Retorna o valor. */
	public double getValor() {
		return valor;
	}

	@Override
	public String toString() {
		return String.format("%-60.60s", produto) + String.format("%7.2f", valor) + " " + loja.getIdent();
	}

}
