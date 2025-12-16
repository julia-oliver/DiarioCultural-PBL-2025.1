package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.controller.GerenciadorFilmes;
import com.example.n.controller.GerenciadorLivros;
import com.example.n.controller.GerenciadorSeries;
import com.example.n.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller responsável por listar e filtrar livros, filmes e séries na interface JavaFX.
 */
public class ListadorJavaFX {

    // --- Componentes da UI para livros ---
    @FXML private TableView<Livro> tabelaLivros;
    @FXML private ComboBox<String> comboFiltroLivros;
    @FXML private TextField campoFiltroLivros;

    // --- Componentes da UI para filmes ---
    @FXML private TableView<Filme> tabelaFilmes;
    @FXML private ComboBox<String> comboFiltroFilmes;
    @FXML private TextField campoFiltroFilmes;

    // --- Componentes da UI para séries ---
    @FXML private TableView<Serie> tabelaSeries;
    @FXML private ComboBox<String> comboFiltroSeries;
    @FXML private TextField campoFiltroSeries;

    // --- Gerenciadores de dados ---
    private GerenciadorLivros gerenciadorLivros;
    private GerenciadorFilmes gerenciadorFilmes;
    private GerenciadorSeries gerenciadorSeries;

    /**
     * Inicialização do controller após o carregamento da interface.
     */
    @FXML
    private void initialize() {
        configurarTabelaLivros();
        configurarTabelaFilmes();
        configurarTabelaSeries();
    }

    // ---------- CONFIGURAÇÕES INICIAIS DE TABELAS E COMBOBOXES ----------

    private void configurarTabelaLivros() {
        TabelaConfigUtils.configurarTabelaLivros(tabelaLivros);
        comboFiltroLivros.getItems().addAll("Todos", "Melhor avaliados", "Pior avaliados", "Por gênero", "Por ano");
        comboFiltroLivros.setValue("Todos");
    }

    private void configurarTabelaFilmes() {
        TabelaConfigUtils.configurarTabelaFilmes(tabelaFilmes);
        comboFiltroFilmes.getItems().addAll("Todos", "Melhor avaliados", "Pior avaliados", "Por gênero", "Por ano");
        comboFiltroFilmes.setValue("Todos");
    }

    private void configurarTabelaSeries() {
        TabelaConfigUtils.configurarTabelaSeries(tabelaSeries);
        comboFiltroSeries.getItems().addAll("Todos", "Melhor avaliadas", "Pior avaliadas", "Por gênero", "Por ano");
        comboFiltroSeries.setValue("Todos");
    }

    // ---------- SETTERS DE GERENCIADORES ----------

    public void setGerenciadorLivros(GerenciadorLivros gerenciadorLivros) {
        this.gerenciadorLivros = gerenciadorLivros;
        carregarDados();
    }

    public void setGerenciadores(GerenciadorLivros gerenciadorLivros,
                                 GerenciadorFilmes gerenciadorFilmes,
                                 GerenciadorSeries gerenciadorSeries) {
        this.gerenciadorLivros = gerenciadorLivros;
        this.gerenciadorFilmes = gerenciadorFilmes;
        this.gerenciadorSeries = gerenciadorSeries;
        carregarDados();
    }

    /**
     * Carrega dados de todas as mídias para as tabelas.
     */
    private void carregarDados() {
        carregarLivros();
        carregarFilmes();
        carregarSeries();
    }

    // ---------- LIVROS ----------

    private void carregarLivros() {
        List<Livro> livros = gerenciadorLivros.getTodosLivros();
        ObservableList<Livro> livrosObservable = FXCollections.observableArrayList(livros);
        tabelaLivros.setItems(livrosObservable);
    }

    /**
     * Aplica o filtro selecionado na lista de livros.
     */
    @FXML
    private void filtrarLivros() {
        String opcao = comboFiltroLivros.getValue();
        List<Livro> livros = gerenciadorLivros.getTodosLivros();

        switch (opcao) {
            case "Melhor avaliados":
                livros = livros.stream()
                        .filter(l -> l.isLido() && l.getReview() != null)
                        .sorted(Comparator.comparingInt(l -> -l.getReview().getEstrelas()))
                        .collect(Collectors.toList());
                break;

            case "Pior avaliados":
                livros = livros.stream()
                        .filter(l -> l.isLido() && l.getReview() != null)
                        .sorted(Comparator.comparingInt(l -> l.getReview().getEstrelas()))
                        .collect(Collectors.toList());
                break;

            case "Por gênero":
                String generoLivro = campoFiltroLivros.getText().trim();
                if (!generoLivro.isEmpty()) {
                    livros = livros.stream()
                            .filter(l -> l.getGenero().equalsIgnoreCase(generoLivro))
                            .collect(Collectors.toList());
                }
                break;

            case "Por ano":
                try {
                    int anoLivro = Integer.parseInt(campoFiltroLivros.getText().trim());
                    livros = livros.stream()
                            .filter(l -> l.getAnoPublicacao() == anoLivro)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    // Ignora e mantém lista completa
                }
                break;
        }

        tabelaLivros.setItems(FXCollections.observableArrayList(livros));
    }

