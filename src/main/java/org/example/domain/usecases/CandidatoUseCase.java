package org.example.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.CandidatoDto;
import org.example.domain.dtos.CandidatoResponseDto;
import org.example.repository.dao.CandidatoDao;
import org.example.repository.dao.CandidatoTokenDao;
import org.example.repository.dao.CompetenciaDao;
import org.example.repository.entities.CandidatoCompetenciaEntity;
import org.example.repository.entities.CandidatoCompetenciaIdEntity;
import org.example.repository.entities.CandidatoEntity;
import org.example.repository.entities.CandidatoTokenEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CandidatoUseCase {
    private ObjectMapper mapper = new ObjectMapper();
    private CandidatoDao candidatoDao =  new CandidatoDao();
    private CandidatoTokenDao candidatoTokenDao = new CandidatoTokenDao();
    private CompetenciaDao competenciaDao =  new CompetenciaDao();
    public String deletar (CandidatoDto candidato) {
        try {
            var candidatoEntity = candidatoDao.findByEmail(candidato.getEmail());
            if(candidatoTokenDao.isTokenValid(candidato.getToken())){

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarCandidato")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            if(candidatoEntity != null){
                var candidatoTokenEntity = candidatoTokenDao.findByCandidato(candidatoEntity);
                if(candidatoTokenEntity != null){
                    candidatoTokenDao.delete(candidatoTokenEntity);
                }
                candidatoDao.delete(candidatoEntity);

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarCandidato")
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarCandidato")
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }

        } catch (Exception e){
            throw new RuntimeException();
        }
    }
    public String atualizar2(CandidatoDto candidato) throws JsonProcessingException {

        try {
            var candidatoEntity = candidatoDao.findByEmail(candidato.getEmail());

            candidatoEntity.setNome(candidato.getNome());
            candidatoEntity.setSenha(candidato.getSenha());

            candidatoDao.save(candidatoEntity);

            return mapper.writeValueAsString(CandidatoResponseDto.builder()
                    .operacao("atualizarCandidato")
                    .status(201).build());

        } catch (Exception e){
            return mapper.writeValueAsString(CandidatoResponseDto.builder()
                    .operacao("atualizarCandidato")
                    .mensagem("E-mail não encontrado")
                    .status(404).build());

        }
    }
    public String atualizar(CandidatoDto candidato) throws JsonProcessingException {

        try {
            if(candidatoTokenDao.isTokenValid(candidato.getToken())){

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("atualizarCandidato")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            var candidatoEntity = candidatoDao.findByEmail(candidato.getEmail());
            List<CandidatoCompetenciaEntity> competencias = new ArrayList<>();
            if(candidato.getNome() != null) {
                candidatoEntity.setNome(candidato.getNome());
            }
            if(candidato.getSenha() != null) {
                candidatoEntity.setSenha(candidato.getSenha());
            }

            candidato.getCompetenciaExperiencia().forEach(comp->{
                var competenciaEntity = competenciaDao.findByName(comp.getCompetencia());
                if(competenciaEntity != null){
                    CandidatoCompetenciaEntity candComp = CandidatoCompetenciaEntity.builder().competencia(competenciaEntity).experiencia(comp.getExperiencia()).candidato(candidatoEntity).build();
                    competencias.add(candComp);
                }
            });
            if(!competencias.isEmpty()) {
                candidatoEntity.setCompetencias(competencias);
            }
            candidatoDao.save(candidatoEntity);

            return mapper.writeValueAsString(CandidatoResponseDto.builder()
                    .operacao(candidato.getOperacao())
                    .mensagem("Competencia cadastrada com sucesso")
                    .status(201).build());

        } catch (Exception e){
            return mapper.writeValueAsString(CandidatoResponseDto.builder()
                    .operacao(candidato.getOperacao())
                    .mensagem("E-mail não encontrado")
                    .status(404).build());

        }
    }

    public String buscar(CandidatoDto candidato){
        try {
            var candidatoEntity = candidatoDao.findByEmail(candidato.getEmail());
            if(candidatoTokenDao.isTokenValid(candidato.getToken())){

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("visualizarCandidato")
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            if(candidatoEntity != null){
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("visualizarCandidato")
                        .nome(candidatoEntity.getNome())
                        .senha(candidatoEntity.getSenha())
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("visualizarCandidato")
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }

        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    public String cadastrar (CandidatoDto candidato) throws JsonProcessingException {
        try {


            var novoCandidato = CandidatoEntity.builder()
                    .email(candidato.getEmail())
                    .senha(candidato.getSenha())
                    .nome(candidato.getNome())
                    .build();

            String validEmail = novoCandidato.getEmail();
            String validSenha = novoCandidato.getSenha();
            String validNome = novoCandidato.getNome();


            if (validNome == null || validNome.isEmpty()) {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(404)
                        .mensagem("Campo nome vazio")
                        .build());
            }
            if (validNome.length() < 6 || validNome.length() > 30) {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(404)
                        .mensagem("Nome muito grande ou pequeno")
                        .build());
            }
            if (validSenha == null || validSenha.length() < 3 || validSenha.length() > 8) {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(404)
                        .mensagem("A senha deve ter entre 3 e 8 caracteres")
                        .build());
            }

            if (validEmail == null || validEmail.isEmpty()) {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(404)
                        .mensagem("Campo email vazio")
                        .build());
            }

            if (!validEmail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(404)
                        .mensagem("Email invalido")
                        .build());
            }

            if(candidatoDao.isEmailAlreadyExists(validEmail)){
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("cadastrarCandidato")
                        .status(402)
                        .mensagem("Email já existente")
                        .build());
            }
            candidatoDao.save(novoCandidato);

            var tokenEntity = candidatoTokenDao.findByCandidato(novoCandidato);

            if (tokenEntity == null) {
                var novoToken = CandidatoTokenEntity.builder()
                        .candidato(novoCandidato)
                        .token(UUID.randomUUID().toString())
                        .build();
                tokenEntity = novoToken;
                candidatoTokenDao.save(novoToken);
            }

            return mapper.writeValueAsString(CandidatoResponseDto.builder()
                    .operacao("cadastrarCandidato")
                    .token(tokenEntity.getToken())
                    .status(201).build());

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String logar(CandidatoDto candidato)   {

        try {
            var candidatoEntity = candidatoDao.findByEmailAndPassword(candidato.getEmail(), candidato.getSenha());

            if(candidatoEntity != null){

                var tokenEntity = candidatoTokenDao.findByCandidato(candidatoEntity);

                if(tokenEntity == null){
                    var novoToken = CandidatoTokenEntity.builder()
                            .candidato(candidatoEntity)
                            .token(UUID.randomUUID().toString())
                            .build();
                    tokenEntity = novoToken;
                    candidatoTokenDao.save(novoToken);
                }

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("loginCandidato")
                        .token(tokenEntity.getToken())
                        .status(200).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("loginCandidato")
                        .mensagem("Login ou senha incorretos")
                        .status(401).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }



    public String logout(CandidatoDto candidato)   {

        try {
            if ((candidato.getToken() == null) || (candidato.getToken().length() != 36)){
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("logout")
                        .mensagem("Token inválido ou não encontrado")
                        .status(404).build());
            }
            var candidatoTokenEntity = candidatoTokenDao.findByToken(candidato.getToken());

            if(candidatoTokenEntity != null){
                candidatoTokenDao.delete(candidatoTokenEntity);

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("logout")
                        .status(204).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("logout")
                        .mensagem("Token não encontrado")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
