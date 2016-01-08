package br.gov.tesouro.fazenda.modelo;



public class RelationshipResponse extends Response {
	private Relationship result;

	public Relationship getResult() {
		return result;
	}

	public void setResult(Relationship result) {
		this.result = result;
	}
}