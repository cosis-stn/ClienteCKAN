package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class RevisionResponseList extends Response {
	private List<Revision> result;

	public List<Revision> getResult() {
		return result;
	}

	public void setResult(List<Revision> result) {
		this.result = result;
	}
}
