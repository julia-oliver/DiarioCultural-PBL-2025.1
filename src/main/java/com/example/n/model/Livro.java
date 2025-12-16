package com.example.n.model;

public class Livro {
    public String titulo;
    public String autor;
    public String editora;
    public String isbn;
    public int anoPublicacao;
    public String genero;
    public boolean possuiExemplar;
    public boolean lido;
    public Review review;

    public Livro(String titulo, String autor, String editora, String isbn, int anoPublicacao, String genero, boolean possuiExemplar) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.possuiExemplar = possuiExemplar;
        this.lido = false;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isPossuiExemplar() {
        return possuiExemplar;
    }

    public boolean isLido() {
        return lido;
    }

    public Review getReview() {
        return review;
    }

    // No arquivo Livro.java
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }


    public void setLido(boolean lido) {
        this.lido = lido;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        String status = lido ? "Lido" : "Não lido";
        String exemplar = possuiExemplar ? "Possui exemplar" : "Não possui exemplar";
        String avaliacao = review != null ? " (" + review.getEstrelas() + " estrelas)" : "";

        return titulo + " - " + autor + " (" + anoPublicacao + ") [" + genero + "] - " +
                status + " - " + exemplar + avaliacao;
    }
}
