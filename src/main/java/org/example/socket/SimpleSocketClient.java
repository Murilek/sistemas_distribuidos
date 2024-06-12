package org.example.socket;

import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.dtos.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class SimpleSocketClient {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the server IP address:");
        String serverIp = scanner.nextLine();

        try {
            Socket socket = new Socket(serverIp, 22222);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ObjectMapper mapper = new ObjectMapper();
            String token = "";
            String tokenEmpresa = "";
            String mensagemEnviada = "";

            System.out.println("Connected to the server.");

            // Loop for displaying the menu and processing options
            while (true) {
                System.out.println("Choose an operation:");
                System.out.println("1- Register Candidate");
                System.out.println("2- View Candidate");
                System.out.println("3- Update Candidate");
                System.out.println("4- Delete Candidate");
                System.out.println("5- Login Candidate");
                System.out.println("6- Logout Candidate");
                System.out.println("7- Register Empresa");
                System.out.println("8- View Empresa");
                System.out.println("9- Update Empresa");
                System.out.println("10- Delete Empresa");
                System.out.println("11- Login Empresa");
                System.out.println("12- Logout Empresa");
                System.out.println("13- Register Vaga");
                System.out.println("14- View Vaga");
                System.out.println("15- Update Vaga");
                System.out.println("16- Delete Vaga");
                System.out.println("17- Listar vagas por email");
                System.out.println("18- Register Competences");
                System.out.println("19- View Competencias");
                System.out.println("20- Update Competencias");
                System.out.println("21- Delete Competencias");
                System.out.println("22- Filter Vagas");
                System.out.println("23- Exit");
                System.out.println("24- Imprimir os tokens");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        // Cadastro
                        if(token.length() == 36){
                            System.out.println("Já logado, faça logout para criar outra conta");
                            break;
                        }
                        System.out.println("Informe o nome:");
                        String nomeCadastro = scanner.nextLine();
                        System.out.println("Informe o email:");
                        String emailCadastro = scanner.nextLine();
                        System.out.println("Informe a senha:");
                        String senhaCadastro = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"cadastrarCandidato\", \"nome\":\"" + nomeCadastro + "\", \"email\":\"" + emailCadastro + "\", \"senha\":\"" + senhaCadastro + "\"}";
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        out.println(mensagemEnviada);
                        String registerResponse = in.readLine();
                        System.out.println("Resposta do servidor: " + registerResponse);
                        JsonNode registerResponseJson = mapper.readTree(registerResponse);
                        if (registerResponseJson.has("token")) {
                            token = registerResponseJson.get("token").asText();
                            System.out.println("Token recebido: " + token);
                        }
                        break;

                    case "2":
                        // Visualizar
                        System.out.println("Informe o email do candidato a ser visualizado:");
                        String emailVisualizar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"visualizarCandidato\", \"email\":\"" + emailVisualizar + "\", \"token\":\"" + token + "\"}";
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "3":
                        // Atualizar
                        System.out.println("Informe o email do candidato a ser atualizado:");
                        String emailAtualizar = scanner.nextLine();
                        System.out.println("Informe o novo nome:");
                        String novoNome = scanner.nextLine();
                        System.out.println("Informe a nova senha:");
                        String novaSenha = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"atualizarCandidato\", \"email\":\"" + emailAtualizar + "\", \"nome\":\"" + novoNome + "\", \"senha\":\"" + novaSenha + "\", \"token\":\"" + token + "\"}";;
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "4":
                        // Apagar
                        System.out.println("Informe o email do candidato a ser apagado:");
                        String emailApagar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"apagarCandidato\", \"email\":\"" + emailApagar + "\", \"token\":\"" + token + "\"}";;
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "5":
                        // Login
                        if(token.length() == 36){
                            System.out.println("Já logado, faça logout para mudar de conta");
                            break;
                        }
                        System.out.println("Informe o email:");
                        String emailLogin = scanner.nextLine();
                        System.out.println("Informe a senha:");
                        String senhaLogin = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"loginCandidato\", \"email\":\"" + emailLogin + "\", \"senha\":\"" + senhaLogin + "\"}";
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        String loginResponse = in.readLine();
                        System.out.println("Resposta do servidor: " + loginResponse);
                        JsonNode loginResponseJson = mapper.readTree(loginResponse);
                        if (loginResponseJson.has("token")) {
                            token = loginResponseJson.get("token").asText();
                            System.out.println("Token recebido: " + token);
                        }
                        break;

                    case "6":
                        // Logout
                        if (!token.isEmpty()) {
                            mensagemEnviada = "{\"operacao\": \"logout\", \"token\":\"" + token + "\"}";
                            out.println(mensagemEnviada);
                            System.out.println("Mensagem enviada: " + mensagemEnviada);
                            System.out.println("Resposta do servidor: " + in.readLine());
                            token = ""; // Limpa o token após o logout
                        } else {
                            System.out.println("Nenhum token disponível. Faça o login primeiro.");
                        }
                        break;

                    case "7":
                        // cadastro empresa
                        if(tokenEmpresa.length() == 36){
                            System.out.println("Já logado, faça logout para criar outra conta");
                            break;
                        }
                        System.out.println("Informe o email:");
                        String emailEmpresaCadastro = scanner.nextLine();
                        System.out.println("Informe a razao social:");
                        String razaoCadastro = scanner.nextLine();
                        System.out.println("Informe a senha:");
                        String senhaEmpresaCadastro = scanner.nextLine();
                        System.out.println("Informe o CNPJ:");
                        String cnpjCadastro = scanner.nextLine();
                        System.out.println("Informe o ramo:");
                        String ramoCadastro = scanner.nextLine();
                        System.out.println("Informe a descricao:");
                        String descricaoCadastro = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"cadastrarEmpresa\", \"email\":\"" + emailEmpresaCadastro + "\", \"senha\":\"" + senhaEmpresaCadastro + "\", \"cnpj\":\"" + cnpjCadastro + "\", \"razaoSocial\":\"" + razaoCadastro + "\", \"ramo\":\"" + ramoCadastro + "\", \"descricao\":\"" + descricaoCadastro + "\"}";
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        out.println(mensagemEnviada);
                        String registerEmpresaResponse = in.readLine();
                        System.out.println("Resposta do servidor: " + registerEmpresaResponse);
                        JsonNode registerEmpresaResponseJson = mapper.readTree(registerEmpresaResponse);
                        if (registerEmpresaResponseJson.has("token")) {
                            tokenEmpresa = registerEmpresaResponseJson.get("token").asText();
                            System.out.println("Token recebido: " + tokenEmpresa);
                        }

                        break;
                    case "8":
                        //visualizar empresa
                        System.out.println("Informe o email da empresa a ser visualizado:");
                        String emailEmpresaVisualizar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"visualizarEmpresa\", \"email\":\"" + emailEmpresaVisualizar + "\", \"token\":\"" + tokenEmpresa + "\"}";
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "9":
                        // atualizar empresa
                        System.out.println("Informe o email da empresa a ser atualizada:");
                        String emailEmpresaAtualizar = scanner.nextLine();
                        System.out.println("Informe a nova razao social:");
                        String novaRazaoCadastro = scanner.nextLine();
                        System.out.println("Informe a nova senha:");
                        String novaEmpresaSenha = scanner.nextLine();
                        System.out.println("Informe o novo CNPJ:");
                        String novoCnpjCadastro = scanner.nextLine();
                        System.out.println("Informe o novo ramo:");
                        String novoRamoCadastro = scanner.nextLine();
                        System.out.println("Informe a nova descricao:");
                        String novaDescricaoCadastro = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"atualizarEmpresa\", \"email\":\"" + emailEmpresaAtualizar + "\", \"senha\":\"" + novaEmpresaSenha + "\", \"cnpj\":\"" + novoCnpjCadastro + "\", \"razaoSocial\":\"" + novaRazaoCadastro + "\", \"ramo\":\"" + novoRamoCadastro + "\", \"descricao\":\"" + novaDescricaoCadastro + "\", \"token\":\"" + tokenEmpresa + "\"}";;
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "10":
                        //Apagar empresa
                        System.out.println("Informe o email do candidato a ser apagado:");
                        String emailEmpresaApagar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"apagarEmpresa\", \"email\":\"" + emailEmpresaApagar + "\", \"token\":\"" + tokenEmpresa + "\"}";;
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "11":
                        //Login empresa
                        System.out.println("Informe o email:");
                        String emailEmpresaLogin = scanner.nextLine();
                        System.out.println("Informe a senha:");
                        String senhaEmpresaLogin = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\": \"loginEmpresa\", \"email\":\"" + emailEmpresaLogin + "\", \"senha\":\"" + senhaEmpresaLogin + "\"}";
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        out.println(mensagemEnviada);
                        String loginEmpresaResponse = in.readLine();
                        System.out.println("Resposta do servidor: " + loginEmpresaResponse);
                        JsonNode loginEmpresaResponseJson = mapper.readTree(loginEmpresaResponse);
                        if (loginEmpresaResponseJson.has("token")) {
                            tokenEmpresa = loginEmpresaResponseJson.get("token").asText();
                            System.out.println("Token recebido: " + tokenEmpresa);
                        }
                        break;
                    case "12":
                        //Logout empresa
                        if (!tokenEmpresa.isEmpty()) {
                            mensagemEnviada = "{\"operacao\": \"logout\", \"token\":\"" + tokenEmpresa + "\"}";
                            out.println(mensagemEnviada);
                            System.out.println("Mensagem enviada: " + mensagemEnviada);
                            System.out.println("Resposta do servidor: " + in.readLine());
                            tokenEmpresa = ""; // Limpa o token após o logout
                        } else {
                            System.out.println("Nenhum token disponível. Faça o login da empresa primeiro.");
                        }
                        break;

                    case "13":
                        // Cadastro vaga
                        if (tokenEmpresa.length() != 36) {
                            System.out.println("Não está logado. Faça login para criar uma vaga.");
                            break;
                        }
                        System.out.println("Informe o nome da vaga:");
                        String nomeVagaCadastro = scanner.nextLine();
                        System.out.println("Informe o email:");
                        String emailVagaCadastro = scanner.nextLine();
                        System.out.println("Informe a faixa salarial:");
                        double faixaSalarialCadastro = scanner.nextDouble();
                        scanner.nextLine(); // Consumir a nova linha deixada pelo nextDouble()
                        System.out.println("Informe a descricao:");
                        String descricaoVagaCadastro = scanner.nextLine();
                        System.out.println("Informe o estado:");
                        String estadoCadastro = scanner.nextLine();
                        System.out.println("Informe as competencias (separadas por vírgula):");
                        String competenciasInput = scanner.nextLine();
                        List<String> competencias = Arrays.asList(competenciasInput.split(",\\s*"));


                        VagaRequestDto  vagaDto =  VagaRequestDto.builder()
                                .operacao("cadastrarVaga")
                                .email(emailVagaCadastro)
                                .nome(nomeVagaCadastro)
                                .descricao(descricaoVagaCadastro)
                                .estado(estadoCadastro)
                                .competencias(competencias)
                                .faixaSalarial(faixaSalarialCadastro)
                                .token(tokenEmpresa)
                                .build();


                        mensagemEnviada = mapper.writeValueAsString(vagaDto);
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "14":
                        //view vaga
                        System.out.println("Informe o email da vaga a ser visualizado:");
                        String emailEmpresaVagaVisualizar = scanner.nextLine();
                        System.out.println("Informe o ID da vaga da empresa a ser visualizado:");
                        String idVagaVisualizar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\":\"visualizarVaga\",\"idVaga\":" + idVagaVisualizar + ",\"email\":\"" + emailEmpresaVagaVisualizar + "\",\"token\":\"" + tokenEmpresa + "\"}";
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "15":
                        //update vaga
                        if (tokenEmpresa.length() != 36) {
                            System.out.println("Não está logado. Faça login para atualizar uma vaga.");
                            break;
                        }
                        System.out.println("Informe o id da vaga:");
                        String idVagaAtualizar = scanner.nextLine();
                        System.out.println("Informe o email:");
                        String emailVagaAtualizar = scanner.nextLine();
                        System.out.println("Informe o nome da vaga:");
                        String nomeVagaAtualizar = scanner.nextLine();
                        System.out.println("Informe a faixa salarial:");
                        double faixaSalarialAtualizar = scanner.nextDouble();
                        scanner.nextLine(); // Consumir a nova linha deixada pelo nextDouble()
                        System.out.println("Informe a descricao:");
                        String descricaoVagaAtualizar = scanner.nextLine();
                        System.out.println("Informe o estado:");
                        String estadoAtualizar = scanner.nextLine();
                        System.out.println("Informe as competencias (separadas por vírgula):");
                        String competenciasInputAtualizar = scanner.nextLine();
                        List<String> competenciasAtualizar = Arrays.asList(competenciasInputAtualizar.split(",\\s*"));


                        VagaRequestDto  vagaDtoAtualizar =  VagaRequestDto.builder()
                                .operacao("atualizarVaga")
                                .email(emailVagaAtualizar)
                                .nome(nomeVagaAtualizar)
                                .descricao(descricaoVagaAtualizar)
                                .estado(estadoAtualizar)
                                .competencias(competenciasAtualizar)
                                .faixaSalarial(faixaSalarialAtualizar)
                                .token(tokenEmpresa)
                                .build();


                        mensagemEnviada = mapper.writeValueAsString(vagaDtoAtualizar);
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "16":
                        //delete vaga
                        System.out.println("Informe o id da vaga para deletar:");
                        String idVagaApagar = scanner.nextLine();
                        System.out.println("Informe o email da vaga para deletar:");
                        String emailVagaApagar = scanner.nextLine();
                        mensagemEnviada = "{\"operacao\":\"apagarVaga\",\"idVaga\":" + idVagaApagar + ",\"email\":\"" + emailVagaApagar + "\",\"token\":\"" + tokenEmpresa + "\"}";
                        out.println(mensagemEnviada);
                        System.out.println("Mensagem enviada: " + mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;
                    case "17":
                        //listar vagas
                        System.out.println("Informe o email para o listar vagas:");
                        String emailEmpresaListar = scanner.nextLine();

                        mensagemEnviada = "{\"operacao\": \"listarVagas\", \"email\": \"" + emailEmpresaListar + "\",\"token\": \"" + tokenEmpresa + "\"}";
                        System.out.println(mensagemEnviada);
                        out.println(mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());

                        break;
                    case "18":
                        //Cadastrar competencias
                        System.out.println("Informe o email para o cadastro de competencias:");
                        String emailCompetenciaExpCadastro = scanner.nextLine();
                        List<CandidatoCompetenciaDto>  candidatoCompetenciaExperenciaList = new ArrayList<>();
                        while (true) {
                            // Solicita a string ao usuário
                            System.out.print("Digite uma competencia:(ou 'sair' para finalizar) ");

                            var compString = scanner.nextLine();

                            // Verifica se o usuário quer sair
                            if (compString.equalsIgnoreCase("sair")) {
                                break;
                            }

                            System.out.print("Digite a experiencia da competencia: ");
                            var expString = scanner.nextInt();
                            scanner.nextLine();
                            CandidatoCompetenciaDto candidatoCompetenciaDto = new CandidatoCompetenciaDto().builder()
                                    .competencia(compString)
                                    .experiencia(expString)
                                    .build();

                            candidatoCompetenciaExperenciaList.add(candidatoCompetenciaDto);
                        }

                        CandidatoDto candidatoDto = CandidatoDto.builder()
                                .operacao("cadastrarCompetenciaExperiencia")
                                .token(token)
                                .email(emailCompetenciaExpCadastro)
                                .competenciaExperiencia(candidatoCompetenciaExperenciaList)
                                .build();

                        mensagemEnviada = mapper.writeValueAsString(candidatoDto);
                        out.println(mensagemEnviada);
                        System.out.println(mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "19":
                        // view competences
                        System.out.println("Informe o email para visualizar as competencias:");
                        String emailCompetenciaExpVisualizar = scanner.nextLine();
                        CandidatoExperienciaDto candidatoExperienciaDto = CandidatoExperienciaDto.builder()
                                .operacao("visualizarCompetenciaExperiencia")
                                .token(token)
                                .email(emailCompetenciaExpVisualizar)
                                .build();

                        mensagemEnviada = mapper.writeValueAsString(candidatoExperienciaDto);
                        out.println(mensagemEnviada);
                        System.out.println(mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;


                    case "20":
                        // update competences
                        System.out.println("Informe o email para atualizar as competencias:");
                        String emailCompetenciaExpAtualizar = scanner.nextLine();
                        List<CandidatoCompetenciaDto>  candidatoCompetenciaExperenciaListAtt = new ArrayList<>();
                        while (true) {
                            // Solicita a string ao usuário
                            System.out.print("Digite uma competencia:(ou 'sair' para finalizar) ");

                            var compString = scanner.nextLine();

                            // Verifica se o usuário quer sair
                            if (compString.equalsIgnoreCase("sair")) {
                                break;
                            }

                            System.out.print("Digite a experiencia da competencia: ");
                            var expString = scanner.nextInt();
                            scanner.nextLine();
                            CandidatoCompetenciaDto candidatoCompetenciaDto = new CandidatoCompetenciaDto().builder()
                                    .competencia(compString)
                                    .experiencia(expString)
                                    .build();

                            candidatoCompetenciaExperenciaListAtt.add(candidatoCompetenciaDto);
                        }

                        CandidatoDto candidatoAttDto = CandidatoDto.builder()
                                .operacao("atualizarCompetenciaExperiencia")
                                .token(token)
                                .email(emailCompetenciaExpAtualizar)
                                .competenciaExperiencia(candidatoCompetenciaExperenciaListAtt)
                                .build();
                        mensagemEnviada = mapper.writeValueAsString(candidatoAttDto);
                        out.println(mensagemEnviada);
                        System.out.println(mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "21":
                        //remover comp
                        System.out.println("Informe o email para apagar as competencias:");
                        String emailCompetenciaRemove = scanner.nextLine();
                        List<CandidatoCompetenciaDto>  candidatoCompetenciaDeleteList = new ArrayList<>();
                        while (true) {
                            // Solicita a string ao usuário
                            System.out.print("Digite uma competencia:(ou 'sair' para finalizar) ");

                            var compDeleteString = scanner.nextLine();

                            // Verifica se o usuário quer sair
                            if (compDeleteString.equalsIgnoreCase("sair")) {
                                break;
                            }

                            System.out.print("Digite a experiencia da competencia: ");
                            var expDeleteString = scanner.nextInt();
                            scanner.nextLine();
                            CandidatoCompetenciaDto candidatoCompetenciaDto = new CandidatoCompetenciaDto().builder()
                                    .competencia(compDeleteString)
                                    .experiencia(expDeleteString)
                                    .build();

                            candidatoCompetenciaDeleteList.add(candidatoCompetenciaDto);
                        }

                        CandidatoDto candidatoApagarDto = CandidatoDto.builder()
                                .operacao("apagarCompetenciaExperiencia")
                                .token(token)
                                .email(emailCompetenciaRemove)
                                .competenciaExperiencia(candidatoCompetenciaDeleteList)
                                .build();

                        mensagemEnviada = mapper.writeValueAsString(candidatoApagarDto);
                        out.println(mensagemEnviada);
                        System.out.println(mensagemEnviada);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "22":
                        // filtrar vagas
                        System.out.print("Escolha o tipo de filtro (OR ou AND): ");
                        String tipoFiltro = scanner.nextLine();
                        System.out.print("Insira as competências (separadas por vírgula): ");
                        String competenciasInputFiltrar = scanner.nextLine();
                        List<String> competenciasFiltrar = Arrays.asList(competenciasInputFiltrar.split(","));
                        String jsonRequisicao = "{"
                                + "\"operacao\": \"filtrarVagas\", "
                                + "\"filtros\": {"
                                + "\"competencias\": [" + competenciasFiltrar.stream().map(c -> "\"" + c.trim() + "\"").collect(Collectors.joining(",")) + "],"
                                + "\"tipo\": \"" + tipoFiltro + "\""
                                + "},"
                                + "\"token\": \"" + token + "\""
                                + "}";

                        System.out.println("Mensagem enviada:" + jsonRequisicao);
                        out.println(jsonRequisicao);
                        System.out.println("Resposta do servidor: " + in.readLine());
                        break;

                    case "23":
                        // Sair
                        //out.println("exit");
                        System.out.println("Desconectando...");
                        return;

                    case "24":
                        System.out.println("token candidato: " + token);
                        System.out.println("token empresa: " + tokenEmpresa);
                        break;


                    default:
                        System.out.println("Opção inválida. Por favor, escolha novamente.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
