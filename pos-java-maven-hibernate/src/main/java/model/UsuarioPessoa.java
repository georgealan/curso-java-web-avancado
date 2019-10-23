package model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/*Uma alternativa no desenvolvimento de querys é que podemos criar querys dentro das classes de modelo a 
 * inserção delas na classe deve ser feita acima como visto aqui, a sintaxe é essa. Com elas inseridas 
 * aqui você vai poder usar livremente no programa chamando ela pelo nome. Mas é recomendado fazer isso 
 * somente para algumas rotinas simples, não é recomendado encher a classe de NamedQuery.*/
@NamedQueries({
	
	@NamedQuery(name = "UsuarioPessoa.todos", query = "select u from UsuarioPessoa u"),
	@NamedQuery(name = "UsuarioPessoa.buscarPorNome", query = "select u from UsuarioPessoa u where u.nome = :nome")
	
})

@Entity /*
		 * A anottation Entity é necessária para que o hibernate consiga interpretar e
		 * criar a tabela a partir dos dados desta classe.
		 */
public class UsuarioPessoa {
	/*As annotations Id e GeneratedValue são obrigatórias para que o hobernate possa gerar o ID de forma automatica.*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	private String sobrenome;
	private String email;
	private String login;
	private String senha;
	private int idade;
	
	@OneToMany(mappedBy = "usuarioPessoa", fetch = FetchType.EAGER)
	private List<TelefoneUser> telefoneUsers;
	
	public List<TelefoneUser> getTelefoneUsers() {
		return telefoneUsers;
	}

	public void setTelefoneUsers(List<TelefoneUser> telefoneUsers) {
		this.telefoneUsers = telefoneUsers;
	}

	public int getIdade() {
		return idade;
	}
	
	public void setIdade(int idade) {
		this.idade = idade;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	/*O método toString é usado para ordenar a apresentação de valores dos atributos de uma classe em forma de
	 * String, ele concatena todos os atributos e apresenta todos em forma de String.*/
	@Override
	public String toString() {
		return "UsuarioPessoa [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + ", email=" + email
				+ ", login=" + login + ", senha=" + senha + ", idade=" + idade + "]";
	}
	
}
