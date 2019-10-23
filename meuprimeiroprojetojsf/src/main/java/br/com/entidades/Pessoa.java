package br.com.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity // Setamos que seja uma classe modelo de banco de dados, que o hibernate vai
		// manipular e criar tabelas e tudo mais automaticamente.
public class Pessoa implements Serializable { // Implementando a serialização.

	private static final long serialVersionUID = 1L;

	@Id // Setando que esse atributo vai ser o primary key
	@GeneratedValue(strategy = GenerationType.AUTO) // Setando a geração de valores automaticamente do id.
	private Long id;

	private String nome;
	private String sobrenome;
	private Integer idade;
	private String sexo;
	private String[] frameworks;
	private Boolean ativo;
	private String login;
	private String senha;
	private String perfilUser;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	public Pessoa() {
		/* Iniciação de Classe manual. */
	}
	
	public String getPerfilUser() {
		return perfilUser;
	}
	
	public void setPerfilUser(String perfilUser) {
		this.perfilUser = perfilUser;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/*
	 * Ao trabalhar com frameworks ORM é necessário usar tipos de variaveis wrappers
	 * e não os tipos primitivos para evitar problemas.
	 */
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String[] getFrameworks() {
		return frameworks;
	}

	public void setFrameworks(String[] frameworks) {
		this.frameworks = frameworks;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * Usamos o equals and hashcode no atributo Id que vai receber serialização
	 * automatica para ajudar o hibernate.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
