package com.algaworks.algalog.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@ControllerAdvice //indica que essa classe fara o tratamento de advertencias(excessoes) levantadas pelos controladores
// extende uma classe que ja esta preparada para tratar as excessoes existentes EsponseEntityExceptionHandler
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	// interface para resolver mensagens
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<Problema.Campo> campos = new ArrayList<>();
		
		//o laço abaixo percorre todos os erros levantados atraves do metodo getallerros
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			
			// faz um cast usando o tipo FieldError sendo possivel acessar o campo que levantou a excessao
			// como sabemos que o erro se dara num campo é utilizado esse cast especifico
			// quando nao se sabe se faz uma verificação do tipo instancesof para descobrir o tipo de erro
			String nome = ((FieldError) error).getField(); 
			                  //interface para resolução de mensagens. passa o erro e a localidade (locale)atual 
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			campos.add(new Problema.Campo(nome,mensagem));
		}
		
		Problema problema = new Problema();
		problema.setStatus(status.value());
		problema.setDataHora(LocalDateTime.now());
		problema.setTitulo("Um ou mais campos estão com preenchimento inválido. faça o preenchimento correto e tente novamente");
		problema.setCampos(campos);
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
}
