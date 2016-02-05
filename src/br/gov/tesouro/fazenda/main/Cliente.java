package br.gov.tesouro.fazenda.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.tesouro.fazenda.excecao.CKANException;
import br.gov.tesouro.fazenda.modelo.Activity;
import br.gov.tesouro.fazenda.modelo.ActivityResponse;
import br.gov.tesouro.fazenda.modelo.ActivityResponseList;
import br.gov.tesouro.fazenda.modelo.BooleanResponse;
import br.gov.tesouro.fazenda.modelo.Follow;
import br.gov.tesouro.fazenda.modelo.FollowResponse;
import br.gov.tesouro.fazenda.modelo.Group;
import br.gov.tesouro.fazenda.modelo.GroupResponse;
import br.gov.tesouro.fazenda.modelo.GroupResponseList;
import br.gov.tesouro.fazenda.modelo.IntegerResponse;
import br.gov.tesouro.fazenda.modelo.License;
import br.gov.tesouro.fazenda.modelo.LicenseResponseList;
import br.gov.tesouro.fazenda.modelo.MemberList;
import br.gov.tesouro.fazenda.modelo.MemberListResponse;
import br.gov.tesouro.fazenda.modelo.MemberListResponseList;
import br.gov.tesouro.fazenda.modelo.Membership;
import br.gov.tesouro.fazenda.modelo.MembershipResponse;
import br.gov.tesouro.fazenda.modelo.Organization;
import br.gov.tesouro.fazenda.modelo.OrganizationResponse;
import br.gov.tesouro.fazenda.modelo.OrganizationResponseList;
import br.gov.tesouro.fazenda.modelo.Package;
import br.gov.tesouro.fazenda.modelo.PackageResponse;
import br.gov.tesouro.fazenda.modelo.PackageResponseList;
import br.gov.tesouro.fazenda.modelo.Rating;
import br.gov.tesouro.fazenda.modelo.RatingResponse;
import br.gov.tesouro.fazenda.modelo.Related;
import br.gov.tesouro.fazenda.modelo.RelatedResponse;
import br.gov.tesouro.fazenda.modelo.RelatedResponseList;
import br.gov.tesouro.fazenda.modelo.Relationship;
import br.gov.tesouro.fazenda.modelo.RelationshipResponse;
import br.gov.tesouro.fazenda.modelo.RelationshipResponseList;
import br.gov.tesouro.fazenda.modelo.Resource;
import br.gov.tesouro.fazenda.modelo.ResourceResponse;
import br.gov.tesouro.fazenda.modelo.ResourceResponseList;
import br.gov.tesouro.fazenda.modelo.Revision;
import br.gov.tesouro.fazenda.modelo.RevisionResponse;
import br.gov.tesouro.fazenda.modelo.Role;
import br.gov.tesouro.fazenda.modelo.RoleResponseList;
import br.gov.tesouro.fazenda.modelo.RoleShow;
import br.gov.tesouro.fazenda.modelo.RoleShowResponse;
import br.gov.tesouro.fazenda.modelo.RoleShowResponseList;
import br.gov.tesouro.fazenda.modelo.Site;
import br.gov.tesouro.fazenda.modelo.SiteResponse;
import br.gov.tesouro.fazenda.modelo.StringResponse;
import br.gov.tesouro.fazenda.modelo.StringResponseList;
import br.gov.tesouro.fazenda.modelo.Tag;
import br.gov.tesouro.fazenda.modelo.TagResponse;
import br.gov.tesouro.fazenda.modelo.TagResponseList;
import br.gov.tesouro.fazenda.modelo.Task;
import br.gov.tesouro.fazenda.modelo.TaskResponse;
import br.gov.tesouro.fazenda.modelo.TaskResponseList;
import br.gov.tesouro.fazenda.modelo.TermTranslation;
import br.gov.tesouro.fazenda.modelo.TermTranslationResponse;
import br.gov.tesouro.fazenda.modelo.TermTranslationResponseList;
import br.gov.tesouro.fazenda.modelo.User;
import br.gov.tesouro.fazenda.modelo.UserResponse;
import br.gov.tesouro.fazenda.modelo.UserResponseList;
import br.gov.tesouro.fazenda.modelo.Vocabulary;
import br.gov.tesouro.fazenda.modelo.VocabularyResponse;
import br.gov.tesouro.fazenda.modelo.VocabularyResponseList;

import com.google.gson.Gson;

public class Cliente {

	private Conexao conexao = null;
	
	/***
	 * Construtor do cliente.
	 * 
	 * @param 	host		Nome do host
	 * @param	porta		N�mero da porta
	 * @param	apiKey		API Key
	 * @param	diretorio	Nome do diret�rio
	 */
	public Cliente(String host, Integer porta, String apiKey, String diretorio) {
		conexao = new Conexao(host, porta, apiKey, diretorio);
	}

	private <T> T loadClass(Class<T> cls, String data) {
		Gson gson = new Gson();
		System.out.println("Resposta JSON:");
		System.out.println(data);
		
		return gson.fromJson(data, cls);
	}

	private void controlarErro(String json, String action) throws CKANException {
		CKANException exception = new CKANException("Ocorreram erros durante a execu��o: " + action);
		HashMap<String, Object> hm = loadClass(HashMap.class, json);
		Map<String, Object> m = (Map<String, Object>) hm.get("error");
		
		for (Map.Entry<String, Object> entry : m.entrySet()) {
			if (entry.getKey().startsWith("_"))
				continue;
			
			exception.addError(entry.getValue() + " - " + entry.getKey());
		}
		
		// System.out.println(json);
		throw exception;
	}

	/***
	 * Retorna uma lista com todos os datasets e seus respectivos recursos.
	 * 
	 * @return Lista de datasets (tipo Package)
	 * @throws CKANException
	 */
	public List<Package> currentPackageListWithResources() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/current_package_list_with_resources", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "currentPackageListWithResources");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista paginada de datasets e seus respectivos recursos.
	 * 
	 * @param 	limit	Quantidade de registros a ser retornada (tamanho da p�gina)
	 * @param 	offset	Posi��o a partir do qual os registros ser�o retornados
	 * @param 	page	P�gina a ser retornada
	 * @return			Lista de datasets (tipo Package)
	 * @throws			CKANException
	 */
	public List<Package> currentPackageListWithResources(Integer limit, Integer offset, Integer page) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("limit", limit.toString());
		parametros.put("offset", offset.toString());
		parametros.put("page", page.toString());
		
