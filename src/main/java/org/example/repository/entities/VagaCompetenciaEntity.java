package org.example.repository.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


public class VagaCompetenciaEntity {

    @EmbeddedId
    private VagaCompetenciaIdEntity vagaCompetenciaId;

    @ManyToOne
    @MapsId("idVaga")
    @JoinColumn(name="idVaga")
    private VagaEntity vaga;

    @ManyToOne
    @MapsId("idCompetencia")
    @JoinColumn(name="idCompetencia")
    private CompetenciaEntity competencia;

    @Column(name= "Experiencia")
    private Integer experiencia;



}
