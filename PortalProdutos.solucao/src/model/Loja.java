package model;

/** Representa as lojas. */
public class Loja {
	private String	ident;
	private String	nome;
	private int		avaliacao;

	public Loja(String ident, String nome, int aval) {
		this.ident = ident;
		this.nome = nome;
		this.avaliacao = aval;
	}

	/** Retorna o identificador da loja. */
	public String getIdent() {
		return this.ident;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Loja && ((Loja) obj).ident.equals(this.ident));
	}

}
