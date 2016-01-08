package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class LicenseResponseList extends Response {
	private List<License> result;

	public List<License> getResult() {
		return result;
	}

	public void setResult(List<License> result) {
		this.result = result;
	}
}