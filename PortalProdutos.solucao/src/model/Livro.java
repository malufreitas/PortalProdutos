package model;

/** Representa os produtos do tipo Livro. */
public class Livro extends Produto {
	private String autor;
	private int numPaginas;

	public Livro(int codigo, String nome, String autor, int numPaginas) {
		super(codigo, nome);
		this.autor = autor;
		this.numPaginas = numPaginas;
	}

}
