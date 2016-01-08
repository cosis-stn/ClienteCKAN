package br.gov.tesouro.fazenda.modelo;

public class TermTranslationResponse extends Response {
	private TermTranslation result;

	public TermTranslation getResult() {
		return result;
	}

	public void setResult(TermTranslation result) {
		this.result = result;
	}
}