package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class VagaRequestDto {

    private String operacao;
    private String nome;
    private Double faixaSalarial;
    private String descricao;
    private String email;
    private String estado;
    private String token;
    private List<String> competencias;

}
