package br.gov.tesouro.fazenda.modelo;


public class ResourceResponse extends Response {
	private Resource result;

	public Resource getResult() {
		return result;
	}

	public void setResult(Resource result) {
		this.result = result;
	}
}
