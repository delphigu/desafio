package br.com.augusto.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteIncluirRequestDTO {
		@NotNull(message = "Nome obrigatório")
	    private String nome;
	    private String cpf;
	    private String nomeMae;
	    private String nomePai;
	    private LocalDate dtNascimento;
	
}
