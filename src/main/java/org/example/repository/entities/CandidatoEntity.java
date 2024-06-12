package org.example.repository.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "candidato")
@AllArgsConstructor
@NoArgsConstructor
public class CandidatoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCandidato", nullable = false)
    private Integer idCandidato;

    @Column(name = "nome", nullable = false, length = 30)
    private String nome;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "senha", nullable = false, length = 8)
    private String senha;

    @OneToMany(mappedBy = "candidato",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CandidatoCompetenciaEntity> competencias;


}
