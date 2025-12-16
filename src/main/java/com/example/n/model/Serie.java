package com.example.n.model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Serie {
    private String titulo;
    private String genero;
    private int anoLancamento;
    private int anoEncerramento;
    private String elenco;
    private String tituloOriginal;
    private String ondeAssistir;
    private ArrayList<Temporada> temporadas;
    private double avaliacaoMedia;


    public Serie(String titulo, String genero, int anoLancamento, int anoEncerramento,
                 String elenco, String tituloOriginal, String ondeAssistir) {
        this.titulo = titulo;
        this.genero = genero;
        this.anoLancamento = anoLancamento;
        this.anoEncerramento = anoEncerramento;
        this.elenco = elenco;
        this.tituloOriginal = tituloOriginal;
        this.ondeAssistir = ondeAssistir;
        this.temporadas = new ArrayList<>();
        this.avaliacaoMedia = 0;
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

    public int getAnoEncerramento() {
        return anoEncerramento;
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

    public ArrayList<Temporada> getTemporadas() {
        return temporadas;
    }

    public double getAvaliacaoMedia() {
        return avaliacaoMedia;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public void setAnoEncerramento(int anoEncerramento) {
        this.anoEncerramento = anoEncerramento;
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

    public void setTemporadas(ArrayList<Temporada> temporadas) {
        this.temporadas = temporadas;
        calcularAvaliacaoMedia(); // Recalcula a média ao alterar as temporadas
    }

    public void setAvaliacaoMedia(double avaliacaoMedia) {
        this.avaliacaoMedia = avaliacaoMedia;
    }

    public void adicionarTemporada(Temporada temporada) {
        temporadas.add(temporada);
    }

    public void calcularAvaliacaoMedia() {
        if (temporadas.isEmpty()) {
            avaliacaoMedia = 0;
            return;
        }

        int totalEstrelas = 0;
        int temporadasAvaliadas = 0;

        for (Temporada temporada : temporadas) {
            if (temporada.isAssistida() && temporada.getReview() != null) {
                totalEstrelas += temporada.getReview().getEstrelas();
                temporadasAvaliadas++;
            }
        }

        if (temporadasAvaliadas > 0) {
            avaliacaoMedia = (double) totalEstrelas / temporadasAvaliadas;
        } else {
            avaliacaoMedia = 0;
        }
    }

    @Override
    public String toString() {
        String status = anoEncerramento == 0 ? "Em andamento" : "Encerrada em " + anoEncerramento;
        String avaliacao = avaliacaoMedia > 0 ? String.format(" (%.1f estrelas)", avaliacaoMedia) : "";

        return titulo + " (" + tituloOriginal + ") - " + anoLancamento + " [" + genero +
                "] - " + status + " - " + temporadas.size() + " temporadas" + avaliacao;
    }
}