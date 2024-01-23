package br.com.desafio.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//ignora campos não mapeados, isso impede erros na execução quando campos forem lidos pela aplicação.
@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosVeiculos(@JsonAlias("Marca") String marca,
                            @JsonAlias("Modelo") String modelo,
                            @JsonAlias("AnoModelo") Integer ano) {

}
