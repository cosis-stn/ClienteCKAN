package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class GroupResponseList extends Response {
	private List<Group> result;

	public List<Group> getResult() {
		return result;
	}

	public void setResult(List<Group> result) {
		this.result = result;
	}
}
