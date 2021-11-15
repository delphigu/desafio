package br.com.augusto.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ClienteAlterarRequestDTO {
		@NotNull(message = "id obrigatório")
		private Integer id;
		@NotNull(message = "Nome obrigatório")
		private String nome;
		private String cpf;
	    private String nomeMae;
	    private String nomePai;
	    private LocalDate dtNascimento;
	
}
