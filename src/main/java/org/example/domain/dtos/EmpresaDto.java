package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class EmpresaDto {
    private String email;
    private String senha;
    private String token;
    private String cnpj;
    private String razaoSocial;
    private String ramo;
    private String descricao;
}
