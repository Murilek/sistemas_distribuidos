package org.example.repository.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class CandidatoCompetenciaIdEntity {


    private Long idCompetencia;

    private Long idCandidato;


}
