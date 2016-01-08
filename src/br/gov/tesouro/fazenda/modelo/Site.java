package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class Site {
	private String ckan_version;
	private String site_url;
	private String site_description;
	private String site_title;
	private String error_emails_to;
	private String locale_default;
	private List<String> extensions;

	public String getCkan_version() {
		return ckan_version;
	}

	public void setCkan_version(String ckan_version) {
		this.ckan_version = ckan_version;
	}

	public String getSite_url() {
		return site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

	public String getSite_description() {
		return site_description;
	}

	public void setSite_description(String site_description) {
		this.site_description = site_description;
	}

	public String getSite_title() {
		return site_title;
	}

	public void setSite_title(String site_title) {
		this.site_title = site_title;
	}

	public String getError_emails_to() {
		return error_emails_to;
	}

	public void setError_emails_to(String error_emails_to) {
		this.error_emails_to = error_emails_to;
	}

	public String getLocale_default() {
		return locale_default;
	}

	public void setLocale_default(String locale_default) {
		this.locale_default = locale_default;
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public void setExtensions(List<String> extensions) {
		this.extensions = extensions;
	}

}
