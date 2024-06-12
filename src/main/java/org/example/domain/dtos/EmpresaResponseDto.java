package org.example.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EmpresaResponseDto {
    private String operacao;
    private Integer status;
    private String mensagem;
    private String token;
    private String senha;
    private String email;
    private String cnpj;
    private String razaoSocial;
    private String ramo;
    private String descricao;
}
