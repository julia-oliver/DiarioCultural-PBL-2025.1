package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.controller.GerenciadorFilmes;
import com.example.n.controller.GerenciadorLivros;
import com.example.n.controller.GerenciadorSeries;
import com.example.n.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para a tela de busca de itens culturais (livros, filmes e séries).
 * Gerencia as operações de busca e exibição dos resultados.
 */
public class BuscadorJavaFX {
    // ========== COMPONENTES DA INTERFACE ==========

    // Componentes do TabPane
    @FXML private TabPane tabPane;
    @FXML private Tab tabLivros;
    @FXML private Tab tabFilmes;
    @FXML private Tab tabSeries;

    // Componentes para busca de Livros
    @FXML private ComboBox<String> comboTipoBuscaLivros;
    @FXML private TextField campoBuscaLivros;
    @FXML private TableView<Livro> tabelaLivros;

    // Componentes para busca de Filmes
    @FXML private ComboBox<String> comboTipoBuscaFilmes;
    @FXML private TextField campoBuscaFilmes;
    @FXML private TableView<Filme> tabelaFilmes;

    // Componentes para busca de Séries
    @FXML private ComboBox<String> comboTipoBuscaSeries;
    @FXML private TextField campoBuscaSeries;
    @FXML private TableView<Serie> tabelaSeries;

    // ========== GERENCIADORES DE DADOS ==========
    private GerenciadorLivros gerenciadorLivros;
    private GerenciadorFilmes gerenciadorFilmes;
    private GerenciadorSeries gerenciadorSeries;

    // ========== INICIALIZAÇÃO ==========

    /**
     * Método de inicialização chamado automaticamente pelo JavaFX.
     * Configura os componentes das abas de busca.
     */
    @FXML
    private void initialize() {
        configurarBuscaLivros();
        configurarBuscaFilmes();
        configurarBuscaSeries();
    }

    // ========== MÉTODOS DE CONFIGURAÇÃO ==========

    /**
     * Configura os componentes da aba de busca de livros.
     */
    private void configurarBuscaLivros() {
        // Configura combobox de tipos de busca
        comboTipoBuscaLivros.getItems().addAll(
                "Título", "Autor", "Gênero", "Ano", "ISBN", "Todos"
        );
        comboTipoBuscaLivros.setValue("Título");

        // Configura tabela usando a classe utilitária
        TabelaConfigUtils.configurarTabelaLivros(tabelaLivros);
    }

    /**
     * Configura os componentes da aba de busca de filmes.
     */
    private void configurarBuscaFilmes() {
        // Configura combobox de tipos de busca
        comboTipoBuscaFilmes.getItems().addAll(
                "Título", "Diretor", "Ator", "Gênero", "Ano", "Todos"
        );
        comboTipoBuscaFilmes.setValue("Título");

        // Configura tabela usando a classe utilitária
        TabelaConfigUtils.configurarTabelaFilmes(tabelaFilmes);
    }

    /**
     * Configura os componentes da aba de busca de séries.
     */
    private void configurarBuscaSeries() {
        // Configura combobox de tipos de busca
        comboTipoBuscaSeries.getItems().addAll(
                "Título", "Gênero", "Ator", "Ano", "Todos"
        );
        comboTipoBuscaSeries.setValue("Título");

        // Configura tabela usando a classe utilitária
        TabelaConfigUtils.configurarTabelaSeries(tabelaSeries);
    }

    // ========== MÉTODOS DE GERENCIAMENTO DE DADOS ==========

    /**
     * Define os gerenciadores de dados para as diferentes categorias.
     */
    public void setGerenciadores(GerenciadorLivros gerenciadorLivros,
                                 GerenciadorFilmes gerenciadorFilmes,
                                 GerenciadorSeries gerenciadorSeries) {
        this.gerenciadorLivros = gerenciadorLivros;
        this.gerenciadorFilmes = gerenciadorFilmes;
        this.gerenciadorSeries = gerenciadorSeries;
    }

    // ========== MÉTODOS DE BUSCA ==========

    /**
     * Realiza a busca de livros com base nos critérios selecionados.
     */
    @FXML
    private void buscarLivros() {
        String tipoBusca = comboTipoBuscaLivros.getValue();
        String termo = campoBuscaLivros.getText().trim();

        List<Livro> resultados = realizarBuscaLivros(tipoBusca, termo);
        tabelaLivros.setItems(FXCollections.observableArrayList(resultados));
    }

    /**
     * Lógica de busca para livros.
     */
    private List<Livro> realizarBuscaLivros(String tipoBusca, String termo) {
        switch (tipoBusca) {
            case "Título":
                return buscarLivrosPorTitulo(termo);
            case "Autor":
                return buscarLivrosPorAutor(termo);
            case "Gênero":
                return buscarLivrosPorGenero(termo);
            case "Ano":
                return buscarLivrosPorAno(termo);
            case "ISBN":
                return buscarLivrosPorISBN(termo);
            case "Todos":
            default:
                return gerenciadorLivros.getTodosLivros();
        }
    }

