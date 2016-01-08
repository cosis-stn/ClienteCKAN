package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class TagResponseList extends Response {
	private List<Tag> result;

	public List<Tag> getResult() {
		return result;
	}

	public void setResult(List<Tag> result) {
		this.result = result;
	}
}
