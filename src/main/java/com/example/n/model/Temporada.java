package com.example.n.model;

public class Temporada {
    private int numero;
    private int ano;
    private int numEpisodios;
    private boolean assistida;
    private Review review;

    public Temporada(int numero, int ano, int numEpisodios) {
        this.numero = numero;
        this.ano = ano;
        this.numEpisodios = numEpisodios;
        this.assistida = false;
    }

    // Getters e Setters
    public int getNumero() {
        return numero;
    }

    public int getAno() {
        return ano;
    }

    public int getNumEpisodios() {
        return numEpisodios;
    }

    public boolean isAssistida() {
        return assistida;
    }

    public Review getReview() {
        return review;
    }

    public void setAssistida(boolean assistida) {
        this.assistida = assistida;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        String status = assistida ? "Assistida" : "Não assistida";
        String avaliacao = review != null ? " (" + review.getEstrelas() + " estrelas)" : "";

        return "Temporada " + numero + " (" + ano + ") - " + numEpisodios +
                " episódios - " + status + avaliacao;
    }
}
