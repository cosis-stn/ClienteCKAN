package br.gov.tesouro.fazenda.modelo;

public abstract class Response {
	private Boolean success;
	private String help;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}

}