    // ---------- FILMES ----------

    private void carregarFilmes() {
        if (gerenciadorFilmes != null) {
            List<Filme> filmes = gerenciadorFilmes.getTodosFilmes();
            ObservableList<Filme> filmesObservable = FXCollections.observableArrayList(filmes);
            tabelaFilmes.setItems(filmesObservable);
        } else {
            tabelaFilmes.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Aplica o filtro selecionado na lista de filmes.
     */
    @FXML
    private void filtrarFilmes() {
        String opcao = comboFiltroFilmes.getValue();
        List<Filme> filmes = gerenciadorFilmes.getTodosFilmes();

        switch (opcao) {
            case "Melhor avaliados":
                filmes = filmes.stream()
                        .filter(f -> f.isVisto() && f.getReview() != null)
                        .sorted(Comparator.comparingInt(f -> -f.getReview().getEstrelas()))
                        .collect(Collectors.toList());
                break;

            case "Pior avaliados":
                filmes = filmes.stream()
                        .filter(f -> f.isVisto() && f.getReview() != null)
                        .sorted(Comparator.comparingInt(f -> f.getReview().getEstrelas()))
                        .collect(Collectors.toList());
                break;

            case "Por gênero":
                String generoFilme = campoFiltroFilmes.getText().trim();
                if (!generoFilme.isEmpty()) {
                    filmes = filmes.stream()
                            .filter(f -> f.getGenero().equalsIgnoreCase(generoFilme))
                            .collect(Collectors.toList());
                }
                break;

            case "Por ano":
                try {
                    int anoFilme = Integer.parseInt(campoFiltroFilmes.getText().trim());
                    filmes = filmes.stream()
                            .filter(f -> f.getAnoLancamento() == anoFilme)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    // Ignora e mantém lista completa
                }
                break;
        }

        tabelaFilmes.setItems(FXCollections.observableArrayList(filmes));
    }

    // ---------- SÉRIES ----------

    private void carregarSeries() {
        if (gerenciadorSeries != null) {
            List<Serie> series = gerenciadorSeries.getTodasSeries();
            ObservableList<Serie> seriesObservable = FXCollections.observableArrayList(series);
            tabelaSeries.setItems(seriesObservable);
        } else {
            tabelaSeries.setItems(FXCollections.observableArrayList());
        }
    }

    /**
     * Aplica o filtro selecionado na lista de séries.
     */
    @FXML
    private void filtrarSeries() {
        String opcao = comboFiltroSeries.getValue();
        List<Serie> series = gerenciadorSeries.getTodasSeries();

        switch (opcao) {
            case "Melhor avaliadas":
                series = series.stream()
                        .filter(s -> s.getAvaliacaoMedia() > 0)
                        .sorted(Comparator.comparingDouble(Serie::getAvaliacaoMedia).reversed())
                        .collect(Collectors.toList());
                break;

            case "Pior avaliadas":
                series = series.stream()
                        .filter(s -> s.getAvaliacaoMedia() > 0)
                        .sorted(Comparator.comparingDouble(Serie::getAvaliacaoMedia))
                        .collect(Collectors.toList());
                break;

            case "Por gênero":
                String generoSerie = campoFiltroSeries.getText().trim();
                if (!generoSerie.isEmpty()) {
                    series = series.stream()
                            .filter(s -> s.getGenero().equalsIgnoreCase(generoSerie))
                            .collect(Collectors.toList());
                }
                break;

            case "Por ano":
                try {
                    int anoSerie = Integer.parseInt(campoFiltroSeries.getText().trim());
                    series = series.stream()
                            .filter(s -> s.getAnoLancamento() == anoSerie)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    // Ignora e mantém lista completa
                }
                break;
        }

        tabelaSeries.setItems(FXCollections.observableArrayList(series));
    }

    /**
     * Retorna para a tela principal do aplicativo.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }
}
