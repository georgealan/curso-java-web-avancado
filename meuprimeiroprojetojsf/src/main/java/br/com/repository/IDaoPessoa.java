package br.com.repository;

import br.com.entidades.Pessoa;

/*Esta é uma interface, aqui nós declaramos as assinaturas dos métodos*/

public interface IDaoPessoa {
	Pessoa consultarUsuario(String login, String senha);
}
