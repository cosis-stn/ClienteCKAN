package br.gov.tesouro.fazenda.modelo;


public class RevisionResponse extends Response {
	private Revision result;

	public Revision getResult() {
		return result;
	}

	public void setResult(Revision result) {
		this.result = result;
	}
}
