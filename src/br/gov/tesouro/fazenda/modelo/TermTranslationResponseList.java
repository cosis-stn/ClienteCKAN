package br.gov.tesouro.fazenda.modelo;

import java.util.List;


public class TermTranslationResponseList  extends Response {
	private List<TermTranslation> result;

	public List<TermTranslation> getResult() {
		return result;
	}

	public void setResult(List<TermTranslation> result) {
		this.result = result;
	}
}
