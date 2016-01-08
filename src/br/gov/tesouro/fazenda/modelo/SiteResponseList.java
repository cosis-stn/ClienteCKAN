package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class SiteResponseList extends Response {
	private List<Site> result;

	public List<Site> getResult() {
		return result;
	}

	public void setResult(List<Site> result) {
		this.result = result;
	}
}
