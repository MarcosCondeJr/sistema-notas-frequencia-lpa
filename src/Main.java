import java.util.Scanner;

public class Main {
   static Scanner scanner = new Scanner(System.in);

    static int[] matriculas;
    static String[] nomes;
    static double[] notas1;
    static double[] notas2;
    static double[] notas3;
    static double[] frequencias;
    static int totalAlunos = 0;
    static int totalAprovado = 0;
    static int totalReprovado = 0;
    static int capacidade;

	public static void main(String[] args) {

        /**
		 * [ALTERAÇÃO - Jade Vila Flor] Substituição da limitação fixa de 10 alunos
		 *
		 */
		do {
			System.out.print("\nInforme a capacidade máxima de alunos da turma: ");
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

		while (opcao != 8) {
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
				relatorioGeral();
			} else if (opcao == 7) {
				excluirAluno();
			} else if (opcao == 8) {
				System.out.println("================= Sistema encerrado =================");
			} else {
				System.out.println("Opção inválida.");
			}
		}
    }

    public static void menu() {
        System.out.println("\n====== SISTEMA DE NOTAS E FREQÊNCIA ACADÊMICA =====");
        System.out.println("");
        System.out.println("1. Cadastrar aluno");
        System.out.println("2. Cadastrar notas");
        System.out.println("3. Cadastrar frequência");
        System.out.println("4. Consultar boletim");
        System.out.println("5. Listar alunos cadastrados");
        System.out.println("6. Relatório geral  da turma");
        System.out.println("7. Excluir aluno");
        System.out.println("8. Sair");
        System.out.println();
        System.out.println("===================================================");
    }

