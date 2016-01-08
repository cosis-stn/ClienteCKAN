package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class RelatedResponseList extends Response {
	private List<Related> result;

	public List<Related> getResult() {
		return result;
	}

	public void setResult(List<Related> result) {
		this.result = result;
	}
}
