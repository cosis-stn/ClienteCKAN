package br.gov.tesouro.fazenda.modelo;

public class Resource {
	private String cache_last_updated;
	private String package_id;
	private String webstore_last_updated;
	private String id;
	private String size;
	private String state;
	private String hash;
	private String description;
	private String format;
	
	private TrackingSummary tracking_summary;
	private String last_modified;
	private String url_type;
	private String mimetype;
	private String cache_url;
	private String name;
	private String created;
	private String url;
	private String webstore_url;
	private String mimetype_inner;
	private Integer position;
	private String revision_id;
	private String resource_type;
	public String getCache_last_updated() {
		return cache_last_updated;
	}
	public void setCache_last_updated(String cache_last_updated) {
		this.cache_last_updated = cache_last_updated;
	}
	public String getPackage_id() {
		return package_id;
	}
	public void setPackage_id(String package_id) {
		this.package_id = package_id;
	}
	public String getWebstore_last_updated() {
		return webstore_last_updated;
	}
	public void setWebstore_last_updated(String webstore_last_updated) {
		this.webstore_last_updated = webstore_last_updated;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public TrackingSummary getTracking_summary() {
		return tracking_summary;
	}
	public void setTracking_summary(TrackingSummary tracking_summary) {
		this.tracking_summary = tracking_summary;
	}
	public String getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(String last_modified) {
		this.last_modified = last_modified;
	}
	public String getUrl_type() {
		return url_type;
	}
	public void setUrl_type(String url_type) {
		this.url_type = url_type;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public String getCache_url() {
		return cache_url;
	}
	public void setCache_url(String cache_url) {
		this.cache_url = cache_url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getWebstore_url() {
		return webstore_url;
	}
	public void setWebstore_url(String webstore_url) {
		this.webstore_url = webstore_url;
	}
	public String getMimetype_inner() {
		return mimetype_inner;
	}
	public void setMimetype_inner(String mimetype_inner) {
		this.mimetype_inner = mimetype_inner;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public String getRevision_id() {
		return revision_id;
	}
	public void setRevision_id(String revision_id) {
		this.revision_id = revision_id;
	}
	public String getResource_type() {
		return resource_type;
	}
	public void setResource_type(String resource_type) {
		this.resource_type = resource_type;
	}
}
