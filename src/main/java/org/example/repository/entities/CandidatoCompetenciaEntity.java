package org.example.repository.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name= "candidatocompetencia")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CandidatoCompetenciaEntity {

//    @EmbeddedId
//    private CandidatoCompetenciaIdEntity candidatoCompetencia;

    @ManyToOne
    @Id
    @JoinColumn(name="idCandidato")
    @JsonBackReference
    private CandidatoEntity candidato;

    @ManyToOne
    @Id
    @JoinColumn(name="idCompetencia")
    private CompetenciaEntity competencia;

    @Column(name= "experiencia")
    private Integer experiencia;


}
