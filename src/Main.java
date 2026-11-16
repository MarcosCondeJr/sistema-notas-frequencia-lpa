import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static int[] matriculas = new int[10];
    static String[] nomes = new String[10];
    static double[] notas1 = new double[10];
    static double[] notas2 = new double[10];
    static double[] notas3 = new double[10];
    static double[] frequencias = new double[10];
    static int totalAlunos = 0;

    public static void main(String[] args) {
        int opcao = 0;

        while (opcao != 5) {
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
        System.out.println("5. Sair");
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
     * @return  retorna true se já tiver chegado no limite
     * @author  Marcos Conde
     */
    public static boolean validarTotalDeAlunosCadastrados() {
        if (totalAlunos == 10) {
            System.out.println(matriculas.length);
            return true;
        }
        return false;
    }

    /**
     * Busca o aluno pela matricula
     *
     * @param   matricula
     * @return  Retorna o indice do aluno se encontrar
     * @author  Marcos Conde
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
     * @author  Marcos Conde
     */
    public static void consultarBoletim() {
        int mediaAprovado = 6;

        System.out.println("\n----- BOLETIM ACADEMICO -----");

        for (int i=0; i<totalAlunos; i++) {
            double media = (notas1[i] + notas2[i] + notas3[i]) / 3;
            String mediaFormatada = String.format("%.2f", media);

            String situacao = (media >= mediaAprovado) ? "APROVADO" : "REPROVADO";

            System.out.println("\nMATRICULA: " + matriculas[i]);
            System.out.println("ALUNO: " + nomes[i]);
            System.out.println("MÉDIA: " + mediaFormatada);
            System.out.println("FREQUÊNCIA: " + frequencias[i]);
            System.out.println("SITUAÇÃO: " + situacao);
        }
    }
}
