package com.example.n;

import com.example.n.controllerjavafx.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import com.example.n.controller.*;

/**
 * Classe principal da aplicação JavaFX que gerencia todas as cenas e controladores.
 * Implementa o padrão Singleton para acesso aos controladores e cenas.
 */
public class HelloApplication extends Application {
    // Elementos estáticos da aplicação
    private static Stage primaryStage;
    private static String usuarioLogado;

    // Controladores das cenas
    private static HelloController menuController;
    private static LivroControllerJavaFX livroController;
    private static FilmeControllerJavaFX filmeController;
    private static SeriesControllerJavaFX serieController;
    private static ListadorJavaFX listadorController;
    private static LivroAvaliacaoController livroAvaliacaoController;
    private static FilmeAvaliacaoController filmeAvaliacaoController;
    private static SeriesAvaliacaoController serieAvaliacaoController;
    private static BuscadorJavaFX buscadorController;
    private static EditorDadosJavaFX editorDadosController;

    // Cenas da aplicação
    private static Scene loginScene;
    private static Scene mainScene;
    private static Scene cadastrolivroScene;
    private static Scene cadastrofilmeScene;
    private static Scene cadastroserieScene;
    private static Scene listarScene;
    private static Scene avaliarLivroScene;
    private static Scene avaliarFilmeScene;
    private static Scene avaliarSerieScene;
    private static Scene buscaScene;
    private static Scene edicaoScene;

    /**
     * Método principal de inicialização da aplicação JavaFX.
     * @param stage Palco principal da aplicação
     * @throws IOException Se ocorrer erro ao carregar os arquivos FXML
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        // Carrega todas as cenas da aplicação
        carregarCenas();

        // Configurações iniciais do palco
        configurarPalcoPrincipal();
    }

    /**
     * Carrega todas as cenas FXML e seus respectivos controladores.
     * @throws IOException Se ocorrer erro ao carregar os arquivos FXML
     */
    private void carregarCenas() throws IOException {
        // 1. Tela de login (primeira tela a ser exibida)
        loginScene = carregarCena("login.fxml", null);

        // 2. Tela principal do menu
        FXMLLoader menuLoader = criarLoader("menuinicial.fxml");
        mainScene = criarCena(menuLoader);
        menuController = menuLoader.getController();

        // 3. Telas de cadastro
        cadastrolivroScene = carregarCenaComController("cadastrolivro.fxml",
                loader -> livroController = loader.getController());
        cadastrofilmeScene = carregarCenaComController("cadastrofilme.fxml",
                loader -> filmeController = loader.getController());
        cadastroserieScene = carregarCenaComController("cadastroseries.fxml",
                loader -> serieController = loader.getController());

        // 4. Telas de listagem e busca
        listarScene = carregarCenaComController("listagem.fxml",
                loader -> listadorController = loader.getController());
        buscaScene = carregarCenaComController("busca.fxml",
                loader -> buscadorController = loader.getController());
        edicaoScene = carregarCenaComController("edicao.fxml",
                loader -> editorDadosController = loader.getController());

        // 5. Telas de avaliação
        avaliarLivroScene = carregarCenaComController("avaliarlivro.fxml",
                loader -> livroAvaliacaoController = loader.getController());
        avaliarFilmeScene = carregarCenaComController("avaliarfilme.fxml",
                loader -> filmeAvaliacaoController = loader.getController());
        avaliarSerieScene = carregarCenaComController("avaliarserie.fxml",
                loader -> serieAvaliacaoController = loader.getController());
    }

