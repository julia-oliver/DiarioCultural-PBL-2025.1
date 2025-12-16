package com.example.n.model;

public class Review extends Avaliacao {
    private String autor;
    private String texto;

    public Review(String autor, int estrelas, String texto, String data) {
        super(estrelas, data);
        this.autor = autor;
        this.texto = texto;
    }

    // Getters e Setters
    public String getAutor() {
        return autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Review por " + autor + ": " + getEstrelas() + " estrelas\n" +
                "Data: " + getData() + "\n" +
                "Comentário: " + texto;
    }
}
