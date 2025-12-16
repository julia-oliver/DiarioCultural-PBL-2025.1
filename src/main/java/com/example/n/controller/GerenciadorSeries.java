package com.example.n.controller;

//Importações necessárias para o uso do código

import com.example.n.model.Review;
import com.example.n.model.Serie;
import com.example.n.model.Temporada;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorSeries {
    private List<Serie> series;
    private Scanner scanner;
    private String nomeUsuario;

    public GerenciadorSeries(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.series = new ArrayList<>(PersistenciaSeries.carregarSeries(nomeUsuario));
        this.scanner = new Scanner(System.in);
    }

    public void salvarDados() {
        PersistenciaSeries.salvarSeries(nomeUsuario, series);
    }

    //Cadastra uma série recebendo um objeto Serie como parâmetro, método usado para testes
    public void cadastrarSerie(Serie serie) {
        series.add(serie);
        salvarDados();
    }

    public List<Serie> getTodasSeries() {
        return new ArrayList<>(series);
    }

}
