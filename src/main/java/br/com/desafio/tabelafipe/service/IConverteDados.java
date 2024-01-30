package br.com.desafio.tabelafipe.service;

import java.util.List;

public interface IConverteDados {
    //<T> T - não sei o retorno do método então uso o tipo genérico representado por estes simbolos
    <T> T obterDados(String json, Class<T> classes);

    <T>List<T> obterLista(String json, Class<T> classe);
}
