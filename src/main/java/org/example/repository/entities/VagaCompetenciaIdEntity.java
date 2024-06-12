package org.example.repository.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Embeddable
public class VagaCompetenciaIdEntity {


    private Long idCompetencia;

    private Long idVaga;


}
