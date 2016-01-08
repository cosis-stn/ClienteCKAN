package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class StringResponseList extends Response {
	private List<String> result;

	public List<String> getResult() {
		return result;
	}

	public void setResult(List<String> result) {
		this.result = result;
	}
}
