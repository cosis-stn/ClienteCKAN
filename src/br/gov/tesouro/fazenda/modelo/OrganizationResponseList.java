package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class OrganizationResponseList extends Response {
	private List<Organization> result;

	public List<Organization> getResult() {
		return result;
	}

	public void setResult(List<Organization> result) {
		this.result = result;
	}
}
