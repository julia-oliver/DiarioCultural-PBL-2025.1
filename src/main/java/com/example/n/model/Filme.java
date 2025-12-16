package com.example.n.model;

public class Filme {
    private String titulo;
    private String genero;
    private int anoLancamento;
    private int duracao; // em minutos
    private String direcao;
    private String roteiro;
    private String elenco;
    private String tituloOriginal;
    private String ondeAssistir;
    private boolean visto;
    private Review review;

    public Filme(String titulo, String genero, int anoLancamento, int duracao, String direcao,
                 String roteiro, String elenco, String tituloOriginal, String ondeAssistir) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.duracao = duracao;
        this.direcao = direcao;
        this.roteiro = roteiro;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.visto = false;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public String getGenero() {
        return genero;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getDirecao() {
        return direcao;
    }

    public String getRoteiro() {
        return roteiro;
    }

    public String getElenco() {
        return elenco;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public String getOndeAssistir() {
        return ondeAssistir;
    }

    public boolean isVisto() {
        return visto;
    }

    public Review getReview() {
        return review;
    }

    // No arquivo Filme.java
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setDirecao(String direcao) {
        this.direcao = direcao;
    }

    public void setRoteiro(String roteiro) {
        this.roteiro = roteiro;
    }

    public void setElenco(String elenco) {
        this.elenco = elenco;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public void setOndeAssistir(String ondeAssistir) {
        this.ondeAssistir = ondeAssistir;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    @Override
    public String toString() {
        String status = visto ? "Visto" : "Não visto";
        String avaliacao = review != null ? " (" + review.getEstrelas() + " estrelas)" : "";

        return titulo + " (" + tituloOriginal + ") - " + direcao + " (" + anoLancamento + ") [" +
                genero + ", " + duracao + " min] - " + status + avaliacao;
    }
}
