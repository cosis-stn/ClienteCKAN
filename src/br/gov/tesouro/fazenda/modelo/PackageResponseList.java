package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class PackageResponseList extends Response {
	private List<Package> result;

	public List<Package> getResult() {
		return result;
	}

	public void setResult(List<Package> result) {
		this.result = result;
	}
}
