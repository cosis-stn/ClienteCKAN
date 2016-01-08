package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class ActivityResponseList  extends Response {
	private List<Activity> result;

	public List<Activity> getResult() {
		return result;
	}

	public void setResult(List<Activity> result) {
		this.result = result;
	}
}
