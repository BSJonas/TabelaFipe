package br.com.desafio.tabelafipe.principal;

import br.com.desafio.tabelafipe.model.DadosVeiculos;
import br.com.desafio.tabelafipe.model.Modelos;
import br.com.desafio.tabelafipe.model.Veiculo;
import br.com.desafio.tabelafipe.service.ConsumoApi;
import br.com.desafio.tabelafipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private String endereco;
    private ConverteDados converteDados = new ConverteDados();
    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {
        var menu = """
                                
                *** OPÇÕES***
                Carro
                Moto
                Caminhão
                                
                Digite umas das opções para consultar: 
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();

        if (opcao.toLowerCase().contains("carr")) {
            endereco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        ConsumoApi consumoApi = new ConsumoApi();
        var json = consumoApi.obterDados(endereco);
        System.out.println(json);
        var marcas = converteDados.obterLista(json, DadosVeiculos.class);
        marcas.stream()
                .sorted(Comparator.comparing(DadosVeiculos::codigo))
                .forEach(System.out::println);

        System.out.println("\nInforme o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endereco = endereco + "/" + codigoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);
        var modeloLista = converteDados.obterDados(json, Modelos.class);

        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(DadosVeiculos::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um trecho do nome do veículo a ser buscado");
        var nomeVeiculo = leitura.nextLine();

        List<DadosVeiculos> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo para buscar os valores de avaliação");
        var codigoModelo = leitura.nextLine();

        endereco = endereco + "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(endereco);
        List<DadosVeiculos> anos = converteDados.obterLista(json, DadosVeiculos.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = converteDados.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados com avaliações por ano: ");
        veiculos.forEach(System.out::println);


    }

}

