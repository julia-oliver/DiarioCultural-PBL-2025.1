package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.model.*;
import com.example.n.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

/**
 * Controller responsável pelo cadastro de novos filmes via interface JavaFX.
 */
public class FilmeControllerJavaFX {

    // Campos de entrada de dados do formulário
    @FXML private TextField txtTitulo;
    @FXML private TextField txtGenero;
    @FXML private TextField txtAnoLancamento;
    @FXML private TextField txtDuracao;
    @FXML private TextField txtDirecao;
    @FXML private TextField txtRoteiro;
    @FXML private TextField txtElenco;
    @FXML private TextField txtTituloOriginal;
    @FXML private TextField txtOndeAssistir;
    @FXML private RadioButton rbAssistido;

    // (Componentes abaixo não estão sendo usados nesse controller, mas estão declarados)
    @FXML private Label lblTituloFilme;
    @FXML private RadioButton rbVisto;
    @FXML private TextField txtDataVisualizacao;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;

    // Atributos para lógica de negócios
    private String nomeUsuario;
    private GerenciadorFilmes gerenciadorFilmes;

    /**
     * Recebe o nome do usuário logado e inicializa o GerenciadorFilmes.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return;
        }
        this.nomeUsuario = usuario;
        this.gerenciadorFilmes = new GerenciadorFilmes(nomeUsuario);
    }

    /**
     * Lida com o evento de cadastro de um novo filme.
     */
    @FXML
    private void handleCadastrarFilme(ActionEvent event) {
        try {
            // Captura os valores dos campos do formulário
            String titulo = txtTitulo.getText();
            String genero = txtGenero.getText();
            int anoLancamento = Integer.parseInt(txtAnoLancamento.getText());
            int duracao = Integer.parseInt(txtDuracao.getText());
            String direcao = txtDirecao.getText();
            String roteiro = txtRoteiro.getText();
            String elenco = txtElenco.getText();
            String tituloOriginal = txtTituloOriginal.getText();
            String ondeAssistir = txtOndeAssistir.getText();
            boolean assistido = rbAssistido.isSelected();

            // Validação mínima de campos obrigatórios
            if (titulo.isEmpty() || genero.isEmpty() || direcao.isEmpty()) {
                mostrarAlerta("Erro", "Campos obrigatórios",
                        "Título, Gênero e Direção são campos obrigatórios!");
                return;
            }

            // Cria objeto Filme com os dados informados
            Filme filme = new Filme(
                    titulo, genero, anoLancamento, duracao,
                    direcao, roteiro, elenco,
                    tituloOriginal, ondeAssistir);

            // Define o status de assistido
            filme.setVisto(assistido);

            // Cadastra o filme no gerenciador
            gerenciadorFilmes.cadastrarFilme(filme);

            // Exibe mensagem de sucesso
            mostrarAlerta("Sucesso", "Cadastro realizado", "Filme cadastrado com sucesso!");

            // Limpa os campos do formulário
            limparCampos();

        } catch (NumberFormatException e) {
            // Tratamento de erro caso ano ou duração não sejam números válidos
            mostrarAlerta("Erro", "Valor inválido",
                    "Ano de lançamento e duração devem ser números inteiros!");
        }
    }

    /**
     * Volta para a tela de menu principal.
     */
    @FXML
    private void handleXbuttonmenu() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Mostra um alerta na tela.
     */
    private void mostrarAlerta(String titulo, String cabecalho, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Limpa todos os campos do formulário após cadastro.
     */
    private void limparCampos() {
        txtTitulo.clear();
        txtGenero.clear();
        txtAnoLancamento.clear();
        txtDuracao.clear();
        txtDirecao.clear();
        txtRoteiro.clear();
        txtElenco.clear();
        txtTituloOriginal.clear();
        txtOndeAssistir.clear();
        rbAssistido.setSelected(false);
    }
}
