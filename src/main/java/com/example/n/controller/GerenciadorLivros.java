package com.example.n.controller;

//Importações necessárias para o uso do código

import com.example.n.model.Livro;
import com.example.n.model.Review;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciadorLivros {
    private List<Livro> livros;
    private Scanner scanner;
    private String nomeUsuario;

    public GerenciadorLivros(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.livros = new ArrayList<>(PersistenciaLivros.carregarLivros(nomeUsuario));
        this.scanner = new Scanner(System.in);
    }

    public void salvarDados() {
        PersistenciaLivros.salvarLivros(nomeUsuario, livros);
    }

    //Cadastra um livro recebendo um objeto Livro como parâmetro, método utilizado para teste
    public void cadastrarLivro(Livro livro) {
        livros.add(livro);
        salvarDados();
    }

    public List<Livro> getTodosLivros() {
        return new ArrayList<>(livros);
    }

}