	public static void cadastrarAluno() {
		if (validarTotalDeAlunosCadastrados()) {
			System.out.println("Limite do cadastro de alunos atingido");
			return;
		}

        System.out.println("\n================ CADASTRAR ALUNO ================");

		System.out.print("\nDigite a matrícula: ");
		int matricula = scanner.nextInt();

		int aluno = buscarAlunoPorMatricula(matricula);

        // [ALTERAÇÃO - Marcos Conde] Adicionando fluxo de atualização de aluno já cadastrado
		if (aluno != -1) {
			System.out.print("\nJá existe um aluno com essa matricula, deseja atualizar a matricula e o nome do aluno? (1 - Sim/ 2 - Não): ");
			int desejaAtualizar = scanner.nextInt();

			if (desejaAtualizar == 1) {
				System.out.print("Digite a matrícula: ");
				matriculas[aluno] = scanner.nextInt();
				scanner.nextLine();

				System.out.print("Digite o nome: ");
				nomes[aluno] = scanner.nextLine();

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
		System.out.print("\nDigite a matrícula: ");
		int matricula = scanner.nextInt();

		return buscarAlunoPorMatricula(matricula);
	}

	public static void cadastrarNotas() {
        System.out.println("\n================ CADASTRAR NOTAS ================");

		int aluno = procurarAluno();
        double nota1, nota2, nota3 = 0.0;
        boolean notasInvalidas = false;

		if (aluno != -1) {
            // [ALTERAÇÃO - Marcos Conde] Adicionando validação de notas inválidas
            do {
                System.out.print("\nDigite a primeira nota: ");
                nota1 = scanner.nextDouble();

                System.out.print("Digite a segunda nota: ");
                nota2 = scanner.nextDouble();

                System.out.print("Digite a terceira nota: ");
                nota3 =scanner.nextDouble();

                notasInvalidas = validarNotasInformada(nota1, nota2, nota3);
                if (notasInvalidas) {
                    System.out.println("\n Notas inválidas. A nota deve está entre 0 e 10, informe novamente!");
                }

            } while (notasInvalidas == true);

			notas1[aluno] = nota1;
			notas2[aluno] = nota2;
			notas3[aluno] = nota3;

			System.out.println("\nNotas cadastradas.");
		} else {
			System.out.println("\nAluno não encontrado.");
		}
	}

	public static void cadastrarFrequencia() {
        System.out.println("\n================ CADASTRAR FREQUÊNCIA ================");

		int aluno = procurarAluno();
        boolean frequenciaInvalida = false;
        double frequencia = 0;

		if (aluno != -1) {
            // [ALTERAÇÃO - Marcos Conde] Adicionando validação de frequência inválida
            do {
                frequenciaInvalida = false;

                System.out.print("\nDigite a frequência em porcentagem: ");
                frequencia = scanner.nextDouble();

                if (frequencia < 0 || frequencia > 100) {
                    System.out.println("\nFrequência inválida. Informe um valor entre 0 e 100");
                    frequenciaInvalida = true;
                }
            } while (frequenciaInvalida == true);

			frequencias[aluno] = frequencia;
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
	 * Consulta o boletim de um aluno ou de todos.
	 *
	 * @author Marcos Conde
	 */
	public static void consultarBoletim() {
		int mediaAprovado = 6;
		int frequenciaMinima = 75;
        boolean listarTodos = true;
        int aluno = -1;

		System.out.println("\n================ BOLETIM ACADEMICO ================");

        if (totalAlunos == 0) {
            System.out.println("\nNenhum aluno cadastrado no sistema.");
            return;
        }

        System.out.print("\nDeseja filtrar por matricula? (1 - Sim/2 - Não): ");

        if (scanner.nextInt() == 1) {
            listarTodos = false;
            aluno = procurarAluno();
        }

		for (int i = 0; i < totalAlunos; i++) {
            if (aluno != -1 && i != aluno) {
                continue;
            }

            if (aluno == -1 && !listarTodos) {
                System.out.println("\nAluno não encontrado.");
                System.out.println("\nOperação finalizada.");
                return;
            }

			double media = (notas1[i] + notas2[i] + notas3[i]) / 3;
			String mediaFormatada = String.format("%.2f", media);

			double frequencia = frequencias[i];

			String situacao = "REPROVADO";
            if (
                media >= mediaAprovado &&
                frequencia > frequenciaMinima
            ) {
                situacao = "APROVADO";
                totalAprovado = totalAprovado + 1;
            }else{
                totalReprovado = totalReprovado + 1;
            }

			System.out.println("\nMATRICULA: " + matriculas[i]);
			System.out.println("ALUNO: " + nomes[i]);
			System.out.println("MÉDIA: " + mediaFormatada);
			System.out.println("FREQUÊNCIA: " + frequencia + "%");
			System.out.println("SITUAÇÃO: " + situacao);
		}
        System.out.println("\n===================================================");
	}

	/**
	 * Listar todos os alunos cadastrados reutilizando o boletim
	 *
	 * @author Jade Vila Flor
	 */
	public static void listarAlunos() {
		System.out.println("\n============ LISTA DE ALUNOS CADASTRADOS ============");

		if (totalAlunos == 0) {
			System.out.println("\nNenhum aluno cadastrado no sistema.");
		} else {
			System.out.println("\nTotal de alunos cadastrados: " + totalAlunos + " de " + capacidade);

            // [ALTERAÇÃO - Marcos Conde] Estilo da lista de alunos
            System.out.printf("\n%-15s | %-30s%n", "MATRÍCULA", "NOME");
            System.out.println("--------------------------------------------------");

            for (int i = 0; i < totalAlunos; i++) {
                System.out.printf("%-15s | %-30s%n", matriculas[i], nomes[i]);
            }
		}
		System.out.println("--------------------------------------------------");
	}

    public static void relatorioGeral() {
        double mediaGeral = 0;

        for(int i=0; i < totalAlunos; i++) {
            mediaGeral = mediaGeral + (notas1[i] + notas2[i] + notas3[i]);
        }

        mediaGeral = mediaGeral / (totalAlunos * 3);
        String mediaFormatada = String.format("%.2f", mediaGeral);
        System.out.println("\nA media  geral da turma é " + mediaFormatada);
        System.out.println("Total de aprovados: " + totalAprovado);
        System.out.println("Total de reprovados: " + totalReprovado);
    }

    /**
     * Valida as notas informadas
     *
     * @param nota1
     * @param nota2
     * @param nota3
     *
     * @return  Retorna true se houver nota inválida
     * @author  Marcos Conde
     */
    public static boolean validarNotasInformada(double nota1, double nota2, double nota3) {
        boolean erro = false;

        if (nota1 < 0 || nota1 > 10) {
            erro = true;
        } else if (nota2 < 0 || nota2 > 10) {
            erro = true;
        } else if (nota3 < 0 || nota3 > 10) {
            erro = true;
        }

        return erro;
    }

    /**
     * Exclui um determinado aluno, e seus dados
     *
     * @author  Marcos Conde
     */
    public static void excluirAluno() {
        System.out.println("\n================ EXCLUIR ALUNO ================");

        int aluno = procurarAluno();

        if (aluno == -1) {
            System.out.println("\nAluno não encontrado.");
            return;
        }

        String nomeAluno = "";

        for (int i = 0, j = 0; i < totalAlunos; i++) {
            if (i == aluno) {
                nomeAluno = nomes[i];
                continue;
            }
            matriculas[j] = matriculas[i];
            nomes[j] = nomes[i];
            notas1[j] = notas1[i];
            notas2[j] = notas2[i];
            notas3[j] = notas3[i];
            frequencias[j] = frequencias[i];
            j++;
        }
        totalAlunos--;
        System.out.println("\nAluno " + nomeAluno + " excluido com sucesso.");
    }
}
