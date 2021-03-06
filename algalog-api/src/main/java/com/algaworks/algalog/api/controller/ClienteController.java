package com.algaworks.algalog.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

//@AllArgsConstructor // cria toda a parte de construtores caso nao existam.
// ele  substituiir o autowired. 
@RestController
@RequestMapping("/clientes") // cria uma baseurl
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CatalogoClienteService catalogoClienteService;
	
	@GetMapping
	public List<Cliente>listar() {
		
		return clienteRepository.findAll();
		
		
//		return manager.createQuery("from Cliente", Cliente.class)
//				.getResultList();
		
		
//		var cliente1 = new Cliente();
//		
//		cliente1.setId(1L);
//		cliente1.setNome("João2");
//		cliente1.setTelefone("35 35356581");
//		cliente1.setEmail("joaodastretas@portalis.com");
//		
//		var cliente2 = new Cliente();
//		
//		cliente2.setId(2L);
//		cliente2.setNome("Maria2");
//		cliente2.setTelefone("45 366556581");
//		cliente2.setEmail("jmariadasastretas@portalis.com");		
//		
//		return Arrays.asList(cliente1, cliente2); 
				
	}		
	
	@GetMapping("/{clienteId}")
	// pathvariable vincula o parametro clienteid que esta na rota
	// com o parametro declarado na função
	// a função retorna o objeto do tipo cliente
	// response entity dá a possibilidade de manupular o retorno dos status das requisições
	public ResponseEntity<Cliente> buscarPorCodigo(@PathVariable Long clienteId) {

		return clienteRepository.findById(clienteId)
			//	.map(cliente -> ResponseEntity.ok(cliente))
				.map(ResponseEntity::ok) // recurso method reference. referencia o metodo ok do Response entity. essa linha tem a mesma funcionalidade da linha acima
				.orElse(ResponseEntity.notFound().build());
		
		//		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
//		// is present valida se há conteudo dentro de cliente
//		if (cliente.isPresent()) {
//			return ResponseEntity.ok(cliente.get());
//		}
//		
//		// caso nao encontre nada. retorna codigo 404 na resposta
//		return ResponseEntity.notFound().build();		
		// se o optional retornar vazio. ele retorna o que definirmos na segunda opção
		// nao é uma boa pratica . pois sempre retorna 200 na resposta mesmo que sem conteudo
//		return cliente.orElse(null);
		

	}
	
	//@RequestBody //indica que os dados virão no corpo da requisição e ele fara a transformação para 
	// um objeto java
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)// define um retorno padrao em caso de sucesso
	public Cliente adicionarCliente (@Valid @RequestBody Cliente cliente) {
		// chama o metodo save que salva os dados no banco e retorna o registro incluido
		//return clienteRepository.save(cliente);
		
		return catalogoClienteService.salvar(cliente);
		
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> atualizarCliente(@PathVariable Long clienteId, 
		@Valid	@RequestBody Cliente cliente){
		
		// verificando se o cliente existe
		if (!clienteRepository.existsById(clienteId) ) {
			return ResponseEntity.notFound().build();
		}
		// ao informar o id antes de chamar o metodo save ele vai entender que deve 
		// fazer o proceeso de atualização e não de inclusão
		cliente.setId(clienteId);
		
		//cliente = clienteRepository.save(cliente);
		
		cliente = catalogoClienteService.salvar(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> removerCliente(@PathVariable Long clienteId){
		// verificando se o cliente existe
		if (!clienteRepository.existsById(clienteId) ) {
			return ResponseEntity.notFound().build();
		}
		
		//clienteRepository.deleteById(clienteId);
		
		catalogoClienteService.excluir(clienteId);
		
		return ResponseEntity.noContent().build();
		
	}

}
