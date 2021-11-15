package br.com.augusto.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ClienteConsultarResponseDTO {
	private Integer id;
    private String nome;
    private String cpf;
    private String nomeMae;
    private String nomePai;
    private LocalDate dtNascimento;
    private String idade;

}
