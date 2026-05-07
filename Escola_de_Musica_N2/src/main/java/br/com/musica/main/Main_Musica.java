package br.com.musica.main;


import java.util.List;
import java.util.Map;
import java.util.Scanner;

import br.com.musica.classes.Curso;
import br.com.musica.classes.CursoMusicaFactory;
import br.com.musica.classes.GestaoCurso;
import br.com.musica.classes.AvaliacaoNaoElegivel;
import br.com.musica.interfaces.Avaliacao;

public class Main_Musica {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		CursoMusicaFactory objCursoMusica = new CursoMusicaFactory();
		GestaoCurso gestaoCurso = new GestaoCurso(objCursoMusica);

		try {
			int opcao;

			do {
				System.out.println("\n=== MENU ESCOLA DE MUSICA ===");
				System.out.println("1 - Cadastrar curso");
				System.out.println("2 - Listar cursos");
				System.out.println("3 - Pesquisar cursos");
				System.out.println("4 - Atualizar curso");
				System.out.println("5 - Remover curso");
				System.out.println("6 - Executar curso (avaliar/iniciar/encerrar)");
				System.out.println("0 - Sair");
				System.out.print("Escolha uma opcao: ");

				opcao = lerInteiro(scanner);

				if (opcao == 1) {
					criarCurso(scanner, gestaoCurso);
				} else if (opcao == 2) {
					listarCursos(gestaoCurso);
				} else if (opcao == 3) {
					pesquisarCursos(scanner, gestaoCurso);
				} else if (opcao == 4) {
					atualizarCurso(scanner, gestaoCurso);
				} else if (opcao == 5) {
					removerCurso(scanner, gestaoCurso);
				} else if (opcao == 6) {
					executarCurso(scanner, gestaoCurso);
				} else if (opcao != 0) {
					System.out.println("Opcao invalida. Tente novamente.");
				}
			} while (opcao != 0);

			System.out.println("Programa encerrado.");
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		} finally {
			scanner.close();
		}
	}

	private static void criarCurso(Scanner scanner, GestaoCurso gestaoCurso) {
		try {
			String nomeCurso = lerNomeCurso(scanner);
			int duracaoMeses = lerInteiroComPrompt(scanner, "Digite a duracao do curso em meses: ");
			int numeroAlunos = lerInteiroComPrompt(scanner, "Digite o numero de alunos: ");
			String nomeProfessor = lerTextoComPrompt(scanner, "Digite o nome do professor: ");

			int id = gestaoCurso.criarCurso(nomeCurso, duracaoMeses, numeroAlunos, nomeProfessor);
			System.out.println("Curso cadastrado com sucesso. ID: " + id);
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar curso: " + e.getMessage());
		}
	}

	private static void listarCursos(GestaoCurso gestaoCurso) {
		Map<Integer, Curso> cursos = gestaoCurso.listarCursos();
		imprimirRegrasCursos();

		if (cursos.isEmpty()) {
			System.out.println("Nenhum curso cadastrado.");
			return;
		}

		System.out.println("\n=== CURSOS CADASTRADOS ===");
		for (Map.Entry<Integer, Curso> entry : cursos.entrySet()) {
			imprimirCurso(entry.getKey(), entry.getValue());
		}
	}

	private static void imprimirRegrasCursos() {
		System.out.println("\n=== REGRAS DOS CURSOS ===");
		System.out.println("Curso: CursoGuitarra");
		System.out.println("Duracao em meses: minimo 2, maximo 12");
		System.out.println("Numero de alunos: minimo 1");
		System.out.println("Professor: obrigatorio");

		System.out.println();
		System.out.println("Curso: CursoPiano");
		System.out.println("Duracao em meses: minimo 2, maximo 12");
		System.out.println("Numero de alunos: minimo 1");
		System.out.println("Professor: obrigatorio");

		System.out.println();
		System.out.println("Curso: CursoBateria");
		System.out.println("Duracao em meses: minimo 2, maximo 12");
		System.out.println("Numero de alunos: minimo 1");
		System.out.println("Professor: obrigatorio");
	}

	private static void pesquisarCursos(Scanner scanner, GestaoCurso gestaoCurso) {
		Map<Integer, Curso> cursos = gestaoCurso.listarCursos();
		if (cursos.isEmpty()) {
			System.out.println("Nenhum curso cadastrado.");
			return;
		}

		System.out.println("\nTitulos disponiveis:");
		for (Map.Entry<Integer, Curso> entry : cursos.entrySet()) {
			System.out.println("ID: " + entry.getKey() + " | Titulo: " + entry.getValue().getNome());
		}

		String termo = lerTextoComPrompt(scanner, "Digite o termo para pesquisa (nome do curso ou professor): ");
		List<Integer> idsEncontrados = gestaoCurso.pesquisarPorTermo(termo);

		if (idsEncontrados.isEmpty()) {
			System.out.println("Nenhum curso encontrado para o termo informado.");
			return;
		}

		System.out.println("\n=== RESULTADO DA PESQUISA ===");
		for (Integer id : idsEncontrados) {
			Curso curso = gestaoCurso.buscarPorId(id);
			if (curso != null) {
				imprimirCurso(id, curso);
			}
		}
	}

	private static void atualizarCurso(Scanner scanner, GestaoCurso gestaoCurso) {
		int id = lerInteiroComPrompt(scanner, "Informe o ID do curso para atualizar: ");
		if (gestaoCurso.buscarPorId(id) == null) {
			System.out.println("Curso nao encontrado para o ID informado.");
			return;
		}

		try {
			String nomeCurso = lerNomeCurso(scanner);
			int duracaoMeses = lerInteiroComPrompt(scanner, "Digite a nova duracao em meses: ");
			int numeroAlunos = lerInteiroComPrompt(scanner, "Digite o novo numero de alunos: ");
			String nomeProfessor = lerTextoComPrompt(scanner, "Digite o novo nome do professor: ");

			boolean atualizado = gestaoCurso.atualizarCurso(id, nomeCurso, duracaoMeses, numeroAlunos, nomeProfessor);
			if (atualizado) {
				System.out.println("Curso atualizado com sucesso.");
			} else {
				System.out.println("Nao foi possivel atualizar o curso.");
			}
		} catch (Exception e) {
			System.out.println("Erro ao atualizar curso: " + e.getMessage());
		}
	}

	private static void removerCurso(Scanner scanner, GestaoCurso gestaoCurso) {
		int id = lerInteiroComPrompt(scanner, "Informe o ID do curso para remover: ");
		boolean removido = gestaoCurso.removerCurso(id);
		if (removido) {
			System.out.println("Curso removido com sucesso.");
		} else {
			System.out.println("Curso nao encontrado para o ID informado.");
		}
	}

	private static void executarCurso(Scanner scanner, GestaoCurso gestaoCurso) {
		int id = lerInteiroComPrompt(scanner, "Informe o ID do curso para executar: ");
		Curso curso = gestaoCurso.buscarPorId(id);

		if (curso == null) {
			System.out.println("Curso nao encontrado para o ID informado.");
			return;
		}

		System.out.print("Deseja realizar a avaliacao final? (s/n): ");
		String respostaAvaliacao = scanner.nextLine().trim();
		if (respostaAvaliacao.equalsIgnoreCase("s")) {
			int mesesConcluidos = lerInteiroComPrompt(scanner, "Quantos meses concluidos? ");
			double percentual = 0.0;
			if (curso.getDuracao_Meses() > 0) {
				percentual = (double) mesesConcluidos / curso.getDuracao_Meses();
			}
			curso.setPercentualConcluido(percentual);

			if (percentual < 0.75) {
				System.out.println("Reprovado: o curso não completou 75% da carga.");
				return;
			}

			Avaliacao avaliacao = curso.provafinal();
			if (avaliacao instanceof AvaliacaoNaoElegivel) {
				avaliacao.avaliacao();
				return;
			} else {
				avaliacao.avaliacao();
			}
		}

		curso.iniciarCurso();
		System.out.println("curso finalizado: " + curso.getNome());
	}

	private static void imprimirCurso(int id, Curso curso) {
		System.out.println("ID: " + id +
				" | Tipo: " + curso.getNome() +
				" | Duracao: " + curso.getDuracao_Meses() + " meses" +
				" | Alunos: " + curso.getNumero_Alunos() +
				" | Professor: " + curso.getProfessor());
	}

	private static int lerInteiro(Scanner scanner) {
		while (true) {
			String entrada = scanner.nextLine().trim();
			try {
				return Integer.parseInt(entrada);
			} catch (NumberFormatException e) {
				System.out.print("Digite um numero valido: ");
			}
		}
	}

	private static int lerInteiroComPrompt(Scanner scanner, String mensagem) {
		System.out.print(mensagem);
		return lerInteiro(scanner);
	}

	private static String lerTextoComPrompt(Scanner scanner, String mensagem) {
		String texto;
		do {
			System.out.print(mensagem);
			texto = scanner.nextLine().trim();
		} while (texto.isEmpty());
		return texto;
	}

	private static String lerNomeCurso(Scanner scanner) {
		while (true) {
			System.out.println("Escolha o curso:");
			System.out.println("1 - CursoGuitarra");
			System.out.println("2 - CursoPiano");
			System.out.println("3 - CursoBateria");
			System.out.print("Opcao: ");

			int escolha = lerInteiro(scanner);

			if (escolha == 1) {
				return "CursoGuitarra";
			} else if (escolha == 2) {
				return "CursoPiano";
			} else if (escolha == 3) {
				return "CursoBateria";
			}

			System.out.println("Curso invalido. Escolha novamente.");
		}
	}

}
