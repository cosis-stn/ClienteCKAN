package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class RoleShow {
	private String domain_object_id;
	private String domain_object_type;
	private String user;
	private List<Role> roles;

	public String getDomain_object_id() {
		return domain_object_id;
	}

	public void setDomain_object_id(String domain_object_id) {
		this.domain_object_id = domain_object_id;
	}

	public String getDomain_object_type() {
		return domain_object_type;
	}

	public void setDomain_object_type(String domain_object_type) {
		this.domain_object_type = domain_object_type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
