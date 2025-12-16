package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.controller.GerenciadorFilmes;
import com.example.n.controller.GerenciadorLivros;
import com.example.n.controller.GerenciadorSeries;
import com.example.n.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.n.controllerjavafx.TabelaConfigUtils;


/**
 * Controlador para a tela de edição de dados de livros, filmes e séries.
 * Permite visualizar e editar informações dos itens culturais cadastrados.
 */
public class EditorDadosJavaFX {
    // ========== COMPONENTES DA INTERFACE ==========

    // Componentes gerais
    @FXML private TabPane tabPane;

    // Componentes para Livros
    @FXML private TableView<Livro> tabelaLivros;
    @FXML private TextField txtLivroTitulo;
    @FXML private TextField txtLivroAutor;
    @FXML private TextField txtLivroGenero;
    @FXML private TextField txtLivroAno;
    @FXML private TextField txtLivroIsbn;
    @FXML private TextField txtLivroEditora;
    @FXML private CheckBox chkLivroLido;

    // Componentes para Filmes
    @FXML private TableView<Filme> tabelaFilmes;
    @FXML private TextField txtFilmeTitulo;
    @FXML private TextField txtFilmeTituloOriginal;
    @FXML private TextField txtFilmeGenero;
    @FXML private TextField txtFilmeAno;
    @FXML private TextField txtFilmeDuracao;
    @FXML private TextField txtFilmeDirecao;
    @FXML private TextField txtFilmeRoteiro;
    @FXML private TextField txtFilmeElenco;
    @FXML private TextField txtFilmeOndeAssistir;
    @FXML private CheckBox chkFilmeVisto;

    // Componentes para Séries
    @FXML private TableView<Serie> tabelaSeries;
    @FXML private TextField txtSerieTitulo;
    @FXML private TextField txtSerieTituloOriginal;
    @FXML private TextField txtSerieGenero;
    @FXML private TextField txtSerieAnoLancamento;
    @FXML private TextField txtSerieAnoEncerramento;
    @FXML private TextField txtSerieElenco;
    @FXML private TextField txtSerieOndeAssistir;

    // ========== GERENCIADORES E ESTADO ==========
    private GerenciadorLivros gerenciadorLivros;
    private GerenciadorFilmes gerenciadorFilmes;
    private GerenciadorSeries gerenciadorSeries;
    private Livro livroSelecionado;
    private Filme filmeSelecionado;
    private Serie serieSelecionado;

    // ========== INICIALIZAÇÃO ==========

    /**
     * Método de inicialização chamado automaticamente pelo JavaFX.
     * Configura as tabelas e listeners de seleção.
     */
    @FXML
    private void initialize() {
        // Configura as tabelas usando a classe utilitária
        TabelaConfigUtils.configurarTabelaLivros(tabelaLivros);
        TabelaConfigUtils.configurarTabelaFilmes(tabelaFilmes);
        TabelaConfigUtils.configurarTabelaSeries(tabelaSeries);

        // Adiciona listeners de seleção (específicos para edição)
        configurarListeners();
    }

