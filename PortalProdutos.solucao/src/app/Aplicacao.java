package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.Eletronico;
import model.Item;
import model.ItemCasa;
import model.Livro;
import model.Loja;
import model.Produto;

/** Gerencia as a��es do portal de produtos. */
public class Aplicacao {
	private static Loja[]	lojas	= new Loja[6];	// array de lojas (fixo)
	private static Item[]	itens;					// array de itens
	private static int		count	= 0;			// total de itens
	private static Scanner	scan;					// scanner para leitura do arquivo e console

	/** M�todo main. */
	public static void main(String[] args) {
		// cria as lojas (est�ticas)
		lojas[0] = new Loja("AMER", "Americanas.com", 5);
		lojas[1] = new Loja("SARA", "Livraria Saraiva", 5);
		lojas[2] = new Loja("SUBM", "Submarino.com", 4);
		lojas[3] = new Loja("RICE", "Ricardo Eletro", 4);
		lojas[4] = new Loja("MAGA", "Magazine Luiza", 5);
		lojas[5] = new Loja("MEGA", "Megaloja Palace", 1);

		// l� os itens do arquivo e os cria
		lerDados("produtos.txt");

		// dispara o menu para as buscas
		abrirMenu();
	}

	/** L� os dados do arquivo de produtos e monta o array de itens. */
	private static void lerDados(String nomeArquivo) {
		try {
			String arquivo = ClassLoader.getSystemResource(nomeArquivo).getPath();
			scan = new Scanner(new File(arquivo), "UTF-8"); // leitura do arquivo com defini��o do encoding
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int numItens = scan.nextInt(); // l� o primeiro inteiro (itens)
		int numProds = scan.nextInt(); // l� o segundo inteiro (produtos): n�o usado aqui
		scan.nextLine(); // descarta a quebra de linha

		// para cada linha, monta os itens
		// [0] loja; [1] codigo; [2] tipo; [3] nome; [4] valor; [5-7] dados espec�ficos
		itens = new Item[numItens];
		while (scan.hasNextLine()) {
			// l� os dados
			String dados[] = scan.nextLine().split(";");
			// obt�m a loja
			Loja loja = buscarLoja(dados[0]);
			// busca o produto
			int codigo = Integer.parseInt(dados[1]);
			Produto prod = buscarProduto(codigo);
			// se o produto n�o existe ainda, o cria conforme o tipo
			if (prod == null) {
				String tipo = dados[2];
				String nome = dados[3];
				switch (tipo) {
				case "Livro":
					String autor = dados[5];
					int numPag = Integer.parseInt(dados[6]);
					prod = new Livro(codigo, nome, autor, numPag);
					break;
				case "Eletronico":
					String marca = dados[5];
					String peso = dados[6];
					String cor = dados[7];
					prod = new Eletronico(codigo, nome, marca, peso, cor);
					break;
				case "ItemCasa":
					String material = dados[5];
					String cori = dados[6];
					String tipoi = dados[7];
					prod = new ItemCasa(codigo, nome, material, cori, tipoi);
					break;
				}
			}
			double valor = Double.parseDouble(dados[4].replace(',', '.')); // captura o valor, trocando a v�rgula
			// cria o item e o adiciona � lista
			itens[count++] = new Item(prod, loja, valor);
		}
		scan.close();
	}

	/** Abre o menu principal com as op��es de busca. */
	private static void abrirMenu() {
		scan = new Scanner(System.in);
		int opcao = 0;
		do {
			System.out.println("\nPORTAL DE PRODUTOS");
			System.out.println("1. Listar por nome");
			System.out.println("2. Listar por tipo");
			System.out.println("3. Listar por loja");
			System.out.println("4. Listar todos os produtos");
			System.out.println("0. Sair");
			System.out.print("Selecione uma op��o: ");
			opcao = scan.nextInt();
			switch (opcao) {
			case 1:
				listarProdutosPorNome();
				break;
			case 2:
				listarProdutosPorTipo();
				break;
			case 3:
				listarProdutosPorLoja();
				break;
			case 4:
				listarTodosProdutos();
				break;
			default:
				System.out.println("Op��o inv�lida, tente novamente!");
				break;
			case 0:
			}
			System.out.print("<enter para continuar>");
			scan.nextLine();
			scan.nextLine();
		} while (opcao != 0);
		System.out.println("Aplica��o finalizada");
		scan.close();
	}

	/** Busca e lista todos os produtos (itens) conforme o nome. */
	private static void listarProdutosPorNome() {
		System.out.print("Informe o nome (ou parte): ");
		String nome = scan.next();
		System.out.println("# Produtos '" + nome + "'");
		for (Item item : itens) {
			if (item.getProduto().getNome().toUpperCase().contains(nome.toUpperCase())) {
				System.out.println(item);
			}
		}
	}

	/** Busca e lista todos os produtos (itens) de um tipo. */
	private static void listarProdutosPorTipo() {
		System.out.print("Informe o tipo de produto [(L)ivro; (E)letr�nico; (I)tem de Casa]: ");
		char tipo = scan.next().toUpperCase().charAt(0);
		switch (tipo) {
		case 'L':
			System.out.println("# Livros");
			for (Item item : itens) {
				if (item.getProduto() instanceof Livro)
					System.out.println(item);
			}
			break;
		case 'E':
			System.out.println("# Eletr�nicos");
			for (Item item : itens) {
				if (item.getProduto() instanceof Eletronico)
					System.out.println(item);
			}
			break;
		case 'I':
			System.out.println("# Itens de Casa");
			for (Item item : itens) {
				if (item.getProduto() instanceof ItemCasa)
					System.out.println(item);
			}
			break;
		default:
			System.out.println("Tipo n�o identificado!");
		}
	}

	/** Busca e lista todos os itens de uma loja. */
	private static void listarProdutosPorLoja() {
		System.out.print("Informe a loja (");
		for (Loja loja : lojas) {
			System.out.print(loja.getIdent() + " ");
		}
		System.out.print("): ");
		String ident = scan.next();
		Loja loja = buscarLoja(ident.toUpperCase());
		if (loja != null) {
			System.out.println("# Produtos da loja " + loja);
			for (Item item : itens) {
				if (item.getLoja().equals(loja)) {
					System.out.println(item);
				}
			}
		} else {
			System.out.println("Loja n�o encontrada: " + ident);
		}
	}

	/** Lista todos os produtos (itens) do portal. */
	private static void listarTodosProdutos() {
		for (Item item : itens) {
			System.out.println(item);
		}
	}

	/** Busca uma loja conforme o identificador. */
	private static Loja buscarLoja(String ident) {
		for (Loja loja : lojas) {
			if (loja.getIdent().equals(ident))
				return loja;
		}
		return null;
	}

	/** Busca um produto conforme o c�digo. */
	private static Produto buscarProduto(int codigo) {
		for (int i = 0; i < count - 1; i++) {
			if (itens[i].getProduto().getCodigo() == codigo)
				return itens[i].getProduto();
		}
		return null;
	}

}