    private List<Livro> buscarLivrosPorTitulo(String termo) {
        return gerenciadorLivros.getTodosLivros().stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Livro> buscarLivrosPorAutor(String termo) {
        return gerenciadorLivros.getTodosLivros().stream()
                .filter(l -> l.getAutor().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Livro> buscarLivrosPorGenero(String termo) {
        return gerenciadorLivros.getTodosLivros().stream()
                .filter(l -> l.getGenero().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Livro> buscarLivrosPorAno(String termo) {
        try {
            int ano = Integer.parseInt(termo);
            return gerenciadorLivros.getTodosLivros().stream()
                    .filter(l -> l.getAnoPublicacao() == ano)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return gerenciadorLivros.getTodosLivros();
        }
    }

    private List<Livro> buscarLivrosPorISBN(String termo) {
        return gerenciadorLivros.getTodosLivros().stream()
                .filter(l -> l.getIsbn().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Realiza a busca de filmes com base nos critérios selecionados.
     */
    @FXML
    private void buscarFilmes() {
        String tipoBusca = comboTipoBuscaFilmes.getValue();
        String termo = campoBuscaFilmes.getText().trim();

        List<Filme> resultados = realizarBuscaFilmes(tipoBusca, termo);
        tabelaFilmes.setItems(FXCollections.observableArrayList(resultados));
    }

    /**
     * Lógica de busca para filmes.
     */
    private List<Filme> realizarBuscaFilmes(String tipoBusca, String termo) {
        switch (tipoBusca) {
            case "Título":
                return buscarFilmesPorTitulo(termo);
            case "Diretor":
                return buscarFilmesPorDiretor(termo);
            case "Ator":
                return buscarFilmesPorAtor(termo);
            case "Gênero":
                return buscarFilmesPorGenero(termo);
            case "Ano":
                return buscarFilmesPorAno(termo);
            case "Todos":
            default:
                return gerenciadorFilmes.getTodosFilmes();
        }
    }

    private List<Filme> buscarFilmesPorTitulo(String termo) {
        return gerenciadorFilmes.getTodosFilmes().stream()
                .filter(f -> f.getTitulo().toLowerCase().contains(termo.toLowerCase()) ||
                        f.getTituloOriginal().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Filme> buscarFilmesPorDiretor(String termo) {
        return gerenciadorFilmes.getTodosFilmes().stream()
                .filter(f -> f.getDirecao().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Filme> buscarFilmesPorAtor(String termo) {
        return gerenciadorFilmes.getTodosFilmes().stream()
                .filter(f -> f.getElenco().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Filme> buscarFilmesPorGenero(String termo) {
        return gerenciadorFilmes.getTodosFilmes().stream()
                .filter(f -> f.getGenero().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Filme> buscarFilmesPorAno(String termo) {
        try {
            int ano = Integer.parseInt(termo);
            return gerenciadorFilmes.getTodosFilmes().stream()
                    .filter(f -> f.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return gerenciadorFilmes.getTodosFilmes();
        }
    }

    /**
     * Realiza a busca de séries com base nos critérios selecionados.
     */
    @FXML
    private void buscarSeries() {
        String tipoBusca = comboTipoBuscaSeries.getValue();
        String termo = campoBuscaSeries.getText().trim();

        List<Serie> resultados = realizarBuscaSeries(tipoBusca, termo);
        tabelaSeries.setItems(FXCollections.observableArrayList(resultados));
    }

    /**
     * Lógica de busca para séries.
     */
    private List<Serie> realizarBuscaSeries(String tipoBusca, String termo) {
        switch (tipoBusca) {
            case "Título":
                return buscarSeriesPorTitulo(termo);
            case "Gênero":
                return buscarSeriesPorGenero(termo);
            case "Ator":
                return buscarSeriesPorAtor(termo);
            case "Ano":
                return buscarSeriesPorAno(termo);
            case "Todos":
            default:
                return gerenciadorSeries.getTodasSeries();
        }
    }

    private List<Serie> buscarSeriesPorTitulo(String termo) {
        return gerenciadorSeries.getTodasSeries().stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(termo.toLowerCase()) ||
                        s.getTituloOriginal().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Serie> buscarSeriesPorGenero(String termo) {
        return gerenciadorSeries.getTodasSeries().stream()
                .filter(s -> s.getGenero().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Serie> buscarSeriesPorAtor(String termo) {
        return gerenciadorSeries.getTodasSeries().stream()
                .filter(s -> s.getElenco().toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    private List<Serie> buscarSeriesPorAno(String termo) {
        try {
            int ano = Integer.parseInt(termo);
            return gerenciadorSeries.getTodasSeries().stream()
                    .filter(s -> s.getAnoLancamento() == ano)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return gerenciadorSeries.getTodasSeries();
        }
    }

    // ========== NAVEGAÇÃO ==========

    /**
     * Volta para o menu principal.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }
}