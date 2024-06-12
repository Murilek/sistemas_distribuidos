package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.repository.entities.CandidatoCompetenciaEntity;
import org.json.JSONArray;

import java.util.List;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class CandidatoResponseDto {
    private String operacao;
    private Integer status;
    private String mensagem;
    private String token;
    private String nome;
    private String senha;
    private String email;
    private List<CompetenciaExperienciaDto> competenciaExperiencia;
}
