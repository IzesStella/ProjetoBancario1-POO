package bancario.projeto.model;

import java.io.Serializable;
import java.util.Objects;

/*A classe ContaBancaria é responsável por todas as operações relacionadas a uma conta bancária, 
 como depósito, saque, transferência e consulta de saldo. */
public class ContaBancaria  implements Serializable{ /*converte objetos em sequencia de bytes a fim de transferir ou salvar em 
arquivo de persistencia*/

	private static final long serialVersionUID = 1L; //garante que a vers. serializada seja compativel
	private Integer numeroConta; 
	private float saldo;
	private String dataAbertura;
	private boolean status;
//usei private pq  impedi que essas variáveis sejam acessadas ou modificadas diretamente de fora da classe
	
	public ContaBancaria() {
		
	}
	
	public ContaBancaria(Integer numero) { 
		this.numeroConta = numero;//Inicializa o número da conta com o valor fornecido.
		this.saldo = 0f; // a conta inicializa com saldo 0
		this.dataAbertura = dataAbertura;
		this.status = true; 
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(numeroConta); /*basicamente como se fosse um "id". Se duas contas com o 
		 mesmo numeroConta produzirão o mesmo código hash. Contas com numeroConta diferentes terão, em geral, 
		 códigos hash diferentes.*/
	}

	@Override
	public boolean equals(Object obj) { /*Este método determina se dois objetos da classe ContaBancaria são considerados iguais.
		A igualdade entre as contas é definida pelo atributo numeroConta.*/
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaBancaria other = (ContaBancaria) obj;
		return Objects.equals(numeroConta, other.numeroConta);
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public String getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	

	@Override
	public String toString() {
		return "ContaBancaria [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
				+ ", status=" + status + "]";
	}

	public void depositar(float quantia) { 
		if (status) {
			if (quantia > 0) {
				this.saldo += quantia;
				System.out.println("Deposito realizado com sucesso.");
			} else {
				System.err.println("Valor invalido para depósito.");
			}
		} else {
			System.err.println("Operação não permitida. Conta desativada.");
		}
	}

	public void sacar(float quantia) {
		if (status) {
			if (quantia > 0) {
				if (this.saldo >= quantia) {  // Verifica se há saldo suficiente na conta para o saque.
					this.saldo -= quantia; //decrementa
					System.out.println("Saque realizado com sucesso!");
				} else {
					System.err.println("Saldo insuficiente.");
				}
			} else {
				System.err.println("Valor invalido para saque.");
			}
		} else {
			System.err.println("Operaçao não permitida. Conta desativada.");
		}

	}

	public void transferir(ContaBancaria cDeDestino, float quantia) {
		if (status && cDeDestino.isStatus()) { //verifica se 2 +++++++++++++as contas tao ativas
			if (quantia <= 0) {
				System.err.println("Valor invalido para transferencia.");
			} else if (quantia <= saldo) {
				this.saldo -= quantia; //decrementa o valor do seu saldo (subtraindo pela quantia q vc transferiu
				cDeDestino.saldo += quantia; //incrementa na conta o valor q foi transferido	
				System.out.println("Transferência realizada com sucesso.");
			} else
				System.err.println("Saldo insuficiente para realizar a transferencia.");
		} else {
			System.err.println("Operacao nao pode ser realizada entre contas desativadas.");
		}

	}


}


