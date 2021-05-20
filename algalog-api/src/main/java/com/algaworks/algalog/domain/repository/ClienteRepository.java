package com.algaworks.algalog.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algalog.domain.model.Cliente;

@Repository // determina que esse repositorio gerencia a entidade cliente
// então ela é responsavel por acessar os dados do banco e devolver para o cliente 
// uma resposta
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
//  metodo abaixo segue o padrao do jpa para consultas customizadas.
	// todo metodo deve começar com find para indicar uma consulta de busca
	//  depois é colocado o nome BY para desiginar qual campo deverá ser pesquisado
	//  o ultimo parametro é o tipo de informação que será buscada
	// essa informação deve ser um dos atributos definidos na classe de entidade
	// esa é uma pesquisa exata
	List<Cliente> findByNome(String nome);
	//Caso eu queira pesquisar por fragmentos de string ( like ) colocar o sufixo Containing
	List<Cliente> findByNomeContaining(String nome);
	
}
