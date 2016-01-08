package br.gov.tesouro.fazenda.modelo;

public class RatingResponse extends Response {
	private Rating result;

	public Rating getResult() {
		return result;
	}

	public void setResult(Rating result) {
		this.result = result;
	}
}
