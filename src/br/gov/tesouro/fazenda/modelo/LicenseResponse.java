package br.gov.tesouro.fazenda.modelo;

public class LicenseResponse extends Response {
	private License result;

	public License getResult() {
		return result;
	}

	public void setResult(License result) {
		this.result = result;
	}
}