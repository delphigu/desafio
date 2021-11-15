package br.com.augusto.dto;

import java.time.LocalDate;

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
public class ClienteConsultarRequestDTO {
	private Integer id;
    private String nome;
    private String cpf;
    private String nomeMae;
    private String nomePai;
    private LocalDate dtNascimento;

}
