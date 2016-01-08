package br.gov.tesouro.fazenda.modelo;

public class MembershipResponse extends Response {
	private Membership result;

	public Membership getResult() {
		return result;
	}

	public void setResult(Membership result) {
		this.result = result;
	}
}
