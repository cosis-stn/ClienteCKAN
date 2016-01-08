package br.gov.tesouro.fazenda.modelo;


public class UserResponse extends Response {
	private User result;

	public User getResult() {
		return result;
	}

	public void setResult(User result) {
		this.result = result;
	}
}