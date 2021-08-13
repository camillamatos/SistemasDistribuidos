package ifc.sd.trabalhofinal.model;

public class Acao {
	private int id;
	private String operacao;
	private int conta;
	private double valor;

	public Acao(int id, String operacao, int conta, double valor) {
		super();
		this.id = id;
		this.operacao = operacao;
		this.conta = conta;
		this.valor = valor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public int getConta() {
		return conta;
	}

	public void setConta(int conta) {
		this.conta = conta;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
