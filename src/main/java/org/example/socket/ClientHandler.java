package org.example.socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.CandidatoDto;
import org.example.domain.dtos.CandidatoExperienciaDto;
import org.example.domain.dtos.VagaDto;
import org.example.domain.usecases.CandidatoCompetenciaUseCase;
import org.example.domain.usecases.CandidatoUseCase;
import org.example.domain.dtos.EmpresaDto;
import org.example.domain.usecases.EmpresaUseCase;
import org.example.domain.usecases.VagaUseCase;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

class ClientHandler implements Runnable {
    private Socket clientSocket;



    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            int who = 0; // 0 ngm - 1 candidato - 2 empresa
            String line;
            while ((line = reader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                CandidatoUseCase candidatoUseCase = new CandidatoUseCase();
                CandidatoCompetenciaUseCase candidatoCompetenciaUseCase = new CandidatoCompetenciaUseCase();
                VagaUseCase vagaUseCase = new VagaUseCase();
                EmpresaUseCase empresaUseCase = new EmpresaUseCase();
                HashMap map = mapper.readValue(line, HashMap.class);

                // Imprime o JSON recebido
                String jsonReceived = mapper.writeValueAsString(map);
                System.out.println("Received JSON: " + jsonReceived);

                var operacao = (String) map.get("operacao");

                switch (operacao) {
                    case "loginCandidato": {
                        who = 1;
                        writer.println(candidatoUseCase.logar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "visualizarCandidato": {
                        writer.println(candidatoUseCase.buscar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "atualizarCandidato": {
                        writer.println(candidatoUseCase.atualizar2(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "apagarCandidato": {
                        writer.println(candidatoUseCase.deletar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "cadastrarCandidato": {
                        who = 1;
                        writer.println(candidatoUseCase.cadastrar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "loginEmpresa": {
                        who = 2;
                        writer.println(empresaUseCase.logar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "visualizarEmpresa": {
                        writer.println(empresaUseCase.buscar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "atualizarEmpresa": {
                        writer.println(empresaUseCase.atualizar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "apagarEmpresa": {
                        writer.println(empresaUseCase.deletar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "cadastrarEmpresa": {
                        who = 2;
                        writer.println(empresaUseCase.cadastrar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "cadastrarVaga":{
                        writer.println(vagaUseCase.cadastrar(mapper.readValue(line, VagaDto.class)));
                        break;
                    }
                    case "atualizarVaga":{
                        writer.println(vagaUseCase.atualizar(mapper.readValue(line, VagaDto.class)));
                        break;
                    }
                    case "apagarVaga":{
                        writer.println(vagaUseCase.deletar(mapper.readValue(line, VagaDto.class)));
                        break;
                    }
                    case "visualizarVaga":{
                        writer.println(vagaUseCase.buscar(mapper.readValue(line, VagaDto.class)));
                        break;
                    }
                    case "listarVagas":{
                        writer.println(empresaUseCase.listar(mapper.readValue(line, EmpresaDto.class)));
                        break;
                    }
                    case "cadastrarCompetenciaExperiencia":{
                        writer.println(candidatoUseCase.atualizar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "atualizarCompetenciaExperiencia":{
                        writer.println(candidatoUseCase.atualizar(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }
                    case "visualizarCompetenciaExperiencia":{
                        writer.println(candidatoCompetenciaUseCase.buscarCandidatoCompetencia(mapper.readValue(line, CandidatoExperienciaDto.class)));
                        break;
                    }
                    case "apagarCompetenciaExperiencia":{
                        writer.println(candidatoCompetenciaUseCase.apagarCompetenciaExperiencia(mapper.readValue(line, CandidatoDto.class)));
                        break;
                    }

                    case "logout": {
                        if (who == 1) {
                            writer.println(candidatoUseCase.logout(mapper.readValue(line, CandidatoDto.class)));
                            break;
                        }
                        if (who == 2) {
                            writer.println(empresaUseCase.logout(mapper.readValue(line, EmpresaDto.class)));
                            break;
                        }else{
                            System.out.println("Não tem ninguem conectado");
                            break;
                        }
                    }
                    default:
                        System.out.println("Operacao nao existe");
                        break;

                }

            }
        } catch (IOException e) {
            System.out.println("Conexão com cliente perdida: " + clientSocket.getRemoteSocketAddress());
            throw new RuntimeException(e);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
