package org.example.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.*;
import org.example.repository.dao.EmpresaDao;
import org.example.repository.dao.EmpresaTokenDao;
import org.example.repository.dao.VagaDao;
import org.example.repository.entities.CompetenciaEntity;
import org.example.repository.entities.EmpresaEntity;
import org.example.repository.entities.EmpresaTokenEntity;
import org.example.repository.entities.VagaEntity;

import java.util.*;
import java.util.stream.Collectors;

public class EmpresaUseCase {
    private ObjectMapper mapper = new ObjectMapper();
    private EmpresaDao empresaDao = new EmpresaDao();
    private EmpresaTokenDao empresaTokenDao = new EmpresaTokenDao();
    private VagaDao vagaDao = new VagaDao();

    public String deletar(EmpresaDto empresa) {
        try {
            var empresaEntity = empresaDao.findByEmail(empresa.getEmail());
            if(empresaTokenDao.isTokenValid(empresa.getToken())){

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarEmpresa")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            if (empresaEntity != null) {
                var empresaTokenEntity = empresaTokenDao.findByEmpresa(empresaEntity);
                if (empresaTokenEntity != null) {
                    empresaTokenDao.delete(empresaTokenEntity);
                }
                empresaDao.delete(empresaEntity);

                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("apagarEmpresa")
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("apagarEmpresa")
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String atualizar(EmpresaDto empresa) throws JsonProcessingException {
        try {
            var empresaEntity = empresaDao.findByEmail(empresa.getEmail());
            if(empresaTokenDao.isTokenValid(empresa.getToken())){

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("atualizarEmpresa")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            empresaEntity.setRazaoSocial(empresa.getRazaoSocial());
            empresaEntity.setSenha(empresa.getSenha());
            empresaEntity.setCnpj(empresa.getCnpj());
            empresaEntity.setRamo(empresa.getRamo());
            empresaEntity.setDescricao(empresa.getDescricao());


            empresaDao.save(empresaEntity);

            return mapper.writeValueAsString(EmpresaResponseDto.builder()
                    .operacao("atualizarEmpresa")
                    .status(201).build());

        } catch (Exception e) {
            return mapper.writeValueAsString(EmpresaResponseDto.builder()
                    .operacao("atualizarEmpresa")
                    .mensagem("E-mail não encontrado")
                    .status(404).build());
        }
    }

    public String buscar(EmpresaDto empresa) {
        try {
            var empresaEntity = empresaDao.findByEmail(empresa.getEmail());
            if(empresaTokenDao.isTokenValid(empresa.getToken())){
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("visualizarEmpresa")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            if (empresaEntity != null) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("visualizarEmpresa")
                        .cnpj(empresaEntity.getCnpj())
                        .ramo(empresaEntity.getRamo())
                        .razaoSocial(empresaEntity.getRazaoSocial())
                        .descricao(empresaEntity.getDescricao())
                        .senha(empresaEntity.getSenha())
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("visualizarEmpresa")
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public String listar(EmpresaDto empresa) {
        try {
            // Encontrar a empresa pelo email
            var empresaEntity = empresaDao.findByEmail(empresa.getEmail());

            // Verificar se o token é válido
            if (empresaTokenDao.isTokenValid(empresa.getToken())) {
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("listarVagas")
                        .mensagem("Token inválido")
                        .status(404)
                        .build());
            }

            if(empresaEntity != null){

                List<VagaEntity> vagas = vagaDao.findByEmpresaId(empresaEntity.getIdEmpresa());
                List<VagaDto> listaVagas = new ArrayList<>();
                for (VagaEntity vaga : vagas) {
                    VagaDto vagaDto = new VagaDto();
                    vagaDto.setIdVaga(vaga.getIdVaga());
                    vagaDto.setNome(vaga.getNome());
                    listaVagas.add(vagaDto);
                }


                // Criar e retornar o JSON de resposta
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("listarVagas")
                        .vagas(listaVagas)
                        .status(201)
                        .build());
            }else{

                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("listarVagas")
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String cadastrar(EmpresaDto empresa) throws JsonProcessingException {
        try {
            var novaEmpresa = EmpresaEntity.builder()
                    .email(empresa.getEmail())
                    .senha(empresa.getSenha())
                    .cnpj(empresa.getCnpj())
                    .razaoSocial(empresa.getRazaoSocial())
                    .descricao(empresa.getDescricao())
                    .ramo(empresa.getRamo())
                    .build();

            String validEmail = novaEmpresa.getEmail();
            String validSenha = novaEmpresa.getSenha();
            String validRazaoSocial = novaEmpresa.getRazaoSocial();
            String ValidCnpj = novaEmpresa.getCnpj();

            if (ValidCnpj.length() != 14){
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("CNPJ deve conter 14 dígitos")
                        .build());
            }
            
            if (validRazaoSocial == null || validRazaoSocial.isEmpty()) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("Campo nome vazio")
                        .build());
            }
            if (validRazaoSocial.length() < 3 || validRazaoSocial.length() > 30) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("Nome muito grande ou pequeno")
                        .build());
            }
            if (validSenha == null || validSenha.length() < 3 || validSenha.length() > 8) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("A senha deve ter entre 3 e 8 caracteres")
                        .build());
            }

            if (validEmail == null || validEmail.isEmpty()) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("Campo email vazio")
                        .build());
            }

            if (!validEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(404)
                        .mensagem("Email invalido")
                        .build());
            }

            if (empresaDao.isEmailAlreadyExists(validEmail)) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("cadastrarEmpresa")
                        .status(402)
                        .mensagem("Email já existente")
                        .build());
            }
            empresaDao.save(novaEmpresa);

            var tokenEntity = empresaTokenDao.findByEmpresa(novaEmpresa);

            if (tokenEntity == null) {
                var novoToken = EmpresaTokenEntity.builder()
                        .empresa(novaEmpresa)
                        .token(UUID.randomUUID().toString())
                        .build();
                tokenEntity = novoToken;
                empresaTokenDao.save(novoToken);
            }

            return mapper.writeValueAsString(EmpresaResponseDto.builder()
                    .operacao("cadastrarEmpresa")
                    .token(tokenEntity.getToken())
                    .status(201).build());

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String logar(EmpresaDto empresa) {
        try {
            var empresaEntity = empresaDao.findByEmailAndPassword(empresa.getEmail(), empresa.getSenha());

            if (empresaEntity != null) {
                var tokenEntity = empresaTokenDao.findByEmpresa(empresaEntity);

                if (tokenEntity == null) {
                    var novoToken = EmpresaTokenEntity.builder()
                            .empresa(empresaEntity)
                            .token(UUID.randomUUID().toString())
                            .build();
                    tokenEntity = novoToken;
                    empresaTokenDao.save(novoToken);
                }

                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("loginEmpresa")
                        .token(tokenEntity.getToken())
                        .status(200).build());
            } else {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("loginEmpresa")
                        .mensagem("Login ou senha incorretos")
                        .status(401).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String logout(EmpresaDto empresa) {
        try {
            if ((empresa.getToken() == null) || (empresa.getToken().length() != 36)) {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("logout")
                        .mensagem("E-mail invalido")
                        .status(404).build());
            }
            var empresaTokenEntity = empresaTokenDao.findByToken(empresa.getToken());

            if (empresaTokenEntity != null) {
                empresaTokenDao.delete(empresaTokenEntity);

                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("logout")
                        .status(204).build());
            } else {
                return mapper.writeValueAsString(EmpresaResponseDto.builder()
                        .operacao("logout")
                        .mensagem("Token não encontrado")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
