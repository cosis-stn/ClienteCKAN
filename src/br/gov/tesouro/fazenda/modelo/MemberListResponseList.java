package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class MemberListResponseList extends Response {
	private List<MemberList> result;

	public List<MemberList> getResult() {
		return result;
	}

	public void setResult(List<MemberList> result) {
		this.result = result;
	}
}

