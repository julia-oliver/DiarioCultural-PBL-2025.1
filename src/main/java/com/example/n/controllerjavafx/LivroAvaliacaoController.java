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
 * Controller responsável por permitir ao usuário avaliar livros (marcar como lido e adicionar review).
 */
public class LivroAvaliacaoController {

    // --- Componentes da interface gráfica ---
    @FXML private TableView<Livro> tabelaLivros;
    @FXML private TableColumn<Livro, String> colTitulo;
    @FXML private TableColumn<Livro, String> colAutor;
    @FXML private Label lblTituloLivro;
    @FXML private RadioButton rbLido;
    @FXML private TextField txtDataLeitura;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;
    @FXML private Button btnSalvar;

    // --- Variáveis auxiliares ---
    private GerenciadorLivros gerenciadorLivros;
    private Livro livroSelecionado;

    /**
     * Inicializa o controller ao carregar a tela.
     * Configura a tabela e o spinner de estrelas.
     */
    @FXML
    private void initialize() {
        // Define quais propriedades dos objetos Livro serão mostradas nas colunas
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        // Configura spinner com valores de 1 a 5, valor inicial 3
        spEstrelas.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));

        // Campos de avaliação começam desabilitados até que um livro seja selecionado
        habilitarCamposAvaliacao(false);
    }

    /**
     * Recebe o nome do usuário logado e carrega seus livros.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty()) {
            this.gerenciadorLivros = new GerenciadorLivros(usuario);
            carregarLivros();
        }
    }

    /**
     * Carrega todos os livros do usuário na tabela.
     */
    private void carregarLivros() {
        ObservableList<Livro> livros = FXCollections.observableArrayList(
                gerenciadorLivros.getTodosLivros());
        tabelaLivros.setItems(livros);
        tabelaLivros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Ação executada ao selecionar um livro na tabela.
     * Habilita os campos e, se houver avaliação anterior, a exibe.
     */
    @FXML
    private void handleSelecionarLivro() {
        livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();

        if (livroSelecionado != null) {
            lblTituloLivro.setText("Avaliando: " + livroSelecionado.getTitulo());
            habilitarCamposAvaliacao(true);

            // Preenche os campos se o livro já tiver sido avaliado
            if (livroSelecionado.isLido() && livroSelecionado.getReview() != null) {
                rbLido.setSelected(true);
                txtDataLeitura.setText(livroSelecionado.getReview().getData());
                spEstrelas.getValueFactory().setValue(livroSelecionado.getReview().getEstrelas());
                taComentario.setText(livroSelecionado.getReview().getTexto());
            }
        } else {
            mostrarAlerta("Aviso", "Nenhum livro selecionado",
                    "Por favor, selecione um livro da tabela.");
        }
    }

    /**
     * Salva a avaliação feita para o livro selecionado.
     * Valida os dados e registra a avaliação.
     */
    @FXML
    private void handleSalvarAvaliacao() {
        try {
            // Validação: nenhum livro selecionado
            if (livroSelecionado == null) {
                mostrarAlerta("Erro", "Nenhum livro selecionado",
                        "Selecione um livro antes de avaliar.");
                return;
            }

            boolean lido = rbLido.isSelected();
            String dataLeitura = txtDataLeitura.getText();
            int estrelas = spEstrelas.getValue();
            String comentario = taComentario.getText();

            // Validação: se marcado como lido, a data é obrigatória
            if (lido && (dataLeitura == null || dataLeitura.trim().isEmpty())) {
                mostrarAlerta("Erro", "Data inválida",
                        "Informe a data de leitura.");
                return;
            }

            livroSelecionado.setLido(lido);

            if (lido) {
                // Cria avaliação e salva
                Review review = new Review(
                        HelloApplication.getUsuarioLogado(),
                        estrelas,
                        comentario,
                        dataLeitura);
                livroSelecionado.setReview(review);
                gerenciadorLivros.salvarDados();

                mostrarAlerta("Sucesso", "Avaliação salva",
                        "Avaliação registrada com sucesso!");
            } else {
                // Se não foi lido, remove avaliação
                livroSelecionado.setReview(null);
                mostrarAlerta("Informação", "Status atualizado",
                        "Livro marcado como não lido.");
            }

            // Limpa campos e reseta estado da interface
            limparCamposAvaliacao();
            tabelaLivros.getSelectionModel().clearSelection();
            livroSelecionado = null;
            habilitarCamposAvaliacao(false);

        } catch (Exception e) {
            mostrarAlerta("Erro", "Erro ao salvar",
                    "Ocorreu um erro: " + e.getMessage());
        }
    }

    /**
     * Volta à tela principal do menu.
     */
    @FXML
    private void handleVoltar() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Limpa os campos de avaliação.
     */
    private void limparCamposAvaliacao() {
        rbLido.setSelected(false);
        txtDataLeitura.clear();
        spEstrelas.getValueFactory().setValue(3);
        taComentario.clear();
    }

    /**
     * Ativa ou desativa os campos de avaliação conforme necessário.
     */
    private void habilitarCamposAvaliacao(boolean habilitar) {
        rbLido.setDisable(!habilitar);
        txtDataLeitura.setDisable(!habilitar);
        spEstrelas.setDisable(!habilitar);
        taComentario.setDisable(!habilitar);
        btnSalvar.setDisable(!habilitar);
    }

    /**
     * Exibe uma janela de alerta informativo.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
