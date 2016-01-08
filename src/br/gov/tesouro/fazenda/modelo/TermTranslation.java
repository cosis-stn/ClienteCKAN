package br.gov.tesouro.fazenda.modelo;

public class TermTranslation {
	private String term;
	private String term_translation;
	private String lang_code;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTerm_translation() {
		return term_translation;
	}

	public void setTerm_translation(String term_translation) {
		this.term_translation = term_translation;
	}

	public String getLang_code() {
		return lang_code;
	}

	public void setLang_code(String lang_code) {
		this.lang_code = lang_code;
	}

}
