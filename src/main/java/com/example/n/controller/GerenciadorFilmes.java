package com.example.n.controller;

//Importações necessárias para o uso do código

import com.example.n.model.Filme;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorFilmes {
    public ArrayList<Filme> filmes;
    public Scanner scanner = new Scanner(System.in);
    public String nomeUsuario;

    // Construtor modificado para receber o nome do usuário
    public GerenciadorFilmes(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.filmes = new ArrayList<>(PersistenciaFilmes.carregarFilmes(nomeUsuario));
    }

    // Método para salvar filmes antes de sair
    public void salvarDados() {
        PersistenciaFilmes.salvarFilmes(nomeUsuario, filmes);
    }

    //Cadastra um filme recebendo um objeto Filme como parâmetro, método utilizado para teste
    public void cadastrarFilme(Filme filme) {
        filmes.add(filme);
        salvarDados();
    }

    public List<Filme> getTodosFilmes() {
        return new ArrayList<>(filmes);
    }
}
