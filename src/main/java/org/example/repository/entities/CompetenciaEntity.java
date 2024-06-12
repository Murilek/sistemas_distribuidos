package org.example.repository.entities;
import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "competencia")
@AllArgsConstructor
@NoArgsConstructor

public class CompetenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompetencia", nullable = false)
    private Integer idCompetencia;

    @Column(name = "nomeCompetencia", nullable = false)
    private String nomeCompetencia;



}
