package model;

/** Representa os produtos do tipo Item Casa. */
public class ItemCasa extends Produto {
	private String material;
	private String cor;
	private String tipo;

	public ItemCasa(int codigo, String nome, String material, String cor, String tipo) {
		super(codigo, nome);
		this.material = material;
		this.cor = cor;
		this.tipo = tipo;
	}

}
