package br.gov.tesouro.fazenda.modelo;

public class TaskResponse extends Response {
	private Task result;

	public Task getResult() {
		return result;
	}

	public void setResult(Task result) {
		this.result = result;
	}
}