package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class UserResponseList  extends Response {
	private List<User> result;

	public List<User> getResult() {
		return result;
	}

	public void setResult(List<User> result) {
		this.result = result;
	}
}


