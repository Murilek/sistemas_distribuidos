package org.example.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "tokenempresa")
@AllArgsConstructor
@NoArgsConstructor

public class EmpresaTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idToken", nullable = false)
    private int idToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmpresa", nullable = false)
    private EmpresaEntity empresa;

    @Column(name = "token", nullable = false, length = 36)
    private String token;

}
