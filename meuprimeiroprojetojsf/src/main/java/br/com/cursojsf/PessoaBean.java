package br.com.cursojsf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import br.com.dao.DaoGeneric;
import br.com.entidades.Pessoa;
import br.com.repository.IDaoPessoa;
import br.com.repository.IDaoPessoaImpl;

/*Esta classe serve para inserir os métodos que vão ativar os métodos da classe DaoGeneric, aqui nós instanciamos 
 * o Objeto da classe que queremos usar, no caso aqui é o objeto pessoa, inserimos aqui no pessoaBean as chamadas
 * dos métodos do DaoGeneric.*/

@ViewScoped // É um escopo de view onde vai ser exibido os dados salvos na tela, 
@ManagedBean(name = "pessoaBean") // <-- Annotation para declarar que a classe é um ManagedBean.
/*
 * A annotation pode receber um nome, por convenção é viavel declarar o nome com
 * o mesmo nome da classe só iniciando com letra minuscula.
 */
public class PessoaBean {

	private Pessoa pessoa = new Pessoa(); // Instanciando a classe Pessoa.
	private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>(); //Instanciando a classe DaoGeneric.
	private List<Pessoa> pessoas = new ArrayList<Pessoa>(); //Abaixo precisamos chamar o get desta variavel.
	private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl(); //A interface é igual a implementação.
	
	
	public String salvar() {
		/*Usamos o merge do método salvar para que o que foi salvo no banco de dados retorne para a tela.
		 * somente o que foi salvo no instante vai ficar visivel na tela e podemos atualizar os valores 
		 * ao alterar algum campo e clicar em salvar novamente.*/
		pessoa = daoGeneric.merge(pessoa);
		carregarPessoas();
		return "";
	}
	
	/*Método que vai criar um novo objeto pessoa após ter salvado no banco de dados, você vai poder 
	 * criar um novo cadastro.*/
	public String novo() {
		pessoa = new Pessoa();	
		return "";
	}
	
	/*Método que vai remover um objeto.*/
	public String remove() {
		daoGeneric.deletePorId(pessoa);
		pessoa = new Pessoa();
		carregarPessoas();
		return "";
	}
	
	/*Esta anottation serve para que sempre que abrir a tela e o manegedBean for instanciado, após ele ter sido
	criado na memoria ele vai carregar o método que esta anotado com o PostConstruct.*/
	@PostConstruct 
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}
	
	/*Getters e Setters das variáveis acima.*/
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	
	public String logar() {
		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());
		
		if (pessoaUser != null) {
			/*Se o usuario for diferente de null, quer dizer que encontrou o usuario.*/
			
			//Adicionar o usuario na sessão usuarioLogado.
			
			//Quando necessitamos de qualquer informação do ambiente de execução do jsf usamos o FacesContext.
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);
			
			return "primeirapagina.jsf";
		}
		return "index.jsf";
	}
	
	
	public boolean permitirAcesso(String acesso) {
		/*Puxamos a seção atual do usuario logado, usando o get. Recupera a pessoa logada.*/
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado"); //Captura o usuario logado.
		
		/*Retorna o atributo perfilUser do usuario que está logado e compara com o parametro do método e se ele for 
		 * igual ao valor do perfilUser do usuario vai retornar true. Deste modo podemos usar o atributo rendered no jsf 
		 * para esconder campos da view dependendo do usuario.*/
		return pessoaUser.getPerfilUser().equals(acesso);
		
	}
	
	
}
