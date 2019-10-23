package beans;

public class BeanProduto {
	private Long id;
	private String nome;
	private double quantidade;
	private double valor;
	
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
	public double getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	/*Responsavel por converter o ponto para virgula no campo de inserir valor de dinheiro
	 *com isso o valor não vai mudar mais quando clicar em alterar na tela de alterar o cadastro de produtos
	 *no campo valor. Para chamar ele pela tag de expressão do JSP lá no campo de input a gente chama ele 
	 *assim: valorEmTexto sem o get e com a primeira letra em minuscula para que ele tenha a referencia de um 
	 *atributo. 
	 * */
	public String getValorEmTexto() {
		return Double.toString(valor).replace('.', ',');
	}
	
	public String getQuantidadeEmTexto() {
		return Double.toString(quantidade).replace('.', ',');
	}
}
