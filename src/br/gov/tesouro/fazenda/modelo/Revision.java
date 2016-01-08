package br.gov.tesouro.fazenda.modelo;

import java.util.List;

public class Revision {
	private String id;
	private String timestamp;
	private String message;
	private String author;
	private String approved_timestamp;
	private List<Package> packages;
	private List<Group> groups;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getApproved_timestamp() {
		return approved_timestamp;
	}

	public void setApproved_timestamp(String approved_timestamp) {
		this.approved_timestamp = approved_timestamp;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
