package org.example.repository.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@Getter
@Setter
@Table(name = "empresa")
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmpresa", nullable = false)
    private Integer idEmpresa;

    @Column(name = "razaoSocial", nullable = false, length = 14)
    private String razaoSocial;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "senha", nullable = false, length = 8)
    private String senha;

    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;

    @Column(name = "ramo", nullable = false, length = 255)
    private String ramo;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;
}
