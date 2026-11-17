import java.util.Scanner;

public class Main {
   static Scanner scanner = new Scanner(System.in);

	// [ALTERAÇÃO - Jade Vila Flor] Substituído o tamanho fixo [10] por arrays dinâmicos baseados na variável 'capacidade'
	static int capacidade;
	static int[] matriculas;
	static String[] nomes;
	static double[] notas1;
	static double[] notas2;
	static double[] notas3;
	static double[] frequencias;
	static int totalAlunos = 0;

	public static void main(String[] args) {

		/**
		 * [ALTERAÇÃO - Jade Vila Flor] Substituição da limitação fixa de 10 alunos
		 * 
		 */
		do {
			System.out.print("Informe a capacidade máxima de alunos da turma: ");
			capacidade = scanner.nextInt();

			if (capacidade <= 0) {
				System.out.println("Valor inválido! Digite um número maior que zero.\n");
			}
		} while (capacidade <= 0);

		// Inicializa os arrays com a capacidade informada pelo usuário
		matriculas = new int[capacidade];
		nomes = new String[capacidade];
		notas1 = new double[capacidade];
		notas2 = new double[capacidade];
		notas3 = new double[capacidade];
		frequencias = new double[capacidade];

		int opcao = 0;

		// [ALTERAÇÃO - Jade Vila Flor] Condição atualizada para 6 por conta da nova opção no menu
		while (opcao != 6) {
			menu();
			System.out.print("Escolha uma opção: ");
			opcao = scanner.nextInt();
			scanner.nextLine();

			if (opcao == 1) {
				cadastrarAluno();
			} else if (opcao == 2) {
				cadastrarNotas();
			} else if (opcao == 3) {
				cadastrarFrequencia();
			} else if (opcao == 4) {
				consultarBoletim();
			} else if (opcao == 5) {
				listarAlunos();
			} else if (opcao == 6) {
				System.out.println("Sistema encerrado.");
			} else {
				System.out.println("Opção inválida.");
			}
		}
	}

	public static void menu() {
		System.out.println("\n1. Cadastrar Aluno");
		System.out.println("2. Cadastrar Notas");
		System.out.println("3. Cadastrar Frequência");
		System.out.println("4. Consultar boletim");
		System.out.println("5. Listar alunos cadastrados");
		System.out.println("6. Sair");
	}

	public static void cadastrarAluno() {
		if (validarTotalDeAlunosCadastrados()) {
			System.out.println("Limite de cadastro de alunos atingido");
			return;
		}

		System.out.print("Digite a matrícula: ");
		int matricula = scanner.nextInt();

		int aluno = buscarAlunoPorMatricula(matricula);

		if (aluno != -1) {
			System.out.println("\nJá existe um aluno com essa matricula, deseja atualizar a matricula e o nome do aluno? (sim/nao): ");
			String desejaAtualizar = scanner.next();

			if (desejaAtualizar.equalsIgnoreCase("sim")) {
				System.out.print("Digite a matrícula: ");
				matriculas[totalAlunos] = scanner.nextInt();
				scanner.nextLine();

				System.out.print("Digite o nome: ");
				nomes[totalAlunos] = scanner.nextLine();

				System.out.println("\nAluno atualizado.");
				return;
			}
			System.out.println("\nOperação finalizada.");
			return;
		}

		matriculas[totalAlunos] = matricula;
		scanner.nextLine();

		System.out.print("Digite o nome: ");
		nomes[totalAlunos] = scanner.nextLine();

		totalAlunos++;
		System.out.println("\nAluno cadastrado.");
	}

	public static int procurarAluno() {
		System.out.print("Digite a matrícula: ");
		int matricula = scanner.nextInt();

		return buscarAlunoPorMatricula(matricula);
	}

	public static void cadastrarNotas() {
		int aluno = procurarAluno();

		if (aluno != -1) {
			System.out.print("Digite a primeira nota: ");
			notas1[aluno] = scanner.nextDouble();
			System.out.print("Digite a segunda nota: ");
			notas2[aluno] = scanner.nextDouble();
			System.out.print("Digite a terceira nota: ");
			notas3[aluno] = scanner.nextDouble();
			System.out.println("\nNotas cadastradas.");
		} else {
			System.out.println("\nAluno não encontrado.");
		}
	}

	public static void cadastrarFrequencia() {
		int aluno = procurarAluno();

		if (aluno != -1) {
			System.out.print("Digite a frequência em porcentagem: ");
			frequencias[aluno] = scanner.nextDouble();
			System.out.println("\nFrequência cadastrada.");
		} else {
			System.out.println("\nAluno não encontrado.");
		}
	}

	/**
	 * Valida o total de alunos cadastrados
	 *
	 * @return retorna true se já tiver chegado no limite
	 * @author Marcos Conde (Alterado por Jade Vila Flor para usar 'capacidade')
	 */
	public static boolean validarTotalDeAlunosCadastrados() {
		// [ALTERAÇÃO - Jade Vila Flor] Substituído o valor fixo '10' pela variável 'capacidade'
		if (totalAlunos == capacidade) {
			System.out.println("Capacidade máxima atingida: " + capacidade);
			return true;
		}
		return false;
	}

	/**
	 * Busca o aluno pela matricula
	 *
	 * @param matricula
	 * @return Retorna o indice do aluno se encontrar
	 * @author Marcos Conde
	 */
	public static int buscarAlunoPorMatricula(int matricula) {
		for (int i = 0; i < totalAlunos; i++) {
			if (matriculas[i] == matricula) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Consultar boletins
	 *
	 * @author Marcos Conde
	 */
	public static void consultarBoletim() {
		int mediaAprovado = 6;
		int frequenciaMinima = 75;

		System.out.println("\n----- BOLETIM ACADEMICO -----");

		for (int i = 0; i < totalAlunos; i++) {
			double media = (notas1[i] + notas2[i] + notas3[i]) / 3;
			String mediaFormatada = String.format("%.2f", media);

			double frequencia = frequencias[i];

			String situacao = "REPROVADO";
			if (media >= mediaAprovado && frequencia > frequenciaMinima) {
				situacao = "APROVADO";
			}

			System.out.println("\nMATRICULA: " + matriculas[i]);
			System.out.println("ALUNO: " + nomes[i]);
			System.out.println("MÉDIA: " + mediaFormatada);
			System.out.println("FREQUÊNCIA: " + frequencia + "%");
			System.out.println("SITUAÇÃO: " + situacao);
			
			// [ALTERAÇÃO - Jade Vila Flor] Condicional simples (if) para alerta de baixa frequência
			if (frequencia < frequenciaMinima) {
							System.out.print(" [ALERTA: Frequência abaixo de 75%]");
			}
			System.out.println();

			System.out.println("SITUAÇÃO: " + situacao);
		}
	}
	/**
	 * Listar todos os alunos cadastrados reutilizando o boletim 
	 *
	 * @author Jade Vila Flor
	 */
	public static void listarAlunos() {
		System.out.println("\n=== LISTA DE ALUNOS CADASTRADOS =========================================================");

		if (totalAlunos == 0) {
			System.out.println("Nenhum aluno cadastrado no sistema.");
		} else {
			System.out.println("Total de alunos cadastrados: " + totalAlunos + " de " + capacidade);
			consultarBoletim();
		}
		System.out.println("===================================\n");
	}
}
