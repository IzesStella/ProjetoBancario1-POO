	package bancario.projeto.persistencia;
	import bancario.projeto.model.ContaBancaria;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.ObjectInputStream;
	import java.io.ObjectOutputStream;
	import java.io.Serializable;
	import java.util.ArrayList;
	import bancario.projeto.model.Cliente;
	
	public class PersistenciaCliente implements Serializable {//converte objetos em sequencia de bytes a fim de transferir ou 
	    //salvar em arquivo de persistencia
	    private static final long serialVersionUID = 1L; //compatibilidade de versoes
	
	
	    private ArrayList<Cliente> clientes; //array q armazena clientes
	
	    public PersistenciaCliente() {
	        clientes = new ArrayList<>();	
	        carregarArquivo(); // // Tenta carregar os clientes salvos no arquivo ao iniciar
	
	    }
	
	    public void adicionarCliente(Cliente c) {
	        if (clientes.contains(c)) { //
	            System.out.println("Cliente já cadastrado");
	        } else {
	            clientes.add(c);
	            System.out.println("Cliente cadastrado com sucesso");
	            salvarArquivo();// metodo para salvar a lista de clientes no arquivo após a adição
	        }
	    }
	
	    public void removerCliente(String cpf) {
	        Cliente cliente = localizarClientePorCpf(cpf); //percorre a lista dos clientes e mostra oq tem o cpf desejado
	        if (cliente != null) { //se o cliente for encontrado
	            clientes.remove(cliente);
	            System.out.println("Cliente removido com sucesso.");
	            salvarArquivo();
	        } else {
	            System.out.println("Cliente não encontrado.");
	        }
	    }
	
	    public Cliente localizarClientePorCpf(String cpf) {
	        for (Cliente cliente : clientes) { //percorre a lista de cliente
	            if (cliente.getCpf().equals(cpf)) { //compara o cpf com equals
	                return cliente;
	            }
	        }
	        return null;
	    }
	
	    public void atualizarCliente(Cliente c) {
	        int index = clientes.indexOf(c); //localiza indic do cliente no arraylist
	        if (index != -1) { //se o cliente for encontrado (ou seja, diferente de -1 no indice, pq começa de 0)
	            clientes.set(index, c); //set atualiza o C na lista
	            System.out.println("Cliente atualizado com sucesso!");
	            salvarArquivo(); //esse metodo salva a lista atualizado
	        } else {
	            System.out.println("Cliente não encontrado");
	        }
	    }
	
	    public void listarClientes() {
	        if (clientes.isEmpty()) { //ve se a lista esta vazia, verifica se o número de elementos na lista é igual a zero (clientes.size() == 0.)
	            System.out.println("Nenhum cliente cadastrado.");
	        } else {
	            for (Cliente c : clientes) {
	                System.out.println(c);
	            }
	        }
	    }
	
	    public void listarContasDoCliente(String cpf) {
	        Cliente cliente = localizarClientePorCpf(cpf);
	        if (cliente == null) {
	            System.out.println("Cliente não encontrado.");
	        } else {
	            if (cliente.getContas().isEmpty()) { //// Verifica se o cliente possui contas cadastradas
	                System.out.println("O cliente não possui contas cadastradas.");
	            } else {
	                for (ContaBancaria conta : cliente.getContas()) { //percore para descobrir e se 
	                	//se houver contas, pega cada uma delas
	                    System.out.println(conta);
	                }
	            }
	        }
	    }
	//SALVA CLIENTES NO ARQUIVO
	    private void salvarArquivo() {
	        try (FileOutputStream fos = new FileOutputStream("clientes.dat"); //o try abre o arquivo para escrever nele
	             ObjectOutputStream oos = new ObjectOutputStream(fos)) {// é usado para escrever objetos de forma serializada. , converte objetos
	        	//em sequencia de bytes
	            oos.writeObject(clientes);
	        } catch (IOException e) { //mensagem de erro caso dê falha
	            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
	        }
	    }
	
	    private void carregarArquivo() {  /* Lê o arquivo CLIENTES.DAT e carrega a lista de clientes,
	     desserializa os dados e restaura o objeto original. */
	        try (FileInputStream fis = new FileInputStream("clientes.dat");
	             ObjectInputStream ois = new ObjectInputStream(fis)) {
	            clientes = (ArrayList<Cliente>) ois.readObject(); /*Lê os bytes do arquivo e desserializa-os para o objeto original.,
	            o resultado se converte no arraylist na variavel clientes*/	
	        } catch (FileNotFoundException e) {
	            System.out.println("Arquivo não encontrado. Será criada uma nova lista de clientes.");
	            clientes = new ArrayList<>();
	        } catch (IOException | ClassNotFoundException e) {
	            System.err.println("Erro ao carregar arquivo: " + e.getMessage());
	        }
	    }
	}