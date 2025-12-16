package com.example.n.controller;

import com.example.n.model.Serie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por salvar e carregar listas de séries
 * em arquivos JSON específicos para cada usuário no diretório "data/".
 */
public class PersistenciaSeries {

    // Constantes de configuração de arquivos
    private static final String SERIES_FILE_PREFIX = "series_";
    private static final String FILE_EXTENSION = ".json";
    private static final String DATA_DIRECTORY = "data/";

    // Instância do Gson configurada com indentação para arquivos legíveis
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Salva a lista de séries do usuário em um arquivo JSON.
     *
     * @param nomeUsuario Nome do usuário (usado para formar o nome do arquivo).
     * @param series Lista de séries a ser salva.
     */
    public static void salvarSeries(String nomeUsuario, List<Serie> series) {
        // Garante que o diretório "data/" exista
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            boolean dirCriado = dataDir.mkdir();
            if (!dirCriado) {
                System.err.println("❌ Falha ao criar o diretório 'data/'");
                return;
            }
        }

        String nomeArquivo = DATA_DIRECTORY + SERIES_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;

        // Serializa e salva a lista de séries no arquivo
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(series, writer);
            System.out.println("✅ Séries salvas com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar séries: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de séries do usuário a partir do arquivo JSON.
     * Se o arquivo estiver ausente, inválido ou vazio, retorna uma lista vazia.
     *
     * @param nomeUsuario Nome do usuário (usado para localizar o arquivo).
     * @return Lista de séries carregada (ou lista vazia em caso de erro).
     */
    public static List<Serie> carregarSeries(String nomeUsuario) {
        String nomeArquivo = DATA_DIRECTORY + SERIES_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;
        List<Serie> series = new ArrayList<>();

        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe e possui conteúdo
        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("ℹ️ Arquivo de séries não encontrado ou vazio. Retornando lista vazia.");
            return series;
        }

        // Tenta desserializar o conteúdo JSON
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Serie>>() {}.getType();
            series = gson.fromJson(reader, tipoLista);

            if (series == null) {
                series = new ArrayList<>();
            }

            System.out.println("✅ Séries carregadas com sucesso de: " + nomeArquivo);
        } catch (JsonSyntaxException | JsonIOException e) {
            System.out.println("⚠️ JSON inválido. Retornando lista vazia: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("⚠️ Erro ao ler arquivo. Retornando lista vazia: " + e.getMessage());
        }

        return series;
    }
}
