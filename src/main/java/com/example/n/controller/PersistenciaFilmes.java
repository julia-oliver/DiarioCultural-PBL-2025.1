package com.example.n.controller;

import com.example.n.model.Filme;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por persistir e carregar listas de filmes em arquivos JSON.
 * Cada usuário possui um arquivo separado com prefixo "filmes_" no diretório "data/".
 */
public class PersistenciaFilmes {

    // Constantes de configuração
    private static final String FILMES_FILE_PREFIX = "filmes_";
    private static final String FILE_EXTENSION = ".json";
    private static final String DATA_DIRECTORY = "data/";

    // Instância configurada do Gson para serialização com indentação
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Salva a lista de filmes do usuário em um arquivo JSON no diretório "data/".
     *
     * @param nomeUsuario Nome do usuário (será usado no nome do arquivo).
     * @param filmes Lista de filmes a serem salvos.
     */
    public static void salvarFilmes(String nomeUsuario, List<Filme> filmes) {
        // Garante que o diretório "data/" exista
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            boolean dirCriado = dataDir.mkdir();
            if (!dirCriado) {
                System.err.println("❌ Falha ao criar o diretório 'data/'");
                return;
            }
        }

        String nomeArquivo = DATA_DIRECTORY + FILMES_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;

        // Tenta salvar a lista em formato JSON
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(filmes, writer);
            System.out.println("✅ Filmes salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar filmes: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de filmes do usuário a partir do arquivo JSON.
     * Se o arquivo não existir ou estiver inválido, retorna uma lista vazia.
     *
     * @param nomeUsuario Nome do usuário (usado para localizar o arquivo).
     * @return Lista de filmes carregada (ou nova lista vazia).
     */
    public static List<Filme> carregarFilmes(String nomeUsuario) {
        String nomeArquivo = DATA_DIRECTORY + FILMES_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;
        List<Filme> filmes = new ArrayList<>();

        File arquivo = new File(nomeArquivo);

        // Verifica se o arquivo existe e não está vazio
        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("ℹ️ Arquivo de filmes não encontrado ou vazio. Retornando lista vazia.");
            return filmes;
        }

        // Tenta ler e desserializar o conteúdo JSON
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Filme>>(){}.getType();
            filmes = gson.fromJson(reader, tipoLista);

            if (filmes == null) {
                filmes = new ArrayList<>();
            }

            System.out.println("✅ Filmes carregados com sucesso de: " + nomeArquivo);
        } catch (JsonSyntaxException | JsonIOException e) {
            System.out.println("⚠️ JSON inválido. Retornando lista vazia: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("⚠️ Erro ao ler arquivo. Retornando lista vazia: " + e.getMessage());
        }

        return filmes;
    }
}
