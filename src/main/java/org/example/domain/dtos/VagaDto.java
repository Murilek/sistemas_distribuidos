package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VagaDto {

    private String operacao;
    private int idVaga;
    private String nome;
    private Double faixaSalarial;
    private String descricao;
    private String email;
    private String estado;
    private List<String> competencias;

}
