package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class ResourceResponseList extends Response {
	private List<Resource> result;

	public List<Resource> getResult() {
		return result;
	}

	public void setResult(List<Resource> result) {
		this.result = result;
	}
}