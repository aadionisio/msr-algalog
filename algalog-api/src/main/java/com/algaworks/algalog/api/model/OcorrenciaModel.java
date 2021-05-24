package com.algaworks.algalog.api.model;
// definindo o modelo da resposta (json de saida)

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaModel {

	private Long id;
	private String descricao;
	private OffsetDateTime dataRegistro;
	
	
}
