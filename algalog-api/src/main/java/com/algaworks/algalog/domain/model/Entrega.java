package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algalog.domain.ValidationGroups;
import com.algaworks.algalog.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.istack.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {
	
	 @EqualsAndHashCode.Include
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
	 
	 @Valid // implementa as validacoes na o objeto referenciado tambem em cascata
	 @ConvertGroup(from = Default.class, to = ValidationGroups.ClientId.class) // converte o grupo de validacao padrao de default para o validationgrup que foi criado.
	 @NotNull
	 @ManyToOne
	 @JoinColumn(name = "cliente_id") //nome da coluna da tabela de cliente que é a pk
     private Cliente cliente;
	 
	 @Valid
	 @NotNull
     @Embedded // aponta para um classe externa para buscar as informações complementares. poderia fazer uma tabela ou não. 
	 private Destinatario destinatario;
	 
	 @OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) //cascade all. vai fazer os dados persistirem no banco de dados pois o recurso sera totalmente mapeado ao ser instanciado
	 private List<Ocorrencia> ocorrencias = new ArrayList<>();
     
     
     @NotNull
     private BigDecimal taxa;
     
     @JsonProperty(access = Access.READ_ONLY)
     @Enumerated(EnumType.STRING) // indica o tipo de informação que será registrada nesse campo. ordinal registra o indice , string registra o conteudo
     private StatusEntrega status;
     
     @JsonProperty(access = Access.READ_ONLY)
     private OffsetDateTime dataPedido;
     
     @JsonProperty(access = Access.READ_ONLY) // mesmo preenchendo no corpo da requisição . essa informação sera ignorada e nao sera enviada ao banco de dados
     private OffsetDateTime dataFinalizacao;

	public Ocorrencia adicionarOcorrenciaDescricao(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);

		//adicionando a ocorrencia que acabamos de instanciar
		this.getOcorrencias().add(ocorrencia);
		
		return ocorrencia; 
	}

	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("Entrega com status diferente de pendente. Não sera possivel finalizar.");
			
		}
		
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
		
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
     
}
