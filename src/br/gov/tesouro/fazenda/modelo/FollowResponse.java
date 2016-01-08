package br.gov.tesouro.fazenda.modelo;



public class FollowResponse extends Response {
	private Follow result;

	public Follow getResult() {
		return result;
	}

	public void setResult(Follow result) {
		this.result = result;
	}
}