package org.example.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.CandidatoResponseDto;
import org.example.domain.dtos.EmpresaDto;
import org.example.domain.dtos.VagaDto;
import org.example.domain.dtos.VagaResponseDto;
import org.example.repository.dao.CandidatoDao;
import org.example.repository.dao.CompetenciaDao;
import org.example.repository.dao.EmpresaDao;
import org.example.repository.dao.VagaDao;
import org.example.repository.entities.CompetenciaEntity;
import org.example.repository.entities.VagaEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class VagaUseCase {
    private ObjectMapper mapper = new ObjectMapper();
    private EmpresaDao empresaDao = new EmpresaDao();
    private VagaDao vagaDao = new VagaDao();
    private CompetenciaDao competenciaDao = new CompetenciaDao();

    public String deletar(VagaDto vaga) {
        try {
            var vagaEntity = vagaDao.findById(vaga.getIdVaga());
            if (vagaEntity != null) {
                vagaDao.delete(vagaEntity);

                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("apagarVaga")
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("apagarVaga")
                        .mensagem("Vaga não encontrada")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String atualizar(VagaDto vaga) throws JsonProcessingException {
        try {
            List<CompetenciaEntity> competenciasAtt = new ArrayList<>();
            var vagaEntity = vagaDao.findById(vaga.getIdVaga());
            if (vagaEntity != null) {
                vagaEntity.setFaixaSalarial(vaga.getFaixaSalarial());
                vagaEntity.setDescricao(vaga.getDescricao());
                vagaEntity.setNome(vaga.getNome());
                vagaEntity.setEstado(vaga.getEstado());
                vaga.getCompetencias().forEach(competencia -> {
                    var competenciaEntity = competenciaDao.findByName(competencia);
                    if (competenciaEntity != null) {
                        competenciasAtt.add(competenciaEntity);
                    } else {
                        System.out.println(competencia + " nao encontrado");
                    }
                });

                vagaEntity.setCompetencias(competenciasAtt);


                vagaDao.save(vagaEntity);

                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("atualizarVaga")
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("atualizarVaga")
                        .mensagem("Vaga não encontrada")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String buscar(VagaDto vaga) {
        try {
            var vagaEntity = vagaDao.findById(vaga.getIdVaga());
            if (vagaEntity != null) {

                List<String> listaCompetencia = vagaEntity.getCompetencias().stream().map(CompetenciaEntity::getNomeCompetencia).collect(Collectors.toList());
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("visualizarVaga")
                        .faixaSalarial(vagaEntity.getFaixaSalarial())
                        .descricao(vagaEntity.getDescricao())
                        .estado(vagaEntity.getEstado())
                        .competencias(listaCompetencia)
                        .status(201).build());
            } else {
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("visualizarVaga")
                        .mensagem("Vaga não encontrada")
                        .status(404).build());
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    public String cadastrar(VagaDto vaga) throws JsonProcessingException {
        try {
            List<CompetenciaEntity> competencias = new ArrayList<>();

            var empresaEntity = empresaDao.findByEmail(vaga.getEmail());
            if (empresaEntity == null) {
                return mapper.writeValueAsString(VagaResponseDto.builder()
                        .operacao("cadastrarVaga")
                        .mensagem("Empresa inexistente")
                        .status(404).build());
            }

            vaga.getCompetencias().forEach(competencia -> {
                var competenciaEntity = competenciaDao.findByName(competencia);
                if (competenciaEntity != null) {
                    competencias.add(competenciaEntity);
                }
                else{
                    System.out.println(competencia + "nao encontrado");
                }
            });

            var novaVaga = VagaEntity.builder()
                    .nome(vaga.getNome())
                    .faixaSalarial(vaga.getFaixaSalarial())
                    .descricao(vaga.getDescricao())
                    .email(vaga.getEmail())
                    .estado(vaga.getEstado())
                    .competencias(competencias)
                    .empresa(empresaEntity)
                    .build();

            vagaDao.save(novaVaga);

            return mapper.writeValueAsString(VagaResponseDto.builder()
                    .operacao("cadastrarVaga")
                    .mensagem("Vaga cadastrada com sucesso")
                    .status(201).build());

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
