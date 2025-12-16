package com.example.n.controllerjavafx;

import com.example.n.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Classe utilitária para configurar tabelas JavaFX com dados de livros, filmes e séries.
 */
public class TabelaConfigUtils {

    // ============================
    // LIVROS
    // ============================

    /**
     * Configura colunas para uma TableView de livros.
     *
     * @param tabela TableView onde os livros serão exibidos.
     */
    public static void configurarTabelaLivros(TableView<Livro> tabela) {
        tabela.getColumns().clear();

        // Colunas padrão
        adicionarColuna(tabela, "Título", "titulo");
        adicionarColuna(tabela, "Autor", "autor");
        adicionarColuna(tabela, "Gênero", "genero");
        adicionarColuna(tabela, "Ano", "anoPublicacao");
        adicionarColuna(tabela, "Editora", "editora");
        adicionarColuna(tabela, "ISBN", "isbn");

        // Coluna personalizada: Status de leitura
        TableColumn<Livro, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> {
            Livro livro = cellData.getValue();
            return new SimpleStringProperty(livro.isLido() ? "Lido" : "Não Lido");
        });
        tabela.getColumns().add(colStatus);

        // Coluna personalizada: Avaliação
        TableColumn<Livro, String> colAvaliacao = new TableColumn<>("Avaliação");
        colAvaliacao.setCellValueFactory(cellData -> {
            Review review = cellData.getValue().getReview();
            return new SimpleStringProperty(
                    review != null ? review.getEstrelas() + " estrelas" : "Não avaliado"
            );
        });
        tabela.getColumns().add(colAvaliacao);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ============================
    // FILMES
    // ============================

    /**
     * Configura colunas para uma TableView de filmes.
     *
     * @param tabela TableView onde os filmes serão exibidos.
     */
    public static void configurarTabelaFilmes(TableView<Filme> tabela) {
        tabela.getColumns().clear();

        // Colunas padrão
        adicionarColuna(tabela, "Título", "titulo");
        adicionarColuna(tabela, "Diretor", "direcao");
        adicionarColuna(tabela, "Gênero", "genero");
        adicionarColuna(tabela, "Ano", "anoLancamento");
        adicionarColuna(tabela, "Duração (min)", "duracao");

        // Coluna personalizada: Status de visualização
        TableColumn<Filme, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> {
            Filme filme = cellData.getValue();
            return new SimpleStringProperty(filme.isVisto() ? "Visto" : "Não Visto");
        });
        tabela.getColumns().add(colStatus);

        // Coluna personalizada: Avaliação
        TableColumn<Filme, String> colAvaliacao = new TableColumn<>("Avaliação");
        colAvaliacao.setCellValueFactory(cellData -> {
            Review review = cellData.getValue().getReview();
            return new SimpleStringProperty(
                    review != null ? review.getEstrelas() + " estrelas" : "Não avaliado"
            );
        });
        tabela.getColumns().add(colAvaliacao);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ============================
    // SÉRIES
    // ============================

    /**
     * Configura colunas para uma TableView de séries.
     *
     * @param tabela TableView onde as séries serão exibidas.
     */
    public static void configurarTabelaSeries(TableView<Serie> tabela) {
        tabela.getColumns().clear();

        // Colunas padrão
        adicionarColuna(tabela, "Título", "titulo");
        adicionarColuna(tabela, "Gênero", "genero");
        adicionarColuna(tabela, "Ano de Lançamento", "anoLancamento");

        // Coluna personalizada: Status (Em andamento ou Encerrada)
        TableColumn<Serie, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cellData -> {
            Serie serie = cellData.getValue();
            boolean emAndamento = serie.getAnoEncerramento() == 0;
            return new SimpleStringProperty(emAndamento ? "Em andamento" : "Encerrada");
        });
        tabela.getColumns().add(colStatus);

        // Coluna personalizada: Avaliação média
        TableColumn<Serie, String> colAvaliacao = new TableColumn<>("Avaliação Média");
        colAvaliacao.setCellValueFactory(cellData -> {
            Serie serie = cellData.getValue();
            double media = serie.getAvaliacaoMedia();
            return new SimpleStringProperty(
                    media > 0 ? String.format("%.1f estrelas", media) : "Não avaliado"
            );
        });
        tabela.getColumns().add(colAvaliacao);

        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // ============================
    // MÉTODO AUXILIAR
    // ============================

    /**
     * Método auxiliar para adicionar uma coluna genérica a uma TableView.
     *
     * @param tabela     A tabela à qual a coluna será adicionada.
     * @param titulo     O título da coluna exibido na interface.
     * @param propriedade A propriedade do objeto que será mapeada na coluna.
     * @param <T>        O tipo de dado exibido na tabela.
     */
    private static <T> void adicionarColuna(TableView<T> tabela, String titulo, String propriedade) {
        TableColumn<T, ?> coluna = new TableColumn<>(titulo);
        coluna.setCellValueFactory(new PropertyValueFactory<>(propriedade));
        tabela.getColumns().add(coluna);
    }
}
