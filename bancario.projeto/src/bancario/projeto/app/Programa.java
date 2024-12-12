package bancario.projeto.app;

import java.util.Scanner;
import bancario.projeto.persistencia.PersistenciaCliente;
import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;

public class Programa {
    public static void main(String[] args) {
        PersistenciaCliente persistencia = new PersistenciaCliente(); /*gerencia os dados e realiza operações de armazenamento 
        (como salvar e carregar de um arquivo ou banco de dados).*/
        Scanner sc = new Scanner(System.in);
        boolean sair = true; //loop do menu
        Integer opcao = 0; //armazena o numero que for escolhido como opçao pelo usuario

        while (sair) {
            System.out.println("\nDigite a opção desejada:\n"
                    + "1 - Cadastro de Cliente\n"
                    + "2 - Remover Cliente\n"
                    + "3 - Listar Clientes\n"
                    + "4 - Opções de Clientes\n"
                    + "5 - Para Sair\n");

            opcao = sc.nextInt(); //lê a opçao escolhida pelo usuario
            sc.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1: { //CADASTRO DE CLIENTE
                    String cpf;
                    String nome;
                    System.out.println("Insira seu CPF:");
                    cpf = sc.nextLine();
                    System.out.println("Insira seu nome:");
                    nome = sc.nextLine(); //le o nome do cliente
                    Cliente novoCliente = new Cliente(nome, cpf); // Cria um novo objeto Cliente
                    persistencia.adicionarCliente(novoCliente);
                    break;
                }
                case 2: { //REMOVER CLIENTE
                    String cpf;
                    System.out.println("Insira o CPF do cliente a ser removido:");
                    cpf = sc.nextLine(); //le o novo cpf  a ser removido
                    persistencia.removerCliente(cpf);
                    break;
                }
                case 3: {
                    persistencia.listarClientes();
                    break;
                }
                case 4: { // Opções de Clientes (menu secundário)
                    menuCliente(persistencia, sc); //operaçoes especificas com as clientese contas
                    break;
                }
                case 5: {
                    sair = false;
                    System.out.println("Saindo do sistema...");
                    break;
                }
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
        sc.close(); // Fecha o Scanner 
    }

    // Menu secundário para opções de clientes
    private static void menuCliente(PersistenciaCliente persistencia, Scanner sc) {
        System.out.println("Insira o CPF do cliente:");
        String cpf = sc.nextLine(); //lê o cpf
        Cliente cliente = persistencia.localizarClientePorCpf(cpf); //chama o metodo de loclaizar la em persistencia
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return; //retorna pro menu principal 
        }

        boolean voltar = false;
        while (!voltar) {
            System.out.println("\nEscolha a opção desejada:\n"
                    + "1 - Criar Conta\n"
                    + "2 - Listar Contas\n"
                    + "3 - Remover Conta\n"
                    + "4 - Realizar Depósito\n"
                    + "5 - Realizar Saque\n"
                    + "6 - Transferir entre Contas\n"
                    + "7 - Consultar Saldo\n"
                    + "8 - Consultar Balanço Total\n"
                    + "9 - Voltar\n");

            int opcao = sc.nextInt();
            sc.nextLine(); 

            switch (opcao) { //CRIAR UMA NOVA CONTA PRO SEU CLIENTE
                case 1: {
                    System.out.println("Digite o número da conta:");
                    Integer numero = sc.nextInt();
                    sc.nextLine(); // Consome a quebra de linha
                    ContaBancaria conta = new ContaBancaria(numero);
                    cliente.adicionarConta(conta);
                    persistencia.atualizarCliente(cliente); // // Atualiza o cliente na persistência
                    break;
                }
                case 2: {
                    persistencia.listarContasDoCliente(cpf); //LISTA AS CONTAS ASSOCIADAS AO CLIENTE
                    break;
                }
                case 3: { //REMOVER UMA  CONTA DO CLIENTE
                    System.out.println("Digite o número da conta a ser removida:");
                    Integer numeroConta = sc.nextInt();
                    sc.nextLine(); // Consome a quebra de linha
                    ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
                    if (conta != null) {// SE A CONTA EXISTIR É REMOVIDA
                        cliente.removerConta(conta);
                        persistencia.atualizarCliente(cliente);
                    } else {
                        System.out.println("Conta não encontrada."); 
                    }
                    break;
                }
                case 4: { //REALIZAR UM DEPOSITO
                    System.out.println("Digite o número da conta para realizar o depósito:");
                    for (ContaBancaria conta : cliente.getContas()) { //PERCORRE E Exibe as contas do cliente
                        System.out.println("Conta número: " + conta.getNumeroConta() + " | Saldo atual: R$ " + conta.getSaldo());
                    }

                    Integer numeroConta = sc.nextInt();
                    sc.nextLine();

                    ContaBancaria contaEscolhida = cliente.localizarContaPorNumero(numeroConta);
                    if (contaEscolhida != null) {
                        System.out.println("Digite o valor a ser depositado:");
                        float quantia = sc.nextFloat();
                        sc.nextLine(); 
                        contaEscolhida.depositar(quantia);// Realiza o depósito na conta
                        persistencia.atualizarCliente(cliente); // Atualiza o cliente na persistência
                        System.out.println("Depósito realizado com sucesso na conta número " + numeroConta);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;
                }
                case 5: { //REALIZAR SAQUE 
                    System.out.println("Digite o valor do saque:");
                    float quantia = sc.nextFloat();// // Lê o valor a ser sacado
                    sc.nextLine();
                    if (!cliente.getContas().isEmpty()) { // Verifica se o cliente possui contas associadas.O método getContas() retorna a lista de contas do cliente. Se a lista não estiver vazia, o código do if será executado.
                        ContaBancaria conta = cliente.getContas().get(0); // Considera a primeira conta DO CLIENTE
                        conta.sacar(quantia); //realiza o saque pelo metodo sacar de contabancaria
                        persistencia.atualizarCliente(cliente); //Atualiza o cliente na persistência
                    } else {
                        System.out.println("O cliente não possui contas.");
                    }
                    break;
                }
                case 6: { //transfeirir entre contas
                    System.out.println("Digite o número da conta de destino:");
                    Integer numeroDestino = sc.nextInt();
                    sc.nextLine();
                    ContaBancaria contaDestino = cliente.localizarContaPorNumero(numeroDestino);
                    if (contaDestino != null) {
                        System.out.println("Digite o valor a ser transferido:");
                        float quantia = sc.nextFloat();
                        sc.nextLine(); 
                        if (!cliente.getContas().isEmpty()) {
                        	//isempty verifica se a lista está vazia e cliente.getcontas retorna a lista de contas bancarias associadas ao cliente
                            ContaBancaria contaOrigem = cliente.getContas().get(0); // Considera a primeira conta do cliente
                            contaOrigem.transferir(contaDestino, quantia); // realiza a trasnferencia aqui
                            persistencia.atualizarCliente(cliente);
                        }
                    } else {
                        System.out.println("Conta de destino não encontrada.");
                    }
                    break;
                }
                case 7: { // Consultar saldo de uma conta do cliente
                    if (!cliente.getContas().isEmpty()) {
                    	////isempty verifica se a lista está vazia e cliente.getcontas retorna a lista de contas bancarias associadas ao client
                        ContaBancaria conta = cliente.getContas().get(0); // Considera a primeira conta DO CLIENTE
                        cliente.consultarSaldo(conta);
                    } else {
                        System.out.println("O cliente não possui contas.");
                    }
                    break;
                }
                case 8: {// Consultar balanço total (soma de todos os saldos das contas do cliente)
                    cliente.balancoEntreContas();
                    break;
                }
                case 9: {
                    voltar = true;
                    break;
                }
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
