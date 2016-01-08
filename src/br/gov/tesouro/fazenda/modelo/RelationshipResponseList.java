package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class RelationshipResponseList extends Response {
	private List<Relationship> result;

	public List<Relationship> getResult() {
		return result;
	}

	public void setResult(List<Relationship> result) {
		this.result = result;
	}
}
