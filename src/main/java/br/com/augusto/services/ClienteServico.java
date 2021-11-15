package br.com.augusto.services;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.augusto.dto.ClienteAlterarRequestDTO;
import br.com.augusto.dto.ClienteConsultarRequestDTO;
import br.com.augusto.dto.ClienteConsultarResponseDTO;
import br.com.augusto.dto.ClienteIncluirRequestDTO;
import br.com.augusto.model.Cliente;
import br.com.augusto.repository.ClienteRepository;

@Service
public class ClienteServico {

	@Autowired
	ClienteRepository clienteRepository;
	

	@Autowired
	ObjectMapper objectMapper;
	


	@PostConstruct
	private void configureObjectMapper() {
		objectMapper.findAndRegisterModules();
	
	}

	public Cliente incluir(ClienteIncluirRequestDTO clienteDTO) {
		objectMapper.findAndRegisterModules();
		return clienteRepository.save(objectMapper.convertValue(clienteDTO,
				Cliente.class));
	}

	public void excluir(Integer id) {
		clienteRepository.findById(id).map(cliente -> {
			clienteRepository.delete(cliente);
			return cliente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

	}
	
	public Cliente alterar(ClienteAlterarRequestDTO clienteDTO) {
			
		return clienteRepository
        .findById(clienteDTO.getId())
        .map( clienteExistente -> {
        	Cliente cliente = objectMapper.convertValue(clienteDTO,Cliente.class);
            cliente.setId(clienteExistente.getId());
            clienteRepository.save(cliente);
            return clienteExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Cliente não encontrado") );
		
		
	}
	
	
	
	public Cliente alterarPatch(ClienteAlterarRequestDTO clienteDTO)  {

		return clienteRepository
		        .findById(clienteDTO.getId())
		        .map( clienteExistente -> {
		        
		        	objectMapper.setSerializationInclusion(Include.NON_NULL);
		        	Map<String, Object> campos = objectMapper.convertValue(clienteDTO, new TypeReference<Map<String, Object>>() {});
			    	 
		    	    campos.forEach((k, v) -> {
		    	        Field field = ReflectionUtils.findField(Cliente.class, k); 
		    	        field.setAccessible(true); 
			            ReflectionUtils.setField(field, clienteExistente, v); 
			        });

		            clienteRepository.save(clienteExistente);
		            return clienteExistente;
		        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
		            "Cliente não encontrado") );
				
	}
	

	public ClienteConsultarResponseDTO clienteEntityToDto(Cliente cliente) {
		return ClienteConsultarResponseDTO.builder().id(cliente.getId()).nome(cliente.getNome())
				.nomeMae(cliente.getNomeMae()).nomePai(cliente.getNomePai()).cpf(cliente.getCpf())
				.dtNascimento(cliente.getDtNascimento())
				.idade(Optional.ofNullable(cliente.getDtNascimento()).isPresent()
						? calcularIdade(cliente.getDtNascimento())
						: null)
				.build();
	}

	public Page<ClienteConsultarResponseDTO> consultar(ClienteConsultarRequestDTO filtro, Pageable page) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		
		Example example = Example.of(
				objectMapper.convertValue(Optional.ofNullable(filtro).isPresent() ? filtro : ClienteConsultarRequestDTO.builder().build(),
						Cliente.class), matcher);
		
		Page<Cliente> lista = clienteRepository.findAll(example, page);

		Page<ClienteConsultarResponseDTO> result = lista.map(entity -> {
			ClienteConsultarResponseDTO dto = clienteEntityToDto(entity);
			return dto;
		});
		return result;

	}

	private String calcularIdade(LocalDate dtNascimento) {
		Period period = Period.between(dtNascimento, LocalDate.now());
		return period.getYears() + " anos ," + period.getMonths() + " meses e " + period.getDays() + " dias";

	}
}
