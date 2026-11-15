import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        int opcao = 0;

        while (opcao != 7) {
            menu();

            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); 
        }
    }

    public static void menu() {
        System.out.println("Menu:");
        System.out.println("1. Cadastrar Aluno        4. Listar Alunos");
        System.out.println("2. Cadastrar Notas        5. Consultar Notas");
        System.out.println("3. Cadastrar Frequência   6. Consultar Frequência");
        System.out.println("7. Sair");
    }
}
