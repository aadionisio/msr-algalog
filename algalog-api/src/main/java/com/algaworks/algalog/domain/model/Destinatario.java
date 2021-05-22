package com.algaworks.algalog.domain.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable // indica que essa classe pode ser usada como embeded em alguma classe
public class Destinatario {
	
	@Column(name = "destinatario_nome")// é feito para fazer distinção dessa coluna quanto ao das classes que as utilizar
	private String nome;
	@Column(name = "destinatario_logradouro")
	private String logradouro;
	@Column(name = "destinatario_numero")
	private String numero;
	@Column(name = "destinatario_complemento")
	private String complemento;
	@Column(name = "destinatario_bairro")
	private String bairro;
	
}
