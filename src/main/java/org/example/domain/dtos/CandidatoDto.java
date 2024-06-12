package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.example.repository.entities.CompetenciaEntity;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class CandidatoDto {
    private String email;
    private String senha;
    private String nome;
    private String token;
    private List<CandidatoCompetenciaDto> competenciaExperiencia;
    private String operacao;
}
