package model;

/*
 * Essa classe é a que cria um modelo do que existe no banco de dados, e ela
 * fica no pacote model de (modelo).
 * */

public class Userposjava {

	private Long id;
	private String nome;
	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Userposjava [id=" + id + ", nome=" + nome + ", email=" + email + "]";
	}
}
