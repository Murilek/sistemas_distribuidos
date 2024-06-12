package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CandidatoCompetenciaDto {


    private String competencia;

    private Integer experiencia;

}
