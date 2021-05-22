package com.algaworks.algalog.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //componente spring com oobjetivo de configuração
public class ModelMapperConfig {
	
	@Bean //indica que a classe abaixo será usada para configuração de bins do spring
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
