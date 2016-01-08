package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class RoleShowResponseList extends Response {
	private List<RoleShow> result;

	public List<RoleShow> getResult() {
		return result;
	}

	public void setResult(List<RoleShow> result) {
		this.result = result;
	}
}