		String resposta = this.conexao.Post("/api/action/current_package_list_with_resources", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "currentPackageListWithResources");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista com todos os datasets dispon�veis.
	 * 
	 * @return Lista de datasets
	 * @throws CKANException
	 */
	public List<String> packageList() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/package_list", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageList");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista de Ids com as revis�es do site.
	 * 
	 * @return Lista de Ids
	 * @throws CKANException
	 */
	public List<String> revisionList() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/revision_list", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "revisionList");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista com as revis�es de um dataset (package) espec�fico.
	 * 
	 * @param	id	Id ou nome do dataset
	 * @return		Lista de datasets (tipo Package)
	 * @throws		CKANException
	 */
	public List<Package> packageRevisionList(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/package_revision_list", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRevisionList");
		
		return r.getResult();
	}

	/***
	 * Retorna um item relacionado espec�fico.
	 * 
	 * @param	id	Id do item relacionado desejado
	 * @return		Objeto do tipo Related
	 * @throws		CKANException
	 */
	public Related relatedShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/related_show", toJson(parametros));
		RelatedResponse r = loadClass(RelatedResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "relatedShow");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista de itens relacionados.
	 * 
	 * @param	id			Id ou nome do dataset
	 * 						<p>Opcional
	 * @param	type_filter	Tipo de item relacionado a ser buscado
	 * 						<p>Caso n�o informado retorna todos os tipos
	 * 						<p>Opcional
	 * @param	sort		Tipo de ordena��o a ser aplicado na lista
	 * 						<p>Valores poss�veis: 'view_count_asc', 'view_count_desc', 'created_asc' e 'created_desc'
	 * 						<p>Opicional
	 * @param	featured	Filtro para itens relacionados marcados como 'featured'
	 * 						<p>Valor padr�o: false
	 * 						<p>Opicional
	 * @return				Lista de objetos do tipo Related
	 * @throws				CKANException
	 */
	public List<Related> relatedList(String id, String type_filter, String sort, Boolean featured) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (id != null)
			parametros.put("id", id);

		if (type_filter != null)
			parametros.put("type_filter", type_filter);

		if (sort != null)
			parametros.put("sort", sort);

		if (featured != null)
			parametros.put("featured", featured.toString());

		String resposta = this.conexao.Post("/api/action/related_list", toJson(parametros));
		RelatedResponseList r = loadClass(RelatedResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "relatedList");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista de membros de um grupo.
	 * <p>Esse m�todo exige que a apikey utilizada tenha acesso de leitura ao grupo.
	 * 
	 * @param	id			Id ou nome do grupo
	 * @param	object_type	Restringe os membros retornados a um tipo espec�fico
	 * 						<p>Exemplo: 'user' ou 'package'
	 * 						<p>Opcional
	 * @param	capacity	Restringe os membros retornados de acordo com o perfil de acesso
	 * 						<p>Exemplo: 'member', 'editor', 'admin', 'public', 'private'
	 * 						<p>Opcional
	 * @return				Lista de objetos do tipo MemberList
	 * @throws				CKANException
	 */
	public List<MemberList> memberList(String id, String object_type, String capacity) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);

		if (object_type != null)
			parametros.put("object_type", object_type);

		if (capacity != null)
			parametros.put("capacity", capacity);

		String resposta = this.conexao.Post("/api/action/member_list", toJson(parametros));
		MemberListResponseList r = loadClass(MemberListResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "memberList");

		return r.getResult();
	}

	/***
	 * Retorna uma lista com os nomes dos grupos do site.
	 * 
	 * @param	sort	Forma de ordena��o do resultado no formato 'campo' 'sentido da ordena��o'
	 * 					<p>Exemplo: "name asc"
	 * 					<p>Valores poss�veis para 'campo': 'name' e 'packages'
	 * 					<p>Opcional 
	 * @param	groups	Lista dos nomes dos grupos a serem retornados
	 * 					<p>Opcional 
	 * @return			Lista de grupos do site
	 * @throws			CKANException
	 */
	public List<String> groupList(String sort, List<String> groups) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (sort != null)
			parametros.put("sort", sort);

		if (groups != null)
			parametros.put("groups", toJson(groups));

		String resposta = this.conexao.Post("/api/action/group_list", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "GroupList");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista com os nomes dos grupos do site.
	 * 
	 * @param	sort	Forma de ordena��o do resultado no formato 'campo' 'sentido da ordena��o'
	 *            		<p>Exemplo: "name asc"
	 *            		<p>Valores poss�veis para 'campo': 'name' e 'packages'
	 *            		<p>Opcional
	 * @param 	groups	Lista dos nomes dos grupos a serem retornados
	 * 					<p>Opcional
	 * @return			Lista de objetos do tipo Group	
	 * @throws			CKANException
	 */
	public List<Group> groupListAllFields(String sort, List<String> groups) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (sort != null)
			parametros.put("sort", sort);

		if (groups != null)
			parametros.put("groups", toJson(groups));

		parametros.put("all_fields", "true");

		String resposta = this.conexao.Post("/api/action/group_list", toJson(parametros));
		GroupResponseList r = loadClass(GroupResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "GroupListAllFields");

		return r.getResult();
	}

	/***
	 * Retorna uma lista com o nome das organiza��es cadastradas no site.
	 * 
	 * @return Lista de organiza��es cadastradas no site
	 * @throws CKANException
	 */
	public List<String> organizationList() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/organization_list", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationList");

		return r.getResult();
	}

	/**
	 * Retorna uma lista com os grupos que o usu�rio est� autorizado a editar.
	 * 
	 * @param	available_only	Remove os grupos existentes do pacote
	 * 							<p>Valor padr�o: false
	 *            				<p>Opcional
	 * @param	am_member		Se verdadeiro retorna apenas os grupos em que o usu�rio da apikey � membro, removendo os grupos em que o mesmo n�o � membro mas tem permiss�o de edi��o
	 * @return					Lista de grupos do site
	 * @throws					CKANException
	 */
	public List<String> groupListAuthz(Boolean available_only, Boolean am_member) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (available_only != null)
			parametros.put("available_only", available_only.toString());

		if (am_member != null)
			parametros.put("am_member", am_member.toString());

		String resposta = this.conexao.Post("/api/action/group_list_authz", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupListAuthz");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista de organiza��es que o usu�rio indicado � membro.
	 * 
	 * @param	permission	Permiss�o que o usu�rio deve possuir
	 * 						<p>Valor padr�o: edit_group
	 *            			<p>Opcional
	 * @return				Lista de objetos do tipo Organization
	 * @throws				CKANException
	 */
	public List<Organization> organizationListForUser(String permission) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (permission != null)
			parametros.put("permission", permission);

		String resposta = this.conexao.Post("/api/action/organization_list_for_user", toJson(parametros));
		OrganizationResponseList r = loadClass(OrganizationResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationListForUser");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista das revis�es de um grupo.
	 * 
	 * @param	id	Id ou nome do grupo
	 * @return		Lista de objetos do tipo Group
	 * @throws		CKANException
	 */
	public List<Group> groupRevisionList(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/group_revision_list", toJson(parametros));
		GroupResponseList r = loadClass(GroupResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupRevisionList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista das revis�es de uma organiza��o.
	 * 
	 * @param	id	Id ou nome da organiza��o
	 * @return		Lista das revis�es de uma organiza��o
	 * @throws		CKANException
	 */
	public List<Organization> organizationRevisionList(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/organization_revision_list", toJson(parametros));
		OrganizationResponseList r = loadClass(OrganizationResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationRevisionList");

		return r.getResult();
	}

	/**
	 * Retorna uma lista dos tipos de licen�as utilizadas no site.
	 * 
	 * @return Lista dos tipos de licen�as utilizadas no site
	 * @throws CKANException
	 */
	public List<License> licenseList() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/license_list", toJson(parametros));
		LicenseResponseList r = loadClass(LicenseResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "licenseList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista das tags utilizadas no site.
	 * <p>Por padr�o apenas tags livres (que n�o pertecem a um vocabul�rio) s�o retornadas.
	 * 
	 * @param	query			Chave a ser localizada no nome da tag
	 * 							<p>Opcional
	 * @param	vocabulary_id	Id ou nome de um vocabul�rio
	 * 							<p>Opcional
	 * @return					Lista das tags utilizadas no site					
	 * @throws 					CKANException
	 */
	public List<String> tagList(String query, String vocabulary_id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (query != null)
			parametros.put("query", query);
		
		if (vocabulary_id != null)
			parametros.put("vocabulary_id", vocabulary_id);
		
		String resposta = this.conexao.Post("/api/action/tag_list", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de todos os atributos das tags utilizadas no site.
	 * <p>Por padr�o apenas tags livres (que n�o pertecem a um vocabul�rio) s�o retornadas.
	 * 
	 * @param	query			Chave a ser localizada no nome da tag
	 * 							<p>Opcional
	 * @param	vocabulary_id	Id ou nome de um vocabul�rio
	 * 							<p>Opcional
	 * @return					Lista de todos os atributos das tags utilizadas no site
	 * @throws					CKANException
	 */
	public List<Tag> tagListAllFields(String query, String vocabulary_id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (query != null)
			parametros.put("query", query);
		
		if (vocabulary_id != null)
			parametros.put("vocabulary_id", vocabulary_id);
		
		parametros.put("all_fields", "True");
		
		String resposta = this.conexao.Post("/api/action/tag_list", toJson(parametros));
		TagResponseList r = loadClass(TagResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagListAllFields");
		
		return r.getResult();
	}

	/***
	 * Retorna uma lista de usu�rios do site.
	 * 
	 * @param	q			Chave a ser localizada no nome do usu�rio
	 * 						<p>Opcional
	 * @param	order_by	Campo a ser utilizado na ordena��o da listagem
	 * 						<p>Valor padr�o: 'name'
	 * 						<p>Opcional 
	 * @return				Lista de usu�rios
	 * @throws				CKANException
	 */
	public List<User> userList(String q, String order_by) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (q != null)
			parametros.put("q", q);
		
		if (order_by != null)
			parametros.put("order_by", order_by);
		
		String resposta = this.conexao.Post("/api/action/user_list", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de rela��es entre dois datasets.
	 * 
	 * @param	id	Id ou nome do primeiro dataset
	 * @param	id2	Id ou nome do segundo dataset
	 * @param	rel	Tipo de relacionamento
	 * 				<p>Valores poss�veis: 'depends_on', 'dependency_of', 'derives_from', 'has_derivation', 'links_to', 'linked_from', 'child_of' ou 'parent_of'
	 * 				<p>Opcional
	 * @return		Lista de rela��es entre dois datasets
	 * @throws		CKANException
	 */
	public List<Relationship> packageRelationshipsList(String id, String id2, String rel) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (rel != null)
			parametros.put("rel", rel);
		
		parametros.put("id", id);
		parametros.put("id2", id2);

		String resposta = this.conexao.Post("/api/action/package_relationships_list", toJson(parametros));
		RelationshipResponseList r = loadClass(RelationshipResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipsList");
		
		return r.getResult();
	}

	/**
	 * Retorna os metadados de um dataset (package) e seus recursos.
	 * 
	 * @param	id					Id ou nome do dataset desejado
	 * @param	use_default_schema	Usa um esquema de dataset padr�o no lugar de um esquema personalizado definido utilizando um plugin IDatasetForm
	 * 								<p>Valor padr�o: false
	 * 								<p>Opcional
	 * @return						Metadados de um dataset (package) e seus recursos
	 * @throws						CKANException
	 */
	public Package packageShow(String id, Boolean use_default_schema) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (use_default_schema != null)
			parametros.put("use_default_schema", use_default_schema.toString());
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/package_show", toJson(parametros));
		PackageResponse r = loadClass(PackageResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageShow");
		
		return r.getResult();
	}

	/**
	 * Retorna os metadados de um recurso.
	 * 
	 * @param	id	Id do recurso
	 * @return		Metadados de um recurso
	 * @throws		CKANException
	 */
	public Resource resourceShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/resource_show", toJson(parametros));
		ResourceResponse r = loadClass(ResourceResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "resourceShow");
		
		return r.getResult();
	}

	/**
	 * Retorna os detalhes de uma revis�o.
	 * 
	 * @param	id	Id da revis�o
	 * @return		Detalhes de uma revis�o
	 * @throws		CKANException
	 */
	public Revision revisionShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/revision_show", toJson(parametros));
		RevisionResponse r = loadClass(RevisionResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "revisionShow");
		
		return r.getResult();
	}

	/**
	 * Retorna os detalhes de um grupo.
	 * <p>Nota: apenas os 1.000 primeiros datasets do grupo ser�o retornados.
	 * 
	 * @param	id					Id ou nome do grupo
	 * @param	include_datasets	Inclue a lista dos datasets do grupo (opcional, valor padr�o: true)
	 * @return						Detalhes de um grupo
	 * @throws						CKANException
	 */
	public Group groupShow(String id, Boolean include_datasets) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		if (include_datasets != null)
			parametros.put("include_datasets", include_datasets.toString());
		
		String resposta = this.conexao.Post("/api/action/group_show", toJson(parametros));
		GroupResponse r = loadClass(GroupResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupShow");
		
		return r.getResult();
	}

	/**
	 * Retorna os detalhes de uma organiza��o.
	 * <p>Nota: apenas os 1.000 primeiros datasets da organiza��o ser�o retornados.
	 * 
	 * @param	id					Id ou nome da organiza��o
	 * @param	include_datasets	Inclue a lista dos datasets da organiza��o (opcional, valor padr�o: true)
	 * @return						Detalhes de uma organiza��o
	 * @throws						CKANException
	 */
	public Organization organizationShow(String id, Boolean include_datasets) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		if (include_datasets != null)
			parametros.put("include_datasets", include_datasets.toString());
		
		String resposta = this.conexao.Post("/api/action/organization_show", toJson(parametros));
		OrganizationResponse r = loadClass(OrganizationResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationShow");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de datasets de um grupo.
	 * 
	 * @param	id		Id ou nome do grupo
	 * @param	limit	N�mero m�ximo de datasets a ser retornado (opcional)
	 * @return			Lista de datasets de um grupo
	 * @throws			CKANException
	 */
	public List<Package> groupPackageShow(String id, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		if (limit != null)
			parametros.put("limit", limit.toString());
		
		String resposta = this.conexao.Post("/api/action/group_package_show", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupPackageShow");
		
		return r.getResult();
	}

	/**
	 * Retorna os detalhes de uma tag e todos os seus datasets.
	 * 
	 * @param	id	Id ou nome da tag
	 * @return		Detalhes de uma tag e todos os seus datasets
	 * @throws		CKANException
	 */
	public Tag tagShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/tag_show", toJson(parametros));
		TagResponse r = loadClass(TagResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagShow");
		
		return r.getResult();
	}

	/**
	 * Retorna a conta de um usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Conta de um usu�rio
	 * @throws		CKANException
	 */
	public User userShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/user_show", toJson(parametros));
		UserResponse r = loadClass(UserResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userShow");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de datasets em que o t�tulo ou o nome cont�m uma dada chave.
	 * 
	 * @param	q		Chave a ser buscada no nome ou t�tulo do dataset
	 * @param	limit	N�mero m�ximo de formato de recursos a serem retornados (opcional, valor padr�o: 10)
	 * @return			Lista de datasets
	 * @throws 			CKANException
	 */
	public List<Package> packageAutocomplete(String q, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("q", q);

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/package_autocomplete", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageAutocomplete");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de formatos de recurso que o respectivo nome cont�m uma dada chave.
	 * 
	 * @param	q		Chave a ser buscada no nome do formato
	 * @param	limit	N�mero m�ximo de formatos de recurso a serem retornados
	 * 					<p>Valor padr�o: 5
	 * 					<p>Opcional
	 * @return			Lista de formatos de recurso
	 * @throws 			CKANException
	 */
	public List<String> formatAutocomplete(String q, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("q", q);

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/format_autocomplete", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "formatAutocomplete");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de usu�rios que cont�m uma dada chave.
	 * 
	 * @param	q		Chave a ser buscada
	 * @param	limit	N�mero m�ximo de usu�rios a serem retornados
	 * 					<p>Valor padr�o: 20
	 * 					<p>Opcional
	 * @return 			Lista de objetos User com os atributos 'name', 'fullname' e 'id' preenchidos
	 * @throws			CKANException
	 */
	public List<User> userAutocomplete(String q, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("q", q);

		if (limit != null)
			parametros.put("limit", limit.toString());
		
		String resposta = this.conexao.Post("/api/action/user_autocomplete", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userAutocomplete");
		
		return r.getResult();
	}

	public List<Package> package_search() throws CKANException {
		// TODO: IMPLEMENTAR
		return null;
	}

	/**
	 * Retorna uma lista de recursos que satisfazem um crit�rio de busca dado
	 * 
	 * @param	query		Crit�rio de busca no formato 'campo':'valor', podendo ser uma lista de strings nesse mesmo formato
	 * 						<p>Verifique a documenta��o da API do CKAN para mais informa��es sobre o par�metro
	 * @param	order_by	Campo a ser utilizado para ordena��o dos resultados
	 * 						<p>Apenas um campo pode ser indicado e ele sempre ser� ordenado de forma ascendente
	 * @param	offset		Indica a partir de qual registro os resultados ser�o retornados
	 * @param	limit		Indica o n�mero m�ximo de resultados a serem retornados.
	 * @return				Lista de recursos
	 * @throws 				CKANException
	 */
	public List<Resource> resourceSearch(String query, String order_by, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("query", query);
		parametros.put("order_by", order_by);
		parametros.put("offset", offset.toString());
		parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/resource_search", toJson(parametros));
		ResourceResponseList r = loadClass(ResourceResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "resourceSearch");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de tags em que o nome cont�m uma dada chave.
	 * 
	 * @param 	query			Chave a ser buscada
	 * @param	vocabulary_id	Id ou nome de um vocubul�rio ao qual deseja-se restringir a busca
	 * 							<p>Opcional
	 * @param	offset			Indica a partir de qual registro os resultados ser�o retornados
	 * 							<p>Considerado somente quando o par�metro limit � informado
	 * @param	limit			N�mero m�ximo de resultados a serem retornados
	 * 							<p>Opcional
	 * @return					Lista de tags
	 * @throws					CKANException
	 */
	public List<Tag> tagSearch(String query, String vocabulary_id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("query", query);

		if (limit != null) {
			parametros.put("offset", offset.toString());
			parametros.put("limit", limit.toString());
		}

		if (vocabulary_id != null)
			parametros.put("vocabulary_id", vocabulary_id);
		
		String resposta = this.conexao.Post("/api/action/tag_search", toJson(parametros));
		TagResponseList r = loadClass(TagResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagSearch");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de tags em que o nome cont�m uma dada chave.
	 * 
	 * @param	query			Chave a ser buscada
	 * @param	vocabulary_id	Id ou nome de um vocubul�rio ao qual deseja-se restringir a busca
	 * 							<p>Opcional
	 * @param	offset			Indica a partir de qual registro os resultados ser�o retornados
	 * 							<p>Considerado somente quando o par�metro limit � informado
	 * @param	limit			N�mero m�ximo de resultados a serem retornados
	 * 							<p>Opcional
	 * @return					Lista de tags
	 * @throws					CKANException
	 */
	public List<String> tag_autocomplete(String query, String vocabulary_id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("query", query);

		if (limit != null) {
			parametros.put("offset", offset.toString());
			parametros.put("limit", limit.toString());
		}

		if (vocabulary_id != null) 
			parametros.put("vocabulary_id", vocabulary_id);
		
		String resposta = this.conexao.Post("/api/action/tag_autocomplete", toJson(parametros));
		StringResponseList r = loadClass(StringResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tag_autocomplete");
		
		return r.getResult();
	}

	/**
	 * Retorna o status de uma task.
	 * <p>Apesar de todos os par�metros serem opcionais, ao menos um par�metro deve ser informado.
	 * 
	 * @param	id 			Id do status da task
	 * @param	entity_id 	Id da entitdade do status da task
	 * @param 	task_type	Tipo de task da do status da task
	 * @param 	key			Chave do status da task
	 * @return				Status de uma task
	 * @throws 				CKANException
	 */
	public Task taskStatusShow(String id, String entity_id, String task_type, String key) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (id != null)
			parametros.put("id", id);
		
		if (entity_id != null)
			parametros.put("entity_id", entity_id);
		
		if (task_type != null)
			parametros.put("task_type", task_type.toString());
		
		if (key != null)
			parametros.put("key", key);
		
		String resposta = this.conexao.Post("/api/action/task_status_show", toJson(parametros));
		TaskResponse r = loadClass(TaskResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "taskStatusShow");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista com a tradu��o de um dado termo.
	 * 
	 * @param	terms		Termos a serem traduzidos
	 * @param 	lang_codes	C�digo da linguagem desejada
	 * 						<p>Por padr�o, pesquisa por tradu��es em qualquer idioma
	 * 						<p>Opcional
	 * @return				Lista com a tradu��o de um dado termo
	 * @throws 				CKANException
	 */
	public List<TermTranslation> termTranslationShow(String terms, String lang_codes) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("terms", terms);
		
		if (lang_codes != null) {
			parametros.put("lang_codes", lang_codes);
		}

		String resposta = this.conexao.Post("/api/action/term_translation_show", toJson(parametros));
		TermTranslationResponseList r = loadClass(TermTranslationResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "termTranslationShow");
		
		return r.getResult();
	}

	/**
	 * Retorna as roles de um usu�rio para um objeto.
	 * 
	 * @param	domain_object 	Id ou nome de um dataset ou grupo
	 * @param 	user 			Id ou nome do usu�rio
	 * @return					Roles de um usu�rio
	 * @throws 					CKANException
	 */
	public RoleShow rolesShow(String domain_object, String user) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("domain_object", domain_object);
		parametros.put("user ", user);

		String resposta = this.conexao.Post("/api/action/roles_show", toJson(parametros));
		RoleShowResponse r = loadClass(RoleShowResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "rolesShow");
		
		return r.getResult();
	}

	/**
	 * Retorna as informa��es da configura��o do site.
	 * 
	 * @return	Informa��es da configura��o do site
	 * @throws	CKANException
	 */
	public Site statusShow() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/status_show", toJson(parametros));
		SiteResponse r = loadClass(SiteResponse.class, resposta);
		
		if (!r.getSuccess())
			controlarErro(resposta, "statusShow");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista com todos os vocabul�rios do site
	 * 
	 * @return	Lista de vocabul�rio
	 * @throws	CKANException
	 */
	public List<Vocabulary> vocabularyList() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/vocabulary_list", toJson(parametros));
		VocabularyResponseList r = loadClass(VocabularyResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "vocabularyList");
		
		return r.getResult();
	}

	/**
	 * Retorna um vocabul�rio espec�fico.
	 * 
	 * @param	id 	Id ou nome do vocabul�rio
	 * @return		Vocabul�rio
	 * @throws		CKANException
	 */
	public Vocabulary vocabularyShow(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/vocabulary_show", toJson(parametros));
		VocabularyResponse r = loadClass(VocabularyResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "vocabularyShow");
		
		return r.getResult();
	}

	/**
	 * Retorna lista de atividades p�blicas de um usu�rio.
	 * 
	 * @param 	id 		Id ou nome do usu�rio
	 * @param	offset 	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Lista de atividades
	 * @throws 			CKANException
	 */
	public List<Activity> userActivityList(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/user_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades em um dataset.
	 * 
	 * @param	id 		Id ou nome do dataset
	 * @param	offset 	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Lista de atividades
	 * @throws 			CKANException
	 */
	public List<Activity> packageActivityList(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/package_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades em um grupo.
	 * 
	 * @param	id 		Id ou nome do grupo
	 * @param 	offset 	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param 	limit 	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Lista de atividades
	 * @throws 			CKANException
	 */
	public List<Activity> groupActivityList(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/group_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades de uma organiza��o.
	 * 
	 * @param	id	Id ou nome da organiza��o
	 * @return		Lista de atividades
	 * @throws		CKANException
	 */
	public List<Activity> organizationActivityList(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/organization_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades em datasets recentemente criados ou alterados.
	 * 
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Valor padr�o: 31
	 * 					<p>Opcional
	 * @return			Llista de atividades
	 * @throws 			CKANException
	 */
	public List<Activity> recentlyChangedPackagesActivityList(Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/recently_changed_packages_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "recentlyChangedPackagesActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades de um item de detalhe de atividade.
	 * 
	 * @param	id	Id da atividade
	 * @return		Lista de atividades
	 * @throws		CKANException
	 */
	public List<Activity> activityDetailList(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/activity_detail_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "recentlyChangedPackagesActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna a atividade p�blica de um usu�rio em formato html.
	 * 
	 * @param	id		Id ou nome do usu�rio
	 * @param	offset 	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit 	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Atividade de um usu�rio
	 * @throws 			CKANException
	 */
	public String userActivityListHTML(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);
		
		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/user_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userActivityListHTML");

		return r.getResult();
	}

	/**
	 * Retorna a atividade de um dataset em formato html.
	 * 
	 * @param	id 		Id ou nome do dataset
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Atividade de um dataset
	 * @throws 			CKANException
	 */
	public String packageActivityListHTML(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);
		
		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/package_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageActivityListHTML");

		return r.getResult();
	}

	/**
	 * Retorna a atividade de um grupo em formato html.
	 * 
	 * @param 	id 		Id ou nome do grupo
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Opcional
	 * @return			Atividade de um grupo
	 * @throws			CKANException
	 */
	public String groupActivityListHTML(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		parametros.put("id", id);
		
		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());
		
		String resposta = this.conexao.Post("/api/action/group_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupActivityListHTML");
		
		return r.getResult();
	}

	/**
	 * Retorna a atividade de uma organiza��o em formato html.
	 * 
	 * @param	id 	Id ou nome da organiza��o
	 * @return		Atividade de uma organiza��o
	 * @throws 		CKANException
	 */
	public String organizationActivityListHTML(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/organization_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationActivityListHTML");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de atividades em datasets recentemente criados ou alterados em formato HTML.
	 * 
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit 	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Valor padr�o: 31
	 * 					<p>Opcional
	 * @return			Lista de atividades
	 * @throws 			CKANException
	 */
	public String recentlyChangedPackagesActivityListHTML(Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();

		if (offset != null)
			parametros.put("offset", offset.toString());

		if (limit != null)
			parametros.put("limit", limit.toString());

		String resposta = this.conexao.Post("/api/action/recently_changed_packages_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "recentlyChangedPackagesActivityListHTML");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de seguidores de um usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		N�mero de seguidores
	 * @throws 		CKANException
	 */
	public Integer userFollowerCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/user_follower_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userFollowerCount");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de seguidores de um dataset.
	 * 
	 * @param	id	Id ou nome do dataset
	 * @return		N�mero de seguidores
	 * @throws 		CKANException
	 */
	public Integer datasetFollowerCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/dataset_follower_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "datasetFollowerCount");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de seguidores de um grupo.
	 * 
	 * @param	id	Id ou nome do grupo
	 * @return		N�mero de seguidores
	 * @throws 		CKANException
	 */
	public Integer grupoFollowerCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/group_follower_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "grupoFollowerCount");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de usu�rios que seguem um dado usu�rio.
	 * 
	 * @param	id 	Id ou nome do usu�rio
	 * @return		Lista de usu�rios
	 * @throws 		CKANException
	 */
	public List<User> userFollowerList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/user_follower_list", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userFollowerList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de usu�rios que seguem um dado dataset.
	 * 
	 * @param	id	Id ou nome do dataset
	 * @return		Lista de usu�rios
	 * @throws 		CKANException
	 */
	public List<User> datasetFollowerList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/dataset_follower_list", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "datasetFollowerList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de usu�rios que seguem um dado dataset.
	 * 
	 * @param	id 	Id ou nome do grupo
	 * @return		Lista de usu�rios
	 * @throws 		CKANException
	 */
	public List<User> groupFollowerList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/group_follower_list", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupFollowerList");
		
		return r.getResult();
	}

	/**
	 * Retorna verdadeiro caso o usu�rio da apikey em uso esteja seguindo um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Valor booleano
	 * @throws		CKANException
	 */
	public Boolean amFollowingUser(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/am_following_user", toJson(parametros));
		BooleanResponse r = loadClass(BooleanResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "amFollowingUser");
		
		return r.getResult();
	}

	/**
	 * Retorna verdadeiro caso o usu�rio da apikey em uso esteja seguindo um dado dataset.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Valor booleano
	 * @throws 		CKANException
	 */
	public Boolean amFollowingDataset(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/am_following_dataset", toJson(parametros));
		BooleanResponse r = loadClass(BooleanResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "amFollowingDataset");
		
		return r.getResult();
	}

	/**
	 * Retorna verdadeiro caso o usu�rio da apikey em uso esteja seguindo um dado grupo.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Valor booleano
	 * @throws 		CKANException
	 */
	public Boolean amFollowingGrupo(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/am_following_group", toJson(parametros));
		BooleanResponse r = loadClass(BooleanResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "amFollowingGrupo");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de objetos seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		N�mero de objetos
	 * @throws 		CKANException
	 */
	public Integer followeeCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/followee_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "followeeCount");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de usu�rios que s�o seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		N�mero de usu�rios
	 * @throws 		CKANException
	 */
	public Integer userFolloweeCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/user_followee_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userFolloweeCount");
	
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de datasets que s�o seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		N�mero de datasets
	 * @throws 		CKANException
	 */
	public Integer datasetFolloweeCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/dataset_followee_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "datasetFolloweeCount");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de grupos que s�o seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		N�mero de grupos
	 * @throws 		CKANException
	 */
	public Integer groupFolloweeCount(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/group_followee_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupFolloweeCount");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de objetos seguidos por um dado usu�rio
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @param	q	Chave de pesquisa para restringir os objetos pelo nome
	 * 				<p>Case-insensitive
	 * 				<p>Opcional
	 * @return		N�mero de objetos
	 * @throws 		CKANException
	 */
	public Integer followeeList(Integer id, String q) throws CKANException {
		// TODO: Implementar m�todo
		return null;
	}

	/**
	 * Retorna uma lista de usu�rios que s�o seguidos por um dado usu�rio
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Lista de usu�rios
	 * @throws 		CKANException
	 */
	public List<User> userFolloweeList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/user_followee_list", toJson(parametros));
		UserResponseList r = loadClass(UserResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userFolloweeList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de datasets que s�o seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Lista de datasets
	 * @throws 		CKANException
	 */
	public List<Package> datasetFolloweeList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/dataset_followee_list", toJson(parametros));
		PackageResponseList r = loadClass(PackageResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "datasetFolloweeList");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista de grupos que s�o seguidos por um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio
	 * @return		Lista de grupos
	 * @throws 		CKANException
	 */
	public List<Group> groupFolloweeList(Integer id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id.toString());

		String resposta = this.conexao.Post("/api/action/group_followee_list", toJson(parametros));
		GroupResponseList r = loadClass(GroupResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupFolloweeList");
		
		return r.getResult();
	}

	/**
	 * Retorna o painel de controle do usu�rio com o stream de atividades.
	 * 
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>valor padr�o: 31
	 * 					<p>Opcional
	 * @return			
	 * @throws 			CKANException
	 */
	public List<Activity> dashboardActivityList(Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (offset != null)
			parametros.put("offset", offset.toString());
		
		if (limit != null)
			parametros.put("limit", limit.toString());
		
		String resposta = this.conexao.Post("/api/action/dashboard_activity_list", toJson(parametros));
		ActivityResponseList r = loadClass(ActivityResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "dashboardActivityList");
		
		return r.getResult();
	}

	/**
	 * Retorna o painel de controle do usu�rio com o stream de atividades.
	 * 
	 * @param	id		Id ou nome do usu�rio
	 * @param	offset	Indica a partir de qual registro os resultados ser�o retornados
	 * 					<p>Opcional
	 * @param	limit	Indica a quantidade m�xima de registros a serem retornados
	 * 					<p>Calor padr�o: 31
	 * 					<p>Opcional
	 * @return			Painel de controle do usu�rio
	 * @throws			CKANException
	 */
	public String dashboardActivityListHTML(String id, Integer offset, Integer limit) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put(id, id);
		
		if (offset != null)
			parametros.put("offset", offset.toString());
		
		if (limit != null)
			parametros.put("limit", limit.toString());
		
		String resposta = this.conexao.Post("/api/action/dashboard_activity_list_html", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "dashboardActivityListHTML");
		
		return r.getResult();
	}

	/**
	 * Retorna o n�mero de novas atividades no painel de controle do usu�rio da apikey.
	 * 
	 * @return	N�mero de novas atividades no painel de controle do usu�rio
	 * @throws	CKANException
	 */
	public Integer dashboardNewActivitiesCount() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/dashboard_new_activities_count", toJson(parametros));
		IntegerResponse r = loadClass(IntegerResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "dashboardNewActivitiesCount");
		
		return r.getResult();
	}

	/**
	 * Retorna uma lista das poss�veis roles para membros de grupos e organiza��es.
	 * 
	 * @param	group_type	Tipo de objeto
	 * 						<p>Valores poss�veis: 'group' ou 'organization'
	 * 						<p>Calor padr�o: 'organization'
	 * 						<p>Opcional
	 * @return				Lista de roles
	 * @throws 				CKANException
	 */
	public List<Role> memberRolesList(String group_type) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		if (group_type != null)
			parametros.put("group_type", group_type);

		String resposta = this.conexao.Post("/api/action/member_roles_list", toJson(parametros));
		RoleResponseList r = loadClass(RoleResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "memberRolesList");

		return r.getResult();
	}

	/**
	 * Cria um novo dataset.
	 * 
	 * @param	_package	Objeto Package contendo todos os dados do novo dataset a ser criado
	 * @return				Dataset
	 * @throws 				CKANException
	 */
	public Package packageCreate(Package _package) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/package_create", gson.toJson(_package));
		PackageResponse r = loadClass(PackageResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageCreate");
		
		return r.getResult();
	}

	/**
	 * Adiciona um novo recurso a um dataset.
	 * 
	 * @param	resource	Objeto Resource contendo os dados do novo recurso
	 * @return				Recurso
	 * @throws 				CKANException
	 */
	public Resource resourceCreate(Resource resource) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/resource_create", gson.toJson(resource));
		ResourceResponse r = loadClass(ResourceResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "resourceCreate");
		
		return r.getResult();
	}

	/**
	 * Adiciona um novo item relacionado a um dataset.
	 * 
	 * @param	related Objeto Related contendo os dados do novo item relacionado
	 * @return			Item relacionado
	 * @throws			CKANException
	 */
	public Related relatedCreate(Related related) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/related_create", gson.toJson(related));
		RelatedResponse r = loadClass(RelatedResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "relatedCreate");
		
		return r.getResult();
	}

	/**
	 * Cria um relacionamento entre dois datasets.
	 * 
	 * @param	subject	Id ou nome do dataset que � referenciado no relacionamento
	 * @param	object	Id ou nome do dataset que � refer�ncia no relacionamento
	 * @param	type	Tipo do relacionamento
	 * 					<p> Valores poss�veis: 'depends_on', 'dependency_of', 'derives_from', 'has_derivation', 'links_to', 'linked_from', 'child_of' ou 'parent_of'
	 * @param	comment	Coment�rios sobre o relacionamento
	 * 					<p>Opcional
	 * @return			Relacionamento entre dois datasets
	 * @throws			CKANException
	 */
	public Relationship packageRelationshipCreate(String subject, String object, String type, String comment) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("subject", subject);
		parametros.put("object", object);
		parametros.put("type", type);

		if (comment != null)
			parametros.put("comment", comment);

		String resposta = this.conexao.Post("/api/action/package_relationship_create", toJson(parametros));
		RelationshipResponse r = loadClass(RelationshipResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipCreate");
		
		return r.getResult();
	}

	/**
	 * Transforma um objeto (um usu�rio, dataset ou grupo) membro de um grupo.
	 * <p>Caso o objeto j� seja membro do grupo, o v�nculo ser� atualizado.
	 * 
	 * @param	id			Id ou nome do grupo ao qual o objeto ser� adicionado
	 * @param 	object		Id ou nome do objeto a ser adicionado
	 * @param	object_type	Tipo de objeto a ser adicionado
	 * 						<p>Exemplo: 'package','user'
	 * @param 	capacity	Capacidade do relacionamento
	 * @return				Membro de um grupo
	 * @throws 				CKANException
	 */
	public MemberList memberCreate(String id, String object, String object_type, String capacity) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("object", object);
		parametros.put("object_type", object_type);
		parametros.put("capacity", capacity);

		String resposta = this.conexao.Post("/api/action/member_create", toJson(parametros));
		MemberListResponse r = loadClass(MemberListResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipCreate");
		
		return r.getResult();
	}

	/**
	 * Cria um novo grupo.
	 * 
	 * @param	group	Objeto Group contendo os dados do novo grupo
	 * @return			Grupo
	 * @throws			CKANException
	 */
	public Group groupCreate(Group group) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/group_create", gson.toJson(group));
		GroupResponse r = loadClass(GroupResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupCreate");
		
		return r.getResult();
	}

	/**
	 * Cria uma nova organiza��o.
	 * 
	 * @param	organization	Objeto Organization contendo todos os atributos da nova organiza��o
	 * @return					Organiza��o
	 * @throws					CKANException
	 */
	public Organization organizationCreate(Organization organization) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/organization_create", gson.toJson(organization));
		OrganizationResponse r = loadClass(OrganizationResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationCreate");
		
		return r.getResult();
	}

	/**
	 * Avalia um dataset.
	 * <p>Este m�todo exige a utiliza��o de uma apikey.
	 * 
	 * @param	_package	Id ou nome do dataset a ser avaliado
	 * @param	rating		Avalia��o do dataset, em uma escala de 1 a 5
	 * @return				Objeto do tipo Rating
	 * @throws				CKANException
	 */
	public Rating ratingCreate(String _package, Integer rating) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("package", _package);
		parametros.put("rating", rating.toString());

		String resposta = this.conexao.Post("/api/action/rating_create", toJson(parametros));
		RatingResponse r = loadClass(RatingResponse.class, resposta);

		if (!r.getSuccess()) 
			controlarErro(resposta, "ratingCreate");
		
		return r.getResult();
	}

	/**
	 * Cria um novo usu�rio.
	 * 
	 * @param	user	Objeto do tipo User com os dados do novo usu�rio
	 * @return			Objeto do tipo User
	 * @throws			CKANException
	 */
	public User userCreate(User user) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/user_create", gson.toJson(user));
		UserResponse r = loadClass(UserResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userCreate");
		
		return r.getResult();
	}

	/**
	 * Convida um usu�rio para entrar em um grupo.
	 * 
	 * @param	email		Email do usu�rio a ser convidado para o grupo
	 * @param	group_id	Id ou nome do grupo
	 * @param	role		Role do novo usu�rio no grupo. Valores poss�veis: member, editor e admin
	 * @return				Objeto do tipo User
	 * @throws				CKANException
	 */
	public User userInvite(String email, String group_id, String role) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("email", email);
		parametros.put("group_id", group_id);
		parametros.put("role", role);

		String resposta = this.conexao.Post("/api/action/user_invite", toJson(parametros));
		UserResponse r = loadClass(UserResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userInvite");
		
		return r.getResult();
	}

	/**
	 * Cria um novo vocabul�rio.
	 * 
	 * @param	vocabulary	Objeto do tipo Vocabulary com os dados do novo vocabul�rio
	 * @return				Objeto do tipo Vocabulary
	 * @throws				CKANException
	 */
	public Vocabulary vocabularyCreate(Vocabulary vocabulary) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/vocabulary_create", gson.toJson(vocabulary));
		VocabularyResponse r = loadClass(VocabularyResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "vocabularyCreate");
		
		return r.getResult();
	}

	/**
	 * Cria uma nova atividade.
	 * <p>Este m�todo requer permiss�o de sysadmin para o usu�rio da apikey.
	 * 
	 * @param	activity	Objeto do tipo Activity com os dados da atividade
	 * @return				
	 * @throws				CKANException
	 */
	public Activity activityCreate(Activity activity) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/activity_create", gson.toJson(activity));
		ActivityResponse r = loadClass(ActivityResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "activityCreate");
		
		return r.getResult();
	}

	/**
	 * Cria uma nova tag.
	 * <p>Esse m�todo exige que a tag seja vinculada a um vocabul�rio, n�o sendo permitido a cria��o de tags livres.
	 * <p>Tags livres podem ser adicionadas diretamente a um dataset, atrav�s do m�todo packageUpdate.
	 * <p>Este m�todo requer permiss�o de sysadmin para o usu�rio da apikey.
	 * 
	 * @param	tag	Objeto do tipo Tag com os dados da nova Tag (name e vocabulary_id)
	 * @return		Objeto do tipo Tag
	 * @throws		CKANException
	 */
	public Tag tagCreate(Tag tag) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/tag_create", gson.toJson(tag));
		TagResponse r = loadClass(TagResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagCreate");
		
		return r.getResult();
	}

	/**
	 * M�todo que permite ao usu�rio da apikey em uso come�ar a seguir um dado usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio a ser seguido
	 * @return		Objeto do tipo Follow
	 * @throws		CKANException
	 */
	public Follow followUser(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/follow_user", toJson(parametros));
		FollowResponse r = loadClass(FollowResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "followUser");
		
		return r.getResult();
	}

	/**
	 * M�todo que permite ao usu�rio da apikey em uso come�ar a seguir um dado dataset.
	 * 
	 * @param	id	Id ou nome do dataset a ser seguido
	 * @return		Objeto do tipo Follow
	 * @throws		CKANException
	 */
	public Follow followDataset(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/follow_dataset", toJson(parametros));
		FollowResponse r = loadClass(FollowResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "followDataset");
		
		return r.getResult();
	}

	/**
	 * Insere um usu�rio em um grupo.
	 * 
	 * @param	id			Id ou nome do grupo que ir� receber o usu�rio
	 * @param	username	Id ou nome do usu�rio a ser inserido no grupo
	 * @param	role		Role do usu�rio no grupo
	 *						<p>Valores poss�veis: 'member', 'editor' ou 'admin'
	 * @return				Objeto do tipo Membership
	 * @throws				CKANException
	 */
	public Membership groupMemberCreate(String id, String username, String role) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("username", username);
		parametros.put("role", role);

		String resposta = this.conexao.Post("/api/action/group_member_create", toJson(parametros));
		MembershipResponse r = loadClass(MembershipResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupMemberCreate");
		
		return r.getResult();
	}

	/**
	 * Insere um usu�rio em uma organiza��o.
	 * 
	 * @param	id			Id ou nome da organiza��o que ir� receber o usu�rio
	 * @param	username	Id ou nome do usu�rio a ser inserido na organiza��o
	 * @param	role		Role do usu�rio no grupo. Valores poss�veis: 'member', 'editor' ou 'admin'.
	 * @return				Objeto do tipo Membership
	 * @throws 				CKANException
	 */
	public Membership organizationMemberCreate(String id, String username, String role) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("username", username);
		parametros.put("role", role);

		String resposta = this.conexao.Post("/api/action/organization_member_create", toJson(parametros));
		MembershipResponse r = loadClass(MembershipResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationMemberCreate");
		
		return r.getResult();
	}

	/**
	 * M�todo que permite ao usu�rio da apikey em uso come�ar a seguir um dado grupo.
	 * 
	 * @param	id	Id ou nome do grupo a ser seguido
	 * @return		Objeto do tipo Follow
	 * @throws		CKANException
	 */
	public Follow followGroup(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);

		String resposta = this.conexao.Post("/api/action/follow_group", toJson(parametros));
		FollowResponse r = loadClass(FollowResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "followGroup");
		
		return r.getResult();
	}

	/**
	 * Documenta��o do m�todo n�o dispon�vel.
	 * 
	 * @param	id	Id ou nome do dataset
	 * @return		Dataset (tipo Package)
	 * @throws		CKANException
	 */
	public Package makeLatestPendingPackageActive(String id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/make_latest_pending_package_active", toJson(parametros));
		PackageResponse r = loadClass(PackageResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "followGroup");
		
		return r.getResult();
	}

	/**
	 * Atualiza um item relacionado.
	 * 
	 * @param	related	Objeto do tipo Related com os dados do item relacionado a ser atualizado
	 * @return			Objeto do tipo Related
	 * @throws 			CKANException
	 */
	public Related relatedUpdate(Related related) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/related_update", gson.toJson(related));
		RelatedResponse r = loadClass(RelatedResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "relatedUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza um recurso.
	 * 
	 * @param	resource	Objeto do tipo Resource com os dados do recurso a ser atualizado
	 * @return				Objeto do tipo Resource 
	 * @throws				CKANException
	 */
	public Resource resourceUpdate(Resource resource) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/resource_update", gson.toJson(resource));
		ResourceResponse r = loadClass(ResourceResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "resourceUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza um dataset.
	 * 
	 * @param	package	Objeto do tipo Package com os dados do dataset a ser atualizado
	 * @return			Objeto do tipo Resource
	 * @throws			CKANException
	 */
	public Resource packageUpdate(Package dataset) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/package_update", gson.toJson(dataset));
		ResourceResponse r = loadClass(ResourceResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageUpdate");
		
		return r.getResult();
	}

	/**
	 * Reordena os recursos de um dataset.
	 * <p>Se apenas uma parte dos recursos forem indicados no par�metro order, os recursos informados ser�o colocados no in�cio e os demais manter�o a mesma ordem.
	 * 
	 * @param	id		Id ou nome do dataset a ser atualizado
	 * @param	order	Lista de ids de recurso na ordem desejada
	 * @return			Objeto do tipo Resource
	 * @throws			CKANException
	 */
	public Resource packageResourceReorder(String id, List<String> order) throws CKANException {
		Gson gson = new Gson();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("id", id);
		parametros.put("order", order);

		String resposta = this.conexao.Post("/api/action/package_resource_reorder", gson.toJson(parametros));
		ResourceResponse r = loadClass(ResourceResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageResourceReorder");
		
		return r.getResult();
	}

	/**
	 * Atualiza um relacionamento entre dois datasets.
	 * 
	 * @param	relationship	Objeto do tipo Relationship com os dados do relacionamento a ser atualizado
	 * @return					Objeto do tipo Relationship
	 * @throws					CKANException
	 */
	public Relationship packageRelationshipUpdate(Relationship relationship) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/package_relationship_update", gson.toJson(relationship));
		RelationshipResponse r = loadClass(RelationshipResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza um dado grupo.
	 * 
	 * @param	group	Objeto do tipo Group com os dados do grupo a ser atualizado
	 * @return			Objeto do tipo Group
	 * @throws			CKANException
	 */
	public Group groupUpdate(Group group) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/group_update", gson.toJson(group));
		GroupResponse r = loadClass(GroupResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza uma organiza��o.
	 * 
	 * @param	organization	Objeto do tipo Organization com os dados da organiza��o a ser atualizada
	 * @return					Objeto do tipo Organization
	 * @throws					CKANException
	 */
	public Organization organizationUpdate(Organization organization) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/organization_update", gson.toJson(organization));
		OrganizationResponse r = loadClass(OrganizationResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza a conta de um usu�rio.
	 * <p>Usu�rios comuns podem atualizar somente a pr�pria conta.
	 * <p>Sysadmins podem atualizar qualquer conta.
	 * 
	 * @param	user	Objeto do tipo User com os dados do usu�rio a ser atualizado
	 * @return			Objeto do tipo User
	 * @throws 			CKANException
	 */
	public User userUpdate(User user) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/user_update", gson.toJson(user));
		UserResponse r = loadClass(UserResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza em bloco um conjunto de tarefas.
	 * 
	 * @param	tasks	Lista de objetos do tipo Task com as tarefas a serem atualizadas
	 * @return			Lista de objetos do tipo Task
	 * @throws			CKANException
	 */
	public List<Task> taskStatusUpdateMany(List<Task> tasks) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/task_status_update_many", gson.toJson(tasks));
		TaskResponseList r = loadClass(TaskResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "taskStatusUpdateMany");
		
		return r.getResult();
	}

	/**
	 * Cria ou atualiza uma tradu��o de termo.
	 * 
	 * @param	term				Termo no idioma original
	 * @param	term_translation	Tradu��o do termo na linguagem especificada
	 * @param	lang_code			C�digo da linguagem
	 * @return						Objeto do tipo TermTranslation 
	 * @throws						CKANException
	 */
	public TermTranslation termTranslationUpdate(String term, String term_translation, String lang_code) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("term", term);
		parametros.put("term_translation", term_translation);
		parametros.put("lang_code", lang_code);

		String resposta = this.conexao.Post("/api/action/term_translation_update", toJson(parametros));
		TermTranslationResponse r = loadClass(TermTranslationResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "termTranslationUpdate");
		
		return r.getResult();
	}

	/**
	 * Cria ou atualiza em lote tradu��es de termos.
	 * 
	 * @param	terms	Lista de objetos do tipo TermTranslation das tradu��es a serem criadas ou atualizadas
	 * @return			Lista de objetos do tipo TermTranslation
	 * @throws			CKANException
	 */
	public List<TermTranslation> termTranslationUpdateMany(List<TermTranslation> terms) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/term_translation_update_many", gson.toJson(terms));
		TermTranslationResponseList r = loadClass(TermTranslationResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "termTranslationUpdateMany");
		
		return r.getResult();
	}

	/**
	 * Atualiza um vocabul�rio.
	 * 
	 * @param	vocabulary	Objeto do tipo Vocabulary com os dados do vocabul�rio a ser atualizado
	 * @return				Objeto do tipo Vocabulary
	 * @throws 				CKANException
	 */
	public Vocabulary vocabulary_update(Vocabulary vocabulary) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/vocabulary_update", gson.toJson(vocabulary));
		VocabularyResponse r = loadClass(VocabularyResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "vocabulary_update");
		
		return r.getResult();
	}

	/**
	 * Atualiza as roles de um usu�rio ou grupo para um objeto de dom�nio.
	 * 
	 * @param	userRole	Objeto do tipo RoleShow que cont�m o usu�rio que ter�o as roles atualizadas, o objeto de domin�o e as roles para o dado objeto
	 * @return				Objeto do tipo RoleShow
	 * @throws 				CKANException
	 */
	public RoleShow userRoleUpdate(RoleShow userRole) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/user_role_update", gson.toJson(userRole));
		RoleShowResponse r = loadClass(RoleShowResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userRoleUpdate");
		
		return r.getResult();
	}

	/**
	 * Atualiza em lote as roles de de usu�rios para objetos de dom�nio.
	 * 
	 * @param	userRole	Lista de objetos do tipo RoleShow que cont�m o usu�rio que ter�o as roles atualizadas, o objeto de domin�o e as roles para o dado objeto
	 * @return				Lista de objetos do tipo RoleShow
	 * @throws 				CKANException
	 */
	public List<RoleShow> userRoleBulkUpdate(List<RoleShow> userRole) throws CKANException {
		Gson gson = new Gson();
		String resposta = this.conexao.Post("/api/action/user_role_bulk_update", gson.toJson(userRole));
		RoleShowResponseList r = loadClass(RoleShowResponseList.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userRoleBulkUpdate");
		
		return r.getResult();
	}

	/**
	 * Marca todas as atividades do painel de controle do usu�rio como velhas.
	 * 
	 * @throws CKANException
	 */
	public void dashboardMarkActivitiesOld() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/dashboard_mark_activities_old", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "dashboardMarkActivitiesOld");
	}

	/**
	 * Envia todos os emails pendentes listas de atividades para os usu�rios.
	 * <p>Este m�todo requer a utiliza��o de uma apikey sysadmin.
	 * 
	 * @throws CKANException
	 */
	public void sendEmailNotifications() throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		String resposta = this.conexao.Post("/api/action/send_email_notifications", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "sendEmailNotifications");
	}

	/**
	 * Atualiza o propriet�rio de um dataset.
	 * 
	 * @param	id 				Id ou nome do dataset a ser atualizado
	 * @param	organization_id	Id ou nome da organiza��o
	 * @throws 					CKANException
	 */
	public void packageOwnerOrgUpdate(String id, String organization_id) throws CKANException {
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("organization_id", organization_id);

		String resposta = this.conexao.Post("/api/action/package_owner_org_update", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageOwnerOrgUpdate");
	}

	/**
	 * Marca uma lista de datasets como privados.
	 * 
	 * @param	org_id 		Id da organiza��o propriet�ria
	 * @param 	datasets	Lista de ids de datasets a serem marcados como privados
	 * @throws 				CKANException
	 */
	public void bulkUpdatePrivate(String org_id, List<String> datasets) throws CKANException {
		Gson gson = new Gson();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("org_id", org_id);
		parametros.put("datasets", datasets);

		String resposta = this.conexao.Post("/api/action/bulk_update_private", gson.toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "bulkUpdatePrivate");
	}

	/**
	 * Marca uma lista de datasets como p�blicos.
	 * 
	 * @param	org_id		Id da organiza��o propriet�ria
	 * @param 	datasets	Lista de ids de datasets a serem marcados como p�blicos
	 * @throws 				CKANException
	 */
	public void bulkUpdatePublic(String org_id, List<String> datasets) throws CKANException {
		Gson gson = new Gson();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("org_id", org_id);
		parametros.put("datasets", datasets);

		String resposta = this.conexao.Post("/api/action/bulk_update_public", gson.toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "bulkUpdatePublic");
	}

	/**
	 * Marca uma lista de datasets como apagados.
	 * 
	 * @param	org_id		Id da organiza��o propriet�ria
	 * @param	datasets	Lista de ids de datasets a serem marcados como apagados
	 * @throws 				CKANException
	 */
	public void bulkUpdateDelete(String org_id, List<String> datasets) throws CKANException {
		Gson gson = new Gson();
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		
		parametros.put("org_id", org_id);
		parametros.put("datasets", datasets);

		String resposta = this.conexao.Post("/api/action/bulk_update_delete", gson.toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "bulkUpdateDelete");
	}
	
	/**
	 * Remove um usu�rio.
	 * 
	 * @param	id	Id ou nome do usu�rio do usu�rio a ser removido
	 * @throws		CKANException
	 */
	public void userDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/user_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "userDelete");
	}
	
	/**
	 * Remove um dataset.
	 * 
	 * @param	id 	Id ou nome dataset a ser removido
	 * @throws 		CKANException
	 */
	public void packageDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/package_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageDelete");
	}

	/**
	 * Remove um recurso.
	 * 
	 * @param	id	Id do recurso a ser removido
	 * @throws		CKANException
	 */
	public void resourceDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/resource_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "resourceDelete");
	}
	
	/**
	 * Remove um relacionamento entre datasets.
	 * 
	 * @param	subject	Id ou nome do dataset que � referenciado no relacionamento
	 * @param	object	Id ou nome do dataset que � refer�ncia no relacionamento
	 * @param	type	Tipo de relacionamento
	 * @throws 			CKANException
	 */
	public void packageRelationshipDelete(String subject, String object, String type) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("subject", subject);
		parametros.put("object", object);
		parametros.put("type", type);
		
		String resposta = this.conexao.Post("/api/action/package_relationship_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipDelete");
	}
	
	/**
	 * Remove um item relacionado de um dataset.
	 * 
	 * @param	id	Id do item relacionado
	 * @throws		CKANException
	 */
	public void relatedDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/related_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "relatedDelete");
	}
	
	/**
	 * Remove um objeto (usu�rio, dataset ou grupo) de um grupo.
	 * 
	 * @param	id			Id do grupo
	 * @param	object		Id ou nome do objeto a ser removido
	 * @param	object_type	Tipo de objeto a ser removido
	 * 						<p>Exemplo: 'package', 'user'
	 * @throws				CKANException
	 */
	public void memberDelete(String id , String object, String object_type) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id );
		parametros.put("object", object);
		parametros.put("object_type", object_type);
		
		String resposta = this.conexao.Post("/api/action/member_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "packageRelationshipDelete");
	}
	
	/**
	 * Remove um item relacionado de um grupo.
	 * 
	 * @param	id	Id do grupo
	 * @throws		CKANException
	 */
	public void groupDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/group_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupDelete");
	}
	
	/**
	 * Remove um item relacionado de uma organiza��o.
	 * 
	 * @param	id	Id da organiza��o
	 * @throws		CKANException
	 */
	public void organizationDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/organization_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationDelete");
	}
	
	/**
	 * Expurga completamente um grupo do sistema.
	 * <p>ESTA OPERA��O N�O PODE SER DESFEITA.
	 * 
	 * @param	id	Id ou nome do grupo a ser expurgado
	 * @throws		CKANException
	 */
	public void groupPurge(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/group_purge", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupPurge");
	}
	
	/**
	 * Expurga completamente uma organiza��o do sistema.
	 * <p>ESTA OPERA��O N�O PODE SER DESFEITA.
	 * 
	 * @param	id	Id ou nome da organiza��o a ser expurgada
	 * @throws		CKANException
	 */
	public void organizationPurge(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/organization_purge", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationPurge");
	}
	
	/**
	 * Remove um status de tarefa.
	 * 
	 * @param	id	Id do status de tarefa a ser removido
	 * @throws		CKANException
	 */
	public void taskStatusDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/task_status_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "taskStatusDelete");
	}
	
	/**
	 * Remove um vocabul�rio de tags.
	 * 
	 * @param	id	Id do vocabul�rio
	 * @throws		CKANException
	 */
	public void vocabularyDelete(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/vocabulary_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "vocabularyDelete");
	}	
	
	/**
	 * Remove uma tag.
	 * 
	 * @param	id				Id ou nome da tag
	 * @param	vocabulary_id	Id ou nome do vocabul�rio ao qual a tag pertence
	 * 							<p>Opcional
	 * @throws					CKANException
	 */
	public void tagDelete(String id, String vocabulary_id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		if(vocabulary_id != null)
			parametros.put("vocabulary_id", vocabulary_id);
				
		String resposta = this.conexao.Post("/api/action/tag_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "tagDelete");
	}	
	
	
	/**
	 * Permite ao usu�rio da apikey em uso deixar de seguir um dado usu�rio.
	 *  
	 * @param	id	Id ou nome do usu�rio que n�o mais ser� seguido
	 * @throws		CKANException
	 */
	public void unfollowUser(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/unfollow_user", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "unfollowUser");
	}
	
	
	/**
	 * Permite ao usu�rio da apikey em uso deixar de seguir um dado dataset.
	 * 
	 * @param	id	Id ou nome do dataset que n�o mais ser� seguido
	 * @throws		CKANException
	 */
	public void unfollowDataset(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/unfollow_dataset", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "unfollowDataset");
	}
	
	/**
	 * Remove um usu�rio de um grupo.
	 * 
	 * @param	id			Id ou nome do grupo 
	 * @param	username	Id ou nome do usu�rio
	 * @throws				CKANException
	 */
	public void groupMemberDelete(String id, String username) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("username", username);
		
		String resposta = this.conexao.Post("/api/action/group_member_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "groupMemberDelete");
	}
	
	/**
	 * Remove um usu�rio de uma organiza��o.
	 * 
	 * @param	id			Id ou nome da organiza��o 
	 * @param	username	Id ou nome do usu�rio
	 * @throws 				CKANException
	 */
	public void organizationMemberDelete(String id, String username) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		parametros.put("username", username);
		
		String resposta = this.conexao.Post("/api/action/organization_member_delete", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "organizationMemberDelete");
	}
	
	/**
	 * Permite ao usu�rio da apikey em uso deixar de seguir um dado grupo.
	 * 
	 * @param	id	Id ou nome do grupo que n�o mais ser� seguido
	 * @throws 		CKANException
	 */
	public void unfollowGroup(String id) throws CKANException {		
		HashMap<String, String> parametros = new HashMap<String, String>();
		
		parametros.put("id", id);
		
		String resposta = this.conexao.Post("/api/action/unfollow_group", toJson(parametros));
		StringResponse r = loadClass(StringResponse.class, resposta);

		if (!r.getSuccess())
			controlarErro(resposta, "unfollowGroup");
	}
	
	/***
	 * Simplifica a convers�o dos par�metros no formato json.
	 * <p>M�todo privado.
	 * 
	 * @param valor
	 * @return
	 */
	private String toJson(HashMap<String, String> valor) {
		Gson gson = new Gson();
		return gson.toJson(valor);
	}

	private String toJson(List<String> valor) {
		Gson gson = new Gson();
		return gson.toJson(valor);
	}
}