package com.algaworks.algalog.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.algaworks.algalog.domain.ValidationGroups;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

//lombok gerencia a criacao do equals e hashcode
// onlyexplicity = true ativa esse monitoramento apenas nas 
// colunas indicadas com o a tag equalsandhashcode.include;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name="cliente")
public class Cliente {
	
	@NotNull(groups = ValidationGroups.ClientId.class)
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	@NotBlank
	@Size(max = 60)
	private String nome;
	
	@NotBlank
	@Email
	@Size(max = 255)
	private String email;
	
	@Column(name = "fone")
	@NotBlank
	@Size(max = 20)
	private String telefone;
	
	

}
