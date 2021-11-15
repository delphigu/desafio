package br.com.augusto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.augusto.dto.ClienteAlterarRequestDTO;
import br.com.augusto.dto.ClienteIncluirRequestDTO;
import br.com.augusto.services.ClienteServico;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class DesafioApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ClienteServico clienteServico;

	@Test
	@Order(1)
	public void testInsereCliente() throws JsonProcessingException, Exception {
		ClienteIncluirRequestDTO cliente = ClienteIncluirRequestDTO.builder().nome("augusto").cpf("1515511551").build();
		mockMvc.perform(
				post("/incluir").contentType("application/json").content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isCreated());

	}

	@Test
	@Order(2)
	public void testAlteraCliente() throws JsonProcessingException, Exception {
		ClienteAlterarRequestDTO cliente = ClienteAlterarRequestDTO.builder().id(1).nome("augusto rocha")
				.cpf("15155151").build();
		mockMvc.perform(
				put("/alterar").contentType("application/json").content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isAccepted());

	}
	
	@Test
	@Order(3)
	public void testAlteraPatchCliente() throws JsonProcessingException, Exception {
		ClienteAlterarRequestDTO cliente = ClienteAlterarRequestDTO.builder().id(1).nome("augusto alterado")
				.cpf("15155151").build();
		mockMvc.perform(
				patch("/alterarPatch").contentType("application/json").content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isAccepted());

	}

	@Test
	@Order(4)
	public void testConsultarCliente() throws JsonProcessingException, Exception {

		this.mockMvc.perform(get("/consultar").param("page", "0").param("size", "1").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());

	}

	@Test
	@Order(5)
	public void testExcluirCliente() throws JsonProcessingException, Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/excluir/{id}", "1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());

	}

}
