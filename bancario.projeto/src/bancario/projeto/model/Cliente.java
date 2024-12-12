package bancario.projeto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

//classe cliente foi criada pq está ligada ao sistema bancario
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L; //permite a persistencia do projeto. converte em byte pra ser salvo
    //no arquivo //esse ID verifica a compatiblidade

    private String nome;
    private String cpf;
    private ArrayList<ContaBancaria> contas; //Armazena as contas bancárias associadas ao cliente.

    public Cliente(String nome, String cpf) { //objetos
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new ArrayList<>(); //armazena as contas(objetos) nesse arraylist
    }

    public void adicionarConta(ContaBancaria c) {
        if (contas.contains(c)) { //O método contains verifica se uma conta já existe na lista (atraves do equals)
            System.out.println("Conta já cadastrada");
        } else {
            contas.add(c);
            System.out.println("Conta cadastrada com sucesso");
        }
    }

    public void removerConta(ContaBancaria c) { 
        if (contas.contains(c)) {
            contas.remove(c);
            System.out.println("Conta removida com sucesso");
        } else {
            System.out.println("Conta não localizada");
        }
    }

    public ContaBancaria localizarContaPorNumero(Integer numero) {
        ContaBancaria temp = new ContaBancaria(); //Uma instância temporária de ContaBancaria é criada para realizar a
        //busca pelo número da conta na lista. p nao precisar usar em persistencia	
        temp.setNumeroConta(numero); // Define o número da conta na conta temporária
        if(contas.contains(temp)) { //verifica no arraylist 
        	int index = contas.indexOf(temp);// Obtém o índice da conta na lista
        	temp = contas.get(index); // Recupera a conta correspondente
        	return temp;
    }
        return null;
}

    public float balancoEntreContas() {
        float vSaldo = 0; //armazenar o saldo total
        for (ContaBancaria c : contas) { //O laço for percorre todas as contas associadas ao cliente para
        	//somar seus saldos, calculando o balanço total.
            vSaldo += c.getSaldo(); //incrementa o saldo das conta
        }
        System.out.println("Balanço entre contas: R$ " + vSaldo);
        return vSaldo;
    }

    public void consultarSaldo(ContaBancaria c) {
        System.out.println("Saldo atual é de R$ " + c.getSaldo());
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ArrayList<ContaBancaria> getContas() {
        return contas;
    }

    public void setContas(ArrayList<ContaBancaria> contas) {
        this.contas = contas;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Cliente other = (Cliente) obj;
        return Objects.equals(cpf, other.cpf);
    }

    @Override
    public String toString() {
        return "Cliente [nome=" + nome + ", cpf=" + cpf + ", contas=" + contas + "]";
    }
}