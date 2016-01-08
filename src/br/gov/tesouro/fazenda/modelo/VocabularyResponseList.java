package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class VocabularyResponseList  extends Response {
	private List<Vocabulary> result;

	public List<Vocabulary> getResult() {
		return result;
	}

	public void setResult(List<Vocabulary> result) {
		this.result = result;
	}
}
