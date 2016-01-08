package br.gov.tesouro.fazenda.modelo;

import com.google.gson.annotations.SerializedName;

public class Data {
	@SerializedName("package")
	private Package _package;
	private Resource resource;	
	private Group group;

	public Package get_package() {
		return _package;
	}

	public void set_package(Package _package) {
		this._package = _package;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
