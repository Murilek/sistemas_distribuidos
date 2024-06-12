package org.example.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "vaga")
@AllArgsConstructor
@NoArgsConstructor
public class VagaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVaga")
    private Integer idVaga;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(name = "faixaSalarial", nullable = false)
    private Double faixaSalarial;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private String estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vagacompetencia",
            joinColumns = @JoinColumn(name = "idVaga"),
            inverseJoinColumns = @JoinColumn(name = "idCompetencia")
    )
    private List<CompetenciaEntity> competencias = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idEmpresa", referencedColumnName = "idEmpresa", nullable = false)
    private EmpresaEntity empresa;
}
