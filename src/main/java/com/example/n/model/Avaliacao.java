package com.example.n.model;

public class Avaliacao {
    private int estrelas;
    private String data;

    public Avaliacao(int estrelas, String data) {
        this.estrelas = estrelas;
        this.data = data;
    }

    // Getters e Setters
    public int getEstrelas() {
        return estrelas;
    }

    public String getData() {
        return data;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public void setData(String data) {
        this.data = data;
    }
}
