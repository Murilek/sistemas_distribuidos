package org.example.domain.dtos;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.repository.entities.CompetenciaEntity;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class VagaResponseDto {
    private String mensagem;
    private String operacao;
    private Integer status;
    private Integer idVaga;
    private String token;
    private String nome;
    private String estado;
    private Double faixaSalarial;
    private String descricao;
    private List<String> competencias;
    private List<VagaDto> vagas;
}
