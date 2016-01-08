package br.gov.tesouro.fazenda.modelo;

public class User {
	private String openid;
	private String about;
	private String display_name;
	private String name;
	private String created;
	private String email_hash;
	private Boolean sysadmin;
	private Boolean activity_streams_email_notifications;
	private String state;
	private Integer number_of_edits;
	private String fullname;
	private String id;
	private Integer number_created_packages;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getEmail_hash() {
		return email_hash;
	}
	public void setEmail_hash(String email_hash) {
		this.email_hash = email_hash;
	}
	public Boolean getSysadmin() {
		return sysadmin;
	}
	public void setSysadmin(Boolean sysadmin) {
		this.sysadmin = sysadmin;
	}
	public Boolean getActivity_streams_email_notifications() {
		return activity_streams_email_notifications;
	}
	public void setActivity_streams_email_notifications(Boolean activity_streams_email_notifications) {
		this.activity_streams_email_notifications = activity_streams_email_notifications;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getNumber_of_edits() {
		return number_of_edits;
	}
	public void setNumber_of_edits(Integer number_of_edits) {
		this.number_of_edits = number_of_edits;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getNumber_created_packages() {
		return number_created_packages;
	}
	public void setNumber_created_packages(Integer number_created_packages) {
		this.number_created_packages = number_created_packages;
	}
	
	
}
