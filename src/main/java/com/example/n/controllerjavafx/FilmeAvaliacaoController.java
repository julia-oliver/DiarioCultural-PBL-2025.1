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
 * Controller responsável por gerenciar a avaliação de filmes por um usuário.
 */
public class FilmeAvaliacaoController {

    // FXML Componentes da interface
    @FXML private TableView<Filme> tabelaFilmes;
    @FXML private TableColumn<Filme, String> colTitulo;
    @FXML private TableColumn<Filme, String> colDiretor;
    @FXML private Label lblTituloFilme;
    @FXML private RadioButton rbVisto;
    @FXML private TextField txtDataVisualizacao;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;
    @FXML private Button btnSalvar;

    // Lógica de negócio
    private GerenciadorFilmes gerenciadorFilmes;
    private Filme filmeSelecionado;

    /**
     * Inicializa o controller após o carregamento da FXML.
     * Configura as colunas da tabela e o Spinner de estrelas.
     */
    @FXML
    private void initialize() {
        // Configura as colunas da tabela
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colDiretor.setCellValueFactory(new PropertyValueFactory<>("direcao"));

        // Define o intervalo do Spinner de estrelas (1 a 5)
        spEstrelas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));

        // Desabilita os campos de avaliação até que um filme seja selecionado
        habilitarCamposAvaliacao(false);
    }

    /**
     * Define o usuário logado e carrega os filmes associados a ele.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty()) {
            this.gerenciadorFilmes = new GerenciadorFilmes(usuario);
            carregarFilmes();
        }
    }

    /**
     * Carrega os filmes do usuário logado na tabela.
     */
    private void carregarFilmes() {
        ObservableList<Filme> filmes = FXCollections.observableArrayList(
                gerenciadorFilmes.getTodosFilmes());

        tabelaFilmes.setItems(filmes);
        tabelaFilmes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Trata a seleção de um filme na tabela e exibe os dados para avaliação.
     */
    @FXML
    private void handleSelecionarFilme() {
        filmeSelecionado = tabelaFilmes.getSelectionModel().getSelectedItem();

        if (filmeSelecionado != null) {
            lblTituloFilme.setText("Avaliando: " + filmeSelecionado.getTitulo());
            habilitarCamposAvaliacao(true);

            // Preenche os campos com os dados da avaliação, se já houver
            if (filmeSelecionado.isVisto() && filmeSelecionado.getReview() != null) {
                rbVisto.setSelected(true);
                txtDataVisualizacao.setText(filmeSelecionado.getReview().getData());
                spEstrelas.getValueFactory().setValue(filmeSelecionado.getReview().getEstrelas());
                taComentario.setText(filmeSelecionado.getReview().getTexto());
            }
        } else {
            mostrarAlerta("Aviso", "Nenhum filme selecionado",
                    "Por favor, selecione um filme da tabela.");
        }
    }

    /**
     * Trata o evento de salvar a avaliação do filme.
     */
    @FXML
    private void handleSalvarAvaliacao() {
        try {
            if (filmeSelecionado == null) {
                mostrarAlerta("Erro", "Nenhum filme selecionado",
                        "Selecione um filme antes de avaliar.");
                return;
            }

            boolean visto = rbVisto.isSelected();
            String dataVisualizacao = txtDataVisualizacao.getText();
            int estrelas = spEstrelas.getValue();
            String comentario = taComentario.getText();

            // Validação: se marcou como visto, a data deve ser informada
            if (visto && (dataVisualizacao == null || dataVisualizacao.trim().isEmpty())) {
                mostrarAlerta("Erro", "Data inválida",
                        "Informe a data de visualização.");
                return;
            }

            // Atualiza o status de visualização
            filmeSelecionado.setVisto(visto);

            if (visto) {
                // Cria a avaliação e salva
                Review review = new Review(
                        HelloApplication.getUsuarioLogado(),
                        estrelas,
                        comentario,
                        dataVisualizacao);

                filmeSelecionado.setReview(review);
                gerenciadorFilmes.salvarDados();

                mostrarAlerta("Sucesso", "Avaliação salva",
                        "Avaliação registrada com sucesso!");
            } else {
                // Remove a avaliação, se o filme não foi visto
                filmeSelecionado.setReview(null);
                mostrarAlerta("Informação", "Status atualizado",
                        "Filme marcado como não visto.");
            }

            // Limpa e reseta a tela
            limparCamposAvaliacao();
            tabelaFilmes.getSelectionModel().clearSelection();
            filmeSelecionado = null;
            habilitarCamposAvaliacao(false);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    /**
     * Retorna à tela de menu principal.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Limpa todos os campos de avaliação.
     */
    private void limparCamposAvaliacao() {
        rbVisto.setSelected(false);
        txtDataVisualizacao.clear();
        spEstrelas.getValueFactory().setValue(3);
        taComentario.clear();
    }

    /**
     * Ativa ou desativa os campos de avaliação.
     */
    private void habilitarCamposAvaliacao(boolean habilitar) {
        rbVisto.setDisable(!habilitar);
        txtDataVisualizacao.setDisable(!habilitar);
        spEstrelas.setDisable(!habilitar);
        taComentario.setDisable(!habilitar);
        btnSalvar.setDisable(!habilitar);
    }

    /**
     * Exibe uma caixa de alerta para o usuário.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}

