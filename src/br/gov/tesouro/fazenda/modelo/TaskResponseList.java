package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class TaskResponseList extends Response {
	private List<Task> result;

	public List<Task> getResult() {
		return result;
	}

	public void setResult(List<Task> result) {
		this.result = result;
	}
}