    /**
     * Configura os listeners de seleção para as tabelas.
     */
    private void configurarListeners() {
        tabelaLivros.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarDadosLivro(newSelection);
            }
        });

        tabelaFilmes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarDadosFilme(newSelection);
            }
        });

        tabelaSeries.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                carregarDadosSerie(newSelection);
            }
        });
    }


    // ========== CARREGAMENTO DE DADOS ==========

    /**
     * Carrega os dados de um livro selecionado nos campos de edição.
     */
    private void carregarDadosLivro(Livro livro) {
        this.livroSelecionado = livro;
        txtLivroTitulo.setText(livro.getTitulo());
        txtLivroAutor.setText(livro.getAutor());
        txtLivroGenero.setText(livro.getGenero());
        txtLivroAno.setText(String.valueOf(livro.getAnoPublicacao()));
        txtLivroIsbn.setText(livro.getIsbn());
        txtLivroEditora.setText(livro.getEditora());
        chkLivroLido.setSelected(livro.isLido());
    }

    /**
     * Carrega os dados de um filme selecionado nos campos de edição.
     */
    private void carregarDadosFilme(Filme filme) {
        this.filmeSelecionado = filme;
        txtFilmeTitulo.setText(filme.getTitulo());
        txtFilmeTituloOriginal.setText(filme.getTituloOriginal());
        txtFilmeGenero.setText(filme.getGenero());
        txtFilmeAno.setText(String.valueOf(filme.getAnoLancamento()));
        txtFilmeDuracao.setText(String.valueOf(filme.getDuracao()));
        txtFilmeDirecao.setText(filme.getDirecao());
        txtFilmeRoteiro.setText(filme.getRoteiro());
        txtFilmeElenco.setText(filme.getElenco());
        txtFilmeOndeAssistir.setText(filme.getOndeAssistir());
        chkFilmeVisto.setSelected(filme.isVisto());
    }

    /**
     * Carrega os dados de uma série selecionada nos campos de edição.
     */
    private void carregarDadosSerie(Serie serie) {
        this.serieSelecionado = serie;
        txtSerieTitulo.setText(serie.getTitulo());
        txtSerieTituloOriginal.setText(serie.getTituloOriginal());
        txtSerieGenero.setText(serie.getGenero());
        txtSerieAnoLancamento.setText(String.valueOf(serie.getAnoLancamento()));
        txtSerieAnoEncerramento.setText(String.valueOf(serie.getAnoEncerramento()));
        txtSerieElenco.setText(serie.getElenco());
        txtSerieOndeAssistir.setText(serie.getOndeAssistir());
    }

    // ========== OPERAÇÕES DE SALVAMENTO ==========

    /**
     * Salva as alterações feitas nos dados do livro selecionado.
     */
    @FXML
    private void salvarLivro() {
        if (livroSelecionado == null) return;

        // Atualiza os dados do livro com os valores dos campos
        livroSelecionado.setTitulo(txtLivroTitulo.getText());
        livroSelecionado.setAutor(txtLivroAutor.getText());
        livroSelecionado.setGenero(txtLivroGenero.getText());
        livroSelecionado.setAnoPublicacao(Integer.parseInt(txtLivroAno.getText()));
        livroSelecionado.setIsbn(txtLivroIsbn.getText());
        livroSelecionado.setEditora(txtLivroEditora.getText());
        livroSelecionado.setLido(chkLivroLido.isSelected());

        // Persiste as alterações
        gerenciadorLivros.salvarDados();
        atualizarTabelaLivros();
        mostrarAlerta("Sucesso", "Livro atualizado com sucesso!");
    }

    /**
     * Salva as alterações feitas nos dados do filme selecionado.
     */
    @FXML
    private void salvarFilme() {
        if (filmeSelecionado == null) return;

        // Atualiza os dados do filme com os valores dos campos
        filmeSelecionado.setTitulo(txtFilmeTitulo.getText());
        filmeSelecionado.setTituloOriginal(txtFilmeTituloOriginal.getText());
        filmeSelecionado.setGenero(txtFilmeGenero.getText());
        filmeSelecionado.setAnoLancamento(Integer.parseInt(txtFilmeAno.getText()));
        filmeSelecionado.setDuracao(Integer.parseInt(txtFilmeDuracao.getText()));
        filmeSelecionado.setDirecao(txtFilmeDirecao.getText());
        filmeSelecionado.setRoteiro(txtFilmeRoteiro.getText());
        filmeSelecionado.setElenco(txtFilmeElenco.getText());
        filmeSelecionado.setOndeAssistir(txtFilmeOndeAssistir.getText());
        filmeSelecionado.setVisto(chkFilmeVisto.isSelected());

        // Persiste as alterações
        gerenciadorFilmes.salvarDados();
        atualizarTabelaFilmes();
        mostrarAlerta("Sucesso", "Filme atualizado com sucesso!");
    }

    /**
     * Salva as alterações feitas nos dados da série selecionada.
     */
    @FXML
    private void salvarSerie() {
        if (serieSelecionado == null) return;

        // Atualiza os dados da série com os valores dos campos
        serieSelecionado.setTitulo(txtSerieTitulo.getText());
        serieSelecionado.setTituloOriginal(txtSerieTituloOriginal.getText());
        serieSelecionado.setGenero(txtSerieGenero.getText());
        serieSelecionado.setAnoLancamento(Integer.parseInt(txtSerieAnoLancamento.getText()));
        serieSelecionado.setAnoEncerramento(Integer.parseInt(txtSerieAnoEncerramento.getText()));
        serieSelecionado.setElenco(txtSerieElenco.getText());
        serieSelecionado.setOndeAssistir(txtSerieOndeAssistir.getText());

        // Persiste as alterações
        gerenciadorSeries.salvarDados();
        atualizarTabelaSeries();
        mostrarAlerta("Sucesso", "Série atualizada com sucesso!");
    }

    // ========== ATUALIZAÇÃO DE TABELAS ==========

    private void atualizarTabelaLivros() {
        tabelaLivros.refresh();
    }

    private void atualizarTabelaFilmes() {
        tabelaFilmes.refresh();
    }

    private void atualizarTabelaSeries() {
        tabelaSeries.refresh();
    }

    // ========== GERENCIADORES DE DADOS ==========

    /**
     * Define os gerenciadores de dados e carrega as informações iniciais.
     */
    public void setGerenciadores(GerenciadorLivros gerenciadorLivros,
                                 GerenciadorFilmes gerenciadorFilmes,
                                 GerenciadorSeries gerenciadorSeries) {
        this.gerenciadorLivros = gerenciadorLivros;
        this.gerenciadorFilmes = gerenciadorFilmes;
        this.gerenciadorSeries = gerenciadorSeries;
        carregarDados();
    }

    /**
     * Carrega os dados iniciais nos gerenciadores.
     */
    private void carregarDados() {
        if (gerenciadorLivros != null) {
            tabelaLivros.setItems(FXCollections.observableArrayList(gerenciadorLivros.getTodosLivros()));
        }
        if (gerenciadorFilmes != null) {
            tabelaFilmes.setItems(FXCollections.observableArrayList(gerenciadorFilmes.getTodosFilmes()));
        }
        if (gerenciadorSeries != null) {
            tabelaSeries.setItems(FXCollections.observableArrayList(gerenciadorSeries.getTodasSeries()));
        }
    }

    // ========== NAVEGAÇÃO E UTILITÁRIOS ==========

    /**
     * Volta para o menu principal.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Exibe um alerta com a mensagem especificada.
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
