package br.gov.tesouro.fazenda.modelo;

public class VocabularyResponse extends Response {
	private Vocabulary result;

	public Vocabulary getResult() {
		return result;
	}

	public void setResult(Vocabulary result) {
		this.result = result;
	}
}