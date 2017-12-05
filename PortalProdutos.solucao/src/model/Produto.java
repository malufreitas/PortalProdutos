package model;

/** Representa os produtos das lojas. */
public abstract class Produto {
	private int codigo;
	private String nome;

	public Produto(int codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	/** Retorna o código do produto. */
	public int getCodigo() {
		return codigo;
	}

	/** Retorna o nome do produto. */
	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "[" + codigo + "] " + nome;
	}
}