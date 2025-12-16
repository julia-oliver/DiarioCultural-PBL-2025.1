package com.example.n;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import com.example.n.model.Usuario;

/**
 * Controller principal da aplicação
 * Gerencia a tela inicial e navegação entre menus
 */
public class HelloController {

    // ---------- Componentes FXML ----------
    @FXML private VBox submenulivro;
    @FXML private VBox submenufilmes;
    @FXML private VBox submenuseries;
    @FXML private Label bemvindoLabel;
    @FXML private TextField nomeTextField;
    @FXML private String usuario; // Armazena nome do usuário

    // ---------- Métodos de Inicialização ----------

    /**
     * Inicializa componentes após carregamento do FXML
     */
    @FXML
    private void initialize() {
        // Configura estado inicial dos submenus
        if (submenulivro != null) {
            submenulivro.setVisible(false);
            submenulivro.setManaged(false);
        }

        // Inicializa label de boas-vindas
        if (bemvindoLabel != null) {
            bemvindoLabel.setText("");
        }

        // Carrega fonte customizada
        Font.loadFont(getClass().getResourceAsStream("/fonts/Gloock-Regular.ttf"), 10);
    }

    // ---------- Métodos de Usuário ----------

    /**
     * Define o usuário atual e atualiza a label de boas-vindas
     * @param nome Nome do usuário
     */
    public void setUsuario(String nome) {
        this.usuario = nome;
        if (bemvindoLabel != null) {
            bemvindoLabel.setText("Bem-vindo(a), " + usuario + "!");
        } else {
            System.err.println("Aviso: bemvindoLabel não foi carregada pelo FXML.");
        }
    }

    /**
     * Valida e cadastra novo usuário
     */
    @FXML
    private void cadastrarUsuario() {
        String nome = nomeTextField.getText().trim();

        if (nome.isEmpty()) {
            showAlert("Erro", "Nome inválido", "Por favor, digite um nome válido.");
            return;
        }

        HelloApplication.setUsuarioLogado(nome);
        Usuario nomeUsuario = new Usuario(nome);
        setUsuario(nome);
        HelloApplication.showMenuScreen();
    }

    // ---------- Métodos de Navegação ----------

    @FXML private void handleBuscar() {
        HelloApplication.buscaScreen();
    }

    @FXML private void handleListar() {
        HelloApplication.listarScreen();
    }

    @FXML private void handleEditar() {
        HelloApplication.edicaoScreen();
    }

    // ---------- Métodos de Submenu Livros ----------

    @FXML private void handleLivros() {
        submenulivro.setVisible(true);
        submenulivro.setManaged(true);
        submenulivro.toFront();
    }

    @FXML private void handleCadastrarLivro() {
        submenulivro.setVisible(false);
        HelloApplication.cadastrolivroScreen();
    }

    @FXML private void handleAvaliarLivro() {
        submenulivro.setVisible(false);
        HelloApplication.avaliarLivroScreen();
    }

    @FXML private void fecharsubmenulivro() {
        submenulivro.setVisible(false);
    }

    // ---------- Métodos de Submenu Filmes ----------

    @FXML private void handleFilmes() {
        submenufilmes.setVisible(true);
        submenufilmes.setManaged(true);
        submenufilmes.toFront();
    }

    @FXML private void handleCadastrarFilmes() {
        submenulivro.setVisible(false);
        HelloApplication.cadastrofilmeScreen();
    }

    @FXML private void handleAvaliarFilme() {
        submenulivro.setVisible(false);
        HelloApplication.avaliarFilmeScreen();
    }

    @FXML private void fecharsubmenufilme() {
        submenufilmes.setVisible(false);
    }

    // ---------- Métodos de Submenu Séries ----------

    @FXML private void handleSeries() {
        submenuseries.setVisible(true);
        submenuseries.setManaged(true);
        submenuseries.toFront();
    }

    @FXML private void handleCadastrarSeries() {
        submenuseries.setVisible(false);
        HelloApplication.cadastroserieScreen();
    }

    @FXML private void handleAvaliarSerie() {
        submenuseries.setVisible(false);
        HelloApplication.avaliarSerieScreen();
    }

    @FXML private void fecharsubmenuseries() {
        submenuseries.setVisible(false);
    }

    // ---------- Métodos Auxiliares ----------

    /**
     * Exibe diálogo de alerta
     * @param title Título do alerta
     * @param header Cabeçalho do alerta
     * @param content Mensagem principal
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}