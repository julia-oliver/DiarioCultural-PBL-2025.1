package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.model.*;
import com.example.n.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Controller responsável pela avaliação de séries e suas temporadas.
 */
public class SeriesAvaliacaoController {

    // --- Componentes da Interface (FXML) ---
    @FXML private TableView<Serie> tabelaSeries;
    @FXML private TableColumn<Serie, String> colTitulo;
    @FXML private TableColumn<Serie, String> colGenero;
    @FXML private Label lblTituloSerie;

    @FXML private Spinner<Integer> spTemporada;
    @FXML private RadioButton rbAssistida;
    @FXML private TextField txtDataVisualizacao;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;
    @FXML private Button btnSalvar;

    // --- Gerenciador e Dados ---
    private GerenciadorSeries gerenciadorSeries;
    private Serie serieSelecionada;

    /**
     * Inicialização dos componentes visuais da tela.
     */
    @FXML
    private void initialize() {
        // Configuração das colunas da tabela
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));

        // Configuração do Spinner de estrelas (1 a 5)
        spEstrelas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));

        // Desativa os campos até que uma série seja selecionada
        habilitarCamposAvaliacao(false);
    }

    /**
     * Define o usuário logado e carrega os dados da série.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty()) {
            this.gerenciadorSeries = new GerenciadorSeries(usuario);
            carregarSeries();
        }
    }

    /**
     * Carrega todas as séries do gerenciador e exibe na tabela.
     */
    private void carregarSeries() {
        ObservableList<Serie> series = FXCollections.observableArrayList(
                gerenciadorSeries.getTodasSeries());
        tabelaSeries.setItems(series);
        tabelaSeries.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Ação de seleção de série na tabela.
     * Ativa os campos de avaliação da temporada.
     */
    @FXML
    private void handleSelecionarSerie() {
        serieSelecionada = tabelaSeries.getSelectionModel().getSelectedItem();

        if (serieSelecionada != null) {
            lblTituloSerie.setText("Avaliando: " + serieSelecionada.getTitulo());

            // Cria Spinner para selecionar temporada
            SpinnerValueFactory.IntegerSpinnerValueFactory factory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            1,
                            serieSelecionada.getTemporadas().size(),
                            1);
            spTemporada.setValueFactory(factory);

            habilitarCamposAvaliacao(true);
        } else {
            mostrarAlerta("Aviso", "Nenhuma série selecionada",
                    "Por favor, selecione uma série da tabela.");
        }
    }

    /**
     * Ação ao clicar no botão "Salvar Avaliação".
     * Registra a avaliação para a temporada da série selecionada.
     */
    @FXML
    private void handleSalvarAvaliacao() {
        try {
            if (serieSelecionada == null) {
                mostrarAlerta("Erro", "Nenhuma série selecionada",
                        "Selecione uma série antes de avaliar.");
                return;
            }

            int numTemporada = spTemporada.getValue();
            boolean assistida = rbAssistida.isSelected();
            String dataVisualizacao = txtDataVisualizacao.getText();
            int estrelas = spEstrelas.getValue();
            String comentario = taComentario.getText();

            if (assistida && (dataVisualizacao == null || dataVisualizacao.trim().isEmpty())) {
                mostrarAlerta("Erro", "Data inválida",
                        "Informe a data de visualização.");
                return;
            }

            Temporada temporada = serieSelecionada.getTemporadas().get(numTemporada - 1);

            if (assistida) {
                Review review = new Review(
                        HelloApplication.getUsuarioLogado(),
                        estrelas,
                        comentario,
                        dataVisualizacao);

                temporada.setReview(review);
                temporada.setAssistida(true);
                serieSelecionada.calcularAvaliacaoMedia();
                gerenciadorSeries.salvarDados();

                mostrarAlerta("Sucesso", "Avaliação salva",
                        "Avaliação registrada com sucesso!");
            } else {
                temporada.setAssistida(false);
                mostrarAlerta("Informação", "Status atualizado",
                        "Temporada marcada como não assistida.");
            }

            // Reset da tela após salvar
            limparCamposAvaliacao();
            tabelaSeries.getSelectionModel().clearSelection();
            serieSelecionada = null;
            habilitarCamposAvaliacao(false);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    /**
     * Volta para o menu principal.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Limpa todos os campos de avaliação da interface.
     */
    private void limparCamposAvaliacao() {
        if (spTemporada.getValueFactory() != null) {
            spTemporada.getValueFactory().setValue(1);
        }
        rbAssistida.setSelected(false);
        txtDataVisualizacao.clear();
        spEstrelas.getValueFactory().setValue(3);
        taComentario.clear();
    }

    /**
     * Ativa ou desativa os campos de avaliação com base na seleção de série.
     */
    private void habilitarCamposAvaliacao(boolean habilitar) {
        spTemporada.setDisable(!habilitar);
        rbAssistida.setDisable(!habilitar);
        txtDataVisualizacao.setDisable(!habilitar);
        spEstrelas.setDisable(!habilitar);
        taComentario.setDisable(!habilitar);
        if (btnSalvar != null) {
            btnSalvar.setDisable(!habilitar);
        }
    }

    /**
     * Exibe um alerta informativo para o usuário.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
