package org.example.domain.usecases;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.*;
import org.example.repository.dao.CandidatoDao;
import org.example.repository.dao.CandidatoTokenDao;
import org.example.repository.entities.CandidatoCompetenciaEntity;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CandidatoCompetenciaUseCase {
    private CandidatoTokenDao candidatoTokenDao = new CandidatoTokenDao();
    private ObjectMapper mapper = new ObjectMapper();
    private CandidatoDao candidatoDao = new CandidatoDao();

    public String buscarCandidatoCompetencia(CandidatoExperienciaDto candidatoRequest) {
        try {
            var candidatoEntity = candidatoDao.findByEmail(candidatoRequest.getEmail());
            if (candidatoTokenDao.isTokenValid(candidatoRequest.getToken())) {

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao(candidatoRequest.getOperacao())
                        .mensagem("Token inválido")
                        .status(404).build());
            }
            if (candidatoEntity != null) {
                List<CandidatoCompetenciaEntity> incompetente = candidatoEntity.getCompetencias();
                List<CompetenciaExperienciaDto> competencias = new ArrayList<>();
                for (CandidatoCompetenciaEntity competencia : incompetente) {
                    CompetenciaExperienciaDto competenciaExp = CompetenciaExperienciaDto.builder()
                            .competencia(competencia.getCompetencia().getNomeCompetencia())
                            .experiencia(competencia.getExperiencia()).build();
                    competencias.add(competenciaExp);
                }
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao(candidatoRequest.getOperacao())
                        .competenciaExperiencia(competencias)
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao(candidatoRequest.getOperacao())
                        .mensagem("E-mail não encontrado")
                        .status(404).build());
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    public String apagarCompetenciaExperiencia(CandidatoDto candidatoRequest) {
        try {
            // Verificar se o token é válido
//            if (candidatoTokenDao.isTokenValid(candidatoRequest.getToken())) {
//                return mapper.writeValueAsString(CandidatoResponseDto.builder()
//                        .operacao("apagarCompetenciaExperiencia")
//                        .mensagem("Token inválido")
//                        .status(404).build());
//            }

            // Encontrar o candidato pelo email
            var candidatoEntity = candidatoDao.findByEmail(candidatoRequest.getEmail());

            if (candidatoEntity != null) {
                // Obter as competências e experiências a serem excluídas da solicitação
                List<CandidatoCompetenciaDto> competenciasParaExcluir = candidatoRequest.getCompetenciaExperiencia();

                for (CandidatoCompetenciaDto competenciaDto : competenciasParaExcluir) {
                    String nomeCompetencia = competenciaDto.getCompetencia();
                    int experiencia = competenciaDto.getExperiencia();

                    // Iterar sobre as competências do candidato
                    for (CandidatoCompetenciaEntity competencia : candidatoEntity.getCompetencias()) {
                        if (competencia.getCompetencia().getNomeCompetencia().equals(nomeCompetencia)) {
                            // Remover a competência da lista em memória
                            candidatoEntity.getCompetencias().remove(competencia);
                            break; // Para evitar ConcurrentModificationException
                        }
                    }
                }

                // Atualizar o candidato no banco de dados
                candidatoDao.save(candidatoEntity);

                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarCompetenciaExperiencia")
                        .mensagem("Competências e experiências excluídas com sucesso")
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(CandidatoResponseDto.builder()
                        .operacao("apagarCompetenciaExperiencia")
                        .mensagem("Candidato não encontrado")
                        .status(404).build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}