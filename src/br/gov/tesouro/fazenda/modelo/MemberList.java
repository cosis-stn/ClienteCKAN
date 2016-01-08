package br.gov.tesouro.fazenda.modelo;

/***
 * Classe criada para simplificar o tratamento do retorno do método MemberList. 
 * Esse método não retorna uma entidade típica, somente uma lista simples de ide, type e capacity
 * 
 * @author fabio.s.barbosa
 *
 */
public class MemberList {
	private String id;
	private String object_type;
	private String capacity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

}
