package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class RoleResponseList extends Response {
	private List<Role> result;

	public List<Role> getResult() {
		return result;
	}

	public void setResult(List<Role> result) {
		this.result = result;
	}
}
