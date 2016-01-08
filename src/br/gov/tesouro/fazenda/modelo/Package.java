package br.gov.tesouro.fazenda.modelo;

import java.util.List;

/***
 * Representa um conjunto de recursos (dataset) no repositório do CKAN
 * @author fabio.s.barbosa
 *
 */
public class Package {
	private String license_title;
	private String maintainer;
	private Boolean _private;
	private String maintainer_email;
	private Integer num_tags;
	private String id;
	private String metadata_created;
	private String metadata_modified;
	private String author;
	private String author_email;
	private String state;
	private String version;
	private String creator_user_id;
	private String type;
	
	private List<Resource> resources;
	private List<Tag> tags;
	private TrackingSummary tracking_summary;
	
	private List<Group> groups;
	private String license_id;
	//TODO: corrigir o tipo de dados 
	private List<Object> relationships_as_object;
	private Organization organization; 
	private String name;
	private Boolean isopen;
	private String url;
	private String notes;
	private String owner_org;
	private List<Extra> extras;
	private String title;
	private String revision_id;
	
	/**
	 * Retorna o tipo de licença utilizada pelo conjunto de dados
	 * @return
	 */
	public String getLicense_title() {
		return license_title;
	}
	
	/**
	 * Define o tipo de licença utilizada pelo conjunto de dados
	 * @return
	 */
	public void setLicense_title(String license_title) {
		this.license_title = license_title;
	}
	
	/**
	 * Retorna o nome da pessoa responsável por manter o conjunto de dados
	 * @return String
	 */
	public String getMaintainer() {
		return maintainer;
	}

/**
 * Define o nome da pessoa responsável por manter o conjunto de dados
 * @param maintainer Nome do mantenedor
 * 
 */
	public void setMaintainer(String maintainer) {
		this.maintainer = maintainer;
	}
	public Boolean get_private() {
		return _private;
	}
	public void set_private(Boolean _private) {
		this._private = _private;
	}
	public String getMaintainer_email() {
		return maintainer_email;
	}
	public void setMaintainer_email(String maintainer_email) {
		this.maintainer_email = maintainer_email;
	}
	public Integer getNum_tags() {
		return num_tags;
	}
	public void setNum_tags(Integer num_tags) {
		this.num_tags = num_tags;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMetadata_created() {
		return metadata_created;
	}
	public void setMetadata_created(String metadata_created) {
		this.metadata_created = metadata_created;
	}
	public String getMetadata_modified() {
		return metadata_modified;
	}
	public void setMetadata_modified(String metadata_modified) {
		this.metadata_modified = metadata_modified;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor_email() {
		return author_email;
	}
	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreator_user_id() {
		return creator_user_id;
	}
	public void setCreator_user_id(String creator_user_id) {
		this.creator_user_id = creator_user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public TrackingSummary getTracking_summary() {
		return tracking_summary;
	}
	public void setTracking_summary(TrackingSummary tracking_summary) {
		this.tracking_summary = tracking_summary;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public String getLicense_id() {
		return license_id;
	}
	public void setLicense_id(String license_id) {
		this.license_id = license_id;
	}
	public List<Object> getRelationships_as_object() {
		return relationships_as_object;
	}
	public void setRelationships_as_object(List<Object> relationships_as_object) {
		this.relationships_as_object = relationships_as_object;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getIsopen() {
		return isopen;
	}
	public void setIsopen(Boolean isopen) {
		this.isopen = isopen;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getOwner_org() {
		return owner_org;
	}
	public void setOwner_org(String owner_org) {
		this.owner_org = owner_org;
	}
	public List<Extra> getExtras() {
		return extras;
	}
	public void setExtras(List<Extra> extras) {
		this.extras = extras;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRevision_id() {
		return revision_id;
	}
	public void setRevision_id(String revision_id) {
		this.revision_id = revision_id;
	}	
	
	
	

}
