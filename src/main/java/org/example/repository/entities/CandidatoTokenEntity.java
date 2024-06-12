package org.example.repository.entities;


import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "tokencandidato")
@AllArgsConstructor
@NoArgsConstructor

public class CandidatoTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idToken", nullable = false)
    private int idToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCandidato", nullable = false)
    private CandidatoEntity candidato;

    @Column(name = "token", nullable = false, length = 36)
    private String token;

}