    /**
     * Configura o palco principal da aplicação.
     */
    private void configurarPalcoPrincipal() {
        primaryStage.setScene(loginScene); // Começa com a tela de login
        primaryStage.setMinWidth(1512);
        primaryStage.setMinHeight(982);
        primaryStage.setTitle("Diário Cultural");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Método auxiliar para carregar uma cena sem necessidade de armazenar o controlador.
     */
    private Scene carregarCena(String fxmlFile, String cssFile) throws IOException {
        FXMLLoader loader = criarLoader(fxmlFile);
        Scene scene = new Scene(loader.load());
        if (cssFile != null) {
            scene.getStylesheets().add(getClass().getResource(cssFile).toExternalForm());
        } else {
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        }
        return scene;
    }

    /**
     * Método auxiliar para carregar uma cena e configurar seu controlador.
     */
    private Scene carregarCenaComController(String fxmlFile, ControllerConsumer consumer) throws IOException {
        FXMLLoader loader = criarLoader(fxmlFile);
        Scene scene = criarCena(loader);
        consumer.accept(loader);
        return scene;
    }

    /**
     * Interface funcional para processar o controlador após o carregamento.
     */
    @FunctionalInterface
    private interface ControllerConsumer {
        void accept(FXMLLoader loader);
    }

    /**
     * Cria um FXMLLoader para o arquivo especificado.
     */
    private FXMLLoader criarLoader(String fxmlFile) {
        return new FXMLLoader(getClass().getResource(fxmlFile));
    }

    /**
     * Cria uma cena a partir de um loader e aplica o estilo padrão.
     */
    private Scene criarCena(FXMLLoader loader) throws IOException {
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

    // ========== MÉTODOS DE GERENCIAMENTO DE USUÁRIO ==========

    /**
     * Define o usuário logado e atualiza todos os controladores.
     * @param usuario Nome do usuário logado
     */
    public static void setUsuarioLogado(String usuario) {
        usuarioLogado = usuario;
        // Atualiza todos os controllers com o usuário logado
        if (menuController != null) {
            menuController.setUsuario(usuario);
        }
        if (livroController != null) {
            livroController.setUsuarioLogado(usuario);
        }
        // Nota: Poderia ser estendido para outros controladores conforme necessário
    }

    /**
     * @return Nome do usuário atualmente logado
     */
    public static String getUsuarioLogado() {
        return usuarioLogado;
    }

    // ========== MÉTODOS DE TROCA DE CENAS ==========

    public static void showMenuScreen() {
        primaryStage.setScene(mainScene);
    }

    public static void showLoginScreen() {
        primaryStage.setScene(loginScene);
    }

    public static void cadastrolivroScreen() {
        if (livroController != null && usuarioLogado != null) {
            livroController.setUsuarioLogado(usuarioLogado);
        }
        primaryStage.setScene(cadastrolivroScene);
    }

    public static void cadastrofilmeScreen() {
        if (filmeController != null && usuarioLogado != null) {
            filmeController.setUsuarioLogado(usuarioLogado);
        }
        primaryStage.setScene(cadastrofilmeScene);
    }

    public static void cadastroserieScreen() {
        if (serieController != null && usuarioLogado != null) {
            serieController.setUsuarioLogado(usuarioLogado);
        }
        primaryStage.setScene(cadastroserieScene);
    }

    public static void listarScreen() {
        if (listadorController != null && usuarioLogado != null) {
            GerenciadorLivros gerenciadorLivros = new GerenciadorLivros(usuarioLogado);
            GerenciadorFilmes gerenciadorFilmes = new GerenciadorFilmes(usuarioLogado);
            GerenciadorSeries gerenciadorSeries = new GerenciadorSeries(usuarioLogado);
            listadorController.setGerenciadores(gerenciadorLivros, gerenciadorFilmes, gerenciadorSeries);
        }
        primaryStage.setScene(listarScene);
    }

    public static void avaliarSerieScreen() {
        if (serieAvaliacaoController != null && usuarioLogado != null) {
            serieAvaliacaoController.setUsuarioLogado(usuarioLogado);
            primaryStage.setScene(avaliarSerieScene);
        }
    }

    public static void avaliarLivroScreen() {
        if (livroAvaliacaoController != null && usuarioLogado != null) {
            livroAvaliacaoController.setUsuarioLogado(usuarioLogado);
            primaryStage.setScene(avaliarLivroScene);
        }
    }

    public static void avaliarFilmeScreen() {
        if (filmeAvaliacaoController != null && usuarioLogado != null) {
            filmeAvaliacaoController.setUsuarioLogado(usuarioLogado);
            primaryStage.setScene(avaliarFilmeScene);
        }
    }

    public static void buscaScreen() {
        if (buscadorController != null && usuarioLogado != null) {
            GerenciadorLivros gerenciadorLivros = new GerenciadorLivros(usuarioLogado);
            GerenciadorFilmes gerenciadorFilmes = new GerenciadorFilmes(usuarioLogado);
            GerenciadorSeries gerenciadorSeries = new GerenciadorSeries(usuarioLogado);
            buscadorController.setGerenciadores(gerenciadorLivros, gerenciadorFilmes, gerenciadorSeries);
        }
        primaryStage.setScene(buscaScene);
    }

    public static void edicaoScreen() {
        if (editorDadosController != null && usuarioLogado != null) {
            GerenciadorLivros gerenciadorLivros = new GerenciadorLivros(usuarioLogado);
            GerenciadorFilmes gerenciadorFilmes = new GerenciadorFilmes(usuarioLogado);
            GerenciadorSeries gerenciadorSeries = new GerenciadorSeries(usuarioLogado);
            editorDadosController.setGerenciadores(gerenciadorLivros, gerenciadorFilmes, gerenciadorSeries);
        }
        primaryStage.setScene(edicaoScene);
    }

    // ========== GETTERS PARA CONTROLADORES ==========

    public static HelloController getMenuController() {
        return menuController;
    }

    // ========== MÉTODO MAIN ==========

    public static void main(String[] args) {
        launch(args);
    }
}