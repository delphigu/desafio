package br.com.augusto.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.augusto.dto.ClienteAlterarRequestDTO;
import br.com.augusto.dto.ClienteConsultarRequestDTO;
import br.com.augusto.dto.ClienteConsultarResponseDTO;
import br.com.augusto.dto.ClienteIncluirRequestDTO;
import br.com.augusto.model.Cliente;
import br.com.augusto.services.ClienteServico;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Cadastro de Clientes")
public class ClienteController {

	@Autowired
	ClienteServico clienteServico;

	@ApiOperation(value = "Endpoint para incluir Clientes")
	@PostMapping("/incluir")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> incluir(@RequestBody @Valid ClienteIncluirRequestDTO clienteDTO) {
		return new ResponseEntity<>(clienteServico.incluir(clienteDTO), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Endpoint para alterar Clientes")
	@PutMapping("/alterar")
	public ResponseEntity<Cliente> alterar(@RequestBody @Valid ClienteAlterarRequestDTO clienteDTO) throws JsonMappingException, JsonProcessingException {
		return new ResponseEntity<>(clienteServico.alterar(clienteDTO), HttpStatus.ACCEPTED);
	}
	
	@ApiOperation(value = "Endpoint para alterar Clientes (partes)")
	@PatchMapping("/alterarPatch")
	public ResponseEntity<Cliente> alterarPatch(@RequestBody @Valid ClienteAlterarRequestDTO clienteDTO) {
		return new ResponseEntity<>(clienteServico.alterarPatch(clienteDTO), HttpStatus.ACCEPTED);
	}

	
	
	@ApiOperation(value = "Endpoint para excluir Clientes")
	@DeleteMapping("/excluir/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir( @PathVariable Integer id ){
	 	clienteServico.excluir(id);
	
	}
	
	@ApiOperation(value = "Endpoint para consultar Clientes")
	@GetMapping("/consultar")
	public Page<ClienteConsultarResponseDTO> consultar(@RequestBody(required = false) ClienteConsultarRequestDTO clienteConsultarRequestDTO,
			  @RequestParam(defaultValue = "0") int page,
		        @RequestParam(defaultValue = "10") int size) {
		  Pageable pagina = PageRequest.of(page,size);
		  return clienteServico.consultar(clienteConsultarRequestDTO, pagina);
	}
	


	

}
