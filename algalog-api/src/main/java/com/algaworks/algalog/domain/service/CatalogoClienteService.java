package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algalog.domain.exception.NegocioException;
import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service // indica que essa classe sera um servico do spring e que ela realizara processos de negocios
public class CatalogoClienteService {
   private ClienteRepository clienteRepository;
   
   public Cliente buscar(Long clienteId) {
		return clienteRepository.findById(clienteId)
				//se encontrar o cliente coloca ele no objeto cliente. senao (orelse) levanta uma excessao
				.orElseThrow(() -> new NegocioException("Cliente enviado, não existe na base de dados. Verifique o codigo enviado."));
		
   }
   
   
   
   @Transactional // anotação que diz que esse metodo só pode ser executado durante uma transação com o banco de dados
   public Cliente salvar(Cliente cliente) {
	   // todo o codigo que trata de regra de negocio deve ser colocado aqui
	   // validar cpf por exemplo
	   
	   boolean emailEmUso = clienteRepository.findByEmail(cliente.getEmail())
			   .stream() // estudar stream
			   .anyMatch(clienteExistente -> !clienteExistente.equals(cliente));
	   
	   if(emailEmUso) {
		   throw new NegocioException("Já existe um cliente cadastrado em este e-mail.");
	   }
	   
	   
	   return clienteRepository.save(cliente);
   }
   
   @Transactional
   public void excluir (Long clienteId) {
	   clienteRepository.deleteById(clienteId);
   }
}
