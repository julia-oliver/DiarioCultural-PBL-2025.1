package com.example.n.controllerjavafx;

import com.example.n.HelloApplication;
import com.example.n.model.*;
import com.example.n.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

/**
 * Controller responsável pelo cadastro de livros na aplicação JavaFX.
 */
public class LivroControllerJavaFX {

    // --- Campos da interface gráfica (FXML) ---
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtEditora;
    @FXML private TextField txtISBN;
    @FXML private TextField txtAnoPublicacao;
    @FXML private TextField txtGenero;
    @FXML private RadioButton rbPossuiExemplar;
    @FXML private Label lblTituloLivro;
    @FXML private RadioButton rbLido;
    @FXML private TextField txtDataLeitura;
    @FXML private Spinner<Integer> spEstrelas;
    @FXML private TextArea taComentario;
    @FXML private TableView<Livro> tabelaLivros;

    // --- Variáveis auxiliares ---
    private String nomeUsuario;
    private boolean usuarioDefinido = false;
    private GerenciadorLivros gerenciadorLivros;
    private List<Livro> livros = new ArrayList<>();

    /**
     * Método de inicialização do controller (caso necessário).
     * Pode ser usado para configurar o Spinner ou a tabela.
     */
    @FXML
    private void initialize() {
        // Aqui você pode inicializar componentes como spEstrelas, caso queira.
    }

    /**
     * Ação ao clicar no botão "Cadastrar Livro".
     * Realiza validações e cria um novo objeto Livro com os dados inseridos.
     */
    @FXML
    private void handleCadastrarLivro(ActionEvent event) {
        try {
            // Coleta de dados dos campos
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String editora = txtEditora.getText();
            String isbn = txtISBN.getText();
            int anoPublicacao = Integer.parseInt(txtAnoPublicacao.getText());
            String genero = txtGenero.getText();
            boolean possuiExemplar = rbPossuiExemplar.isSelected();

            // Validação de campos obrigatórios
            if (titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) {
                mostrarAlerta("Erro", "Campos obrigatórios",
                        "Título, Autor e ISBN são campos obrigatórios!");
                return;
            }

            // Criação do objeto Livro
            Livro livro = new Livro(titulo, autor, editora, isbn,
                    anoPublicacao, genero, possuiExemplar);

            // Cadastra o livro no gerenciador
            gerenciadorLivros.cadastrarLivro(livro);

            // Feedback positivo ao usuário
            mostrarAlerta("Sucesso", "Cadastro realizado", "Livro cadastrado com sucesso!");

            // Limpa os campos após cadastro
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Valor inválido",
                    "O ano de publicação deve ser um número inteiro!");
        }
    }

    /**
     * Define o usuário logado e instancia o gerenciador correspondente.
     */
    public void setUsuarioLogado(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return;
        }
        this.nomeUsuario = usuario;
        this.gerenciadorLivros = new GerenciadorLivros(nomeUsuario);
    }

    /**
     * Volta para a tela principal do menu.
     */
    @FXML
    private void handleXbuttonmenu() {
        HelloApplication.showMenuScreen();
    }

    /**
     * Limpa todos os campos do formulário de cadastro.
     */
    private void limparCampos() {
        txtTitulo.clear();
        txtAutor.clear();
        txtEditora.clear();
        txtISBN.clear();
        txtAnoPublicacao.clear();
        txtGenero.clear();
        rbPossuiExemplar.setSelected(false);
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

    /**
     * Retorna a lista de livros cadastrados (se necessário em outra tela).
     */
    public List<Livro> getLivros() {
        return livros;
    }
}
