package br.gov.tesouro.fazenda.modelo;

public class License {
	private String status;
	private String maintainer;
	private String family;
	private String title;
	private Boolean domain_data;
	private Boolean is_okd_compliant;
	private Boolean is_generic;
	private String url;
	private Boolean is_osi_compliant;
	private Boolean domain_content;
	private Boolean domain_software;
	private String id;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMaintainer() {
		return maintainer;
	}
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getDomain_data() {
		return domain_data;
	}
	public void setDomain_data(Boolean domain_data) {
		this.domain_data = domain_data;
	}
	public Boolean getIs_okd_compliant() {
		return is_okd_compliant;
	}
	public void setIs_okd_compliant(Boolean is_okd_compliant) {
		this.is_okd_compliant = is_okd_compliant;
	}
	public Boolean getIs_generic() {
		return is_generic;
	}
	public void setIs_generic(Boolean is_generic) {
		this.is_generic = is_generic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getIs_osi_compliant() {
		return is_osi_compliant;
	}
	public void setIs_osi_compliant(Boolean is_osi_compliant) {
		this.is_osi_compliant = is_osi_compliant;
	}
	public Boolean getDomain_content() {
		return domain_content;
	}
	public void setDomain_content(Boolean domain_content) {
		this.domain_content = domain_content;
	}
	public Boolean getDomain_software() {
		return domain_software;
	}
	public void setDomain_software(Boolean domain_software) {
		this.domain_software = domain_software;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
