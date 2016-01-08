package br.gov.tesouro.fazenda.modelo;


public class MemberListResponse extends Response {
	private MemberList result;

	public MemberList getResult() {
		return result;
	}

	public void setResult(MemberList result) {
		this.result = result;
	}
}
