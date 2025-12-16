package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.model.*;
import com.example.n.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller responsável pelo cadastro e avaliação de séries.
 */
public class SeriesControllerJavaFX {

    // --- Campos da aba de cadastro ---
    @FXML private TextField txtTitulo;
    @FXML private TextField txtTituloOriginal;
    @FXML private TextField txtGenero;
    @FXML private TextField txtAnoLancamento;
    @FXML private TextField txtAnoEncerramento;
    @FXML private TextField txtElenco;
    @FXML private TextField txtOndeAssistir;
    @FXML private Spinner<Integer> spNumTemporadas;
    @FXML private RadioButton rbEmAndamento;
    @FXML private GridPane temporadasGrid;

    // --- Campos da aba de avaliação ---
    @FXML private Label lblTituloSerie;
    @FXML private Spinner<Integer> spTemporada;
    @FXML private RadioButton rbAssistida;
    @FXML private TextField txtDataVisualizacao;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;

    // --- Controle interno ---
    private String nomeUsuario;
    private GerenciadorSeries gerenciadorSeries;
    private List<Spinner<Integer>> episodiosSpinners = new ArrayList<>();
    private Serie serieSelecionada;

    /**
     * Inicialização do controller. Configura spinner de temporadas e listeners.
     */
    @FXML
    private void initialize() {
        // Define o número de temporadas de 1 a 20 (padrão: 1)
        spNumTemporadas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1));

        // Atualiza os campos de episódios conforme o número de temporadas
        spNumTemporadas.valueProperty().addListener((obs, oldVal, newVal) -> {
            atualizarCamposTemporadas(newVal);
        });

        atualizarCamposTemporadas(1); // Inicializa com 1 temporada
    }

    /**
     * Atualiza o GridPane de temporadas com spinners para episódios.
     */
    private void atualizarCamposTemporadas(int numTemporadas) {
        temporadasGrid.getChildren().clear();
        episodiosSpinners.clear();

        for (int i = 0; i < numTemporadas; i++) {
            Label label = new Label("Temporada " + (i + 1) + " - Episódios:");
            label.getStyleClass().add("textocadastromenor");

            Spinner<Integer> spinner = new Spinner<>(1, 100, 10);
            spinner.getStyleClass().add("spinner");

            temporadasGrid.addRow(i, label, spinner);
            episodiosSpinners.add(spinner);
        }
    }

    /**
     * Define o usuário logado e instancia o gerenciador de séries.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) return;

        this.nomeUsuario = usuario;
        this.gerenciadorSeries = new GerenciadorSeries(usuario);
    }

    /**
     * Ação para cadastrar uma nova série.
     */
    @FXML
    private void handleCadastrarSerie(ActionEvent event) {
        try {
            // Leitura e validação dos campos
            String titulo = txtTitulo.getText();
            String tituloOriginal = txtTituloOriginal.getText();
            String genero = txtGenero.getText();
            int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
            int anoEncerramento = rbEmAndamento.isSelected() ? 0 : Integer.parseInt(txtAnoEncerramento.getText());
            String elenco = txtElenco.getText();
            String ondeAssistir = txtOndeAssistir.getText();

            if (titulo.isEmpty() || genero.isEmpty() || txtAnoLancamento.getText().isEmpty()) {
                mostrarAlerta("Erro", "Campos obrigatórios",
                        "Título, Gênero e Ano de Lançamento são obrigatórios!");
                return;
            }

            // Criação do objeto Serie
            Serie serie = new Serie(titulo, genero, anoLancamento, anoEncerramento,
                    elenco, tituloOriginal, ondeAssistir);

            // Adiciona temporadas com número de episódios personalizados
            for (int i = 0; i < episodiosSpinners.size(); i++) {
                int numEpisodios = episodiosSpinners.get(i).getValue();
                Temporada temporada = new Temporada(i + 1, anoLancamento + i, numEpisodios);
                serie.adicionarTemporada(temporada);
            }

            // Cadastro e persistência
            gerenciadorSeries.cadastrarSerie(serie);
            gerenciadorSeries.salvarDados();

            mostrarAlerta("Sucesso", "Cadastro realizado",
                    "Série cadastrada com sucesso!");

            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Valor inválido",
                    "Ano de lançamento/encerramento deve ser numérico!");
        }
    }

    /**
     * Ação ao marcar/desmarcar a opção "Em andamento".
     */
    @FXML
    private void handleEmAndamentoChanged(ActionEvent event) {
        boolean emAndamento = rbEmAndamento.isSelected();
        txtAnoEncerramento.setDisable(emAndamento);
        if (emAndamento) txtAnoEncerramento.clear();
    }

    /**
     * Ação para voltar ao menu principal.
     */
    @FXML
    private void handleXbuttonmenu() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Limpa os campos do formulário de cadastro.
     */
    private void limparCampos() {
        txtTitulo.clear();
        txtTituloOriginal.clear();
        txtGenero.clear();
        txtAnoLancamento.clear();
        txtAnoEncerramento.clear();
        txtElenco.clear();
        txtOndeAssistir.clear();
        rbEmAndamento.setSelected(false);
        spNumTemporadas.getValueFactory().setValue(1);
        atualizarCamposTemporadas(1);
    }

    /**
     * Define a série que está sendo avaliada e prepara a interface.
     */
    public void setSerieSelecionada(Serie serie) {
        this.serieSelecionada = serie;
        lblTituloSerie.setText("Avaliando: " + serie.getTitulo());

        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                1, serie.getTemporadas().size(), 1);
        spTemporada.setValueFactory(factory);
    }

    /**
     * Ação para salvar avaliação da temporada selecionada.
     */
    @FXML
    private void handleSalvarAvaliacaoSerie() {
        try {
            int numTemporada = spTemporada.getValue();
            boolean assistida = rbAssistida.isSelected();
            String dataVisualizacao = txtDataVisualizacao.getText();
            int estrelas = spEstrelas.getValue();
            String comentario = taComentario.getText();

            Temporada temporada = serieSelecionada.getTemporadas().get(numTemporada - 1);

            if (assistida) {
                Review review = new Review(
                        HelloApplication.getUsuarioLogado(),
                        estrelas, comentario, dataVisualizacao);
                temporada.setReview(review);
                temporada.setAssistida(true);
                serieSelecionada.calcularAvaliacaoMedia();
                gerenciadorSeries.salvarDados();

                mostrarAlerta("Sucesso", "Avaliação salva",
                        "Sua avaliação foi registrada com sucesso!");
            } else {
                temporada.setAssistida(false);
                mostrarAlerta("Informação", "Status atualizado",
                        "A temporada foi marcada como não assistida.");
            }

            HelloApplication.showMenuScreen();

        } catch (Exception e) {
            mostrarAlerta("Erro", "Dados inválidos", "Verifique os dados informados.");
        }
    }

    /**
     * Mostra uma caixa de alerta para o usuário.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
