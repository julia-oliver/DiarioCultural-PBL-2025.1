package com.example.n.controller;

import com.example.n.model.Livro;
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
 * Classe responsável por salvar e carregar listas de livros
 * para arquivos JSON específicos de cada usuário no diretório "data/".
 */
public class PersistenciaLivros {

    // Constantes de configuração de arquivos
    private static final String LIVROS_FILE_PREFIX = "livros_";
    private static final String FILE_EXTENSION = ".json";
    private static final String DATA_DIRECTORY = "data/";

    // Instância configurada do Gson para serialização legível
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    /**
     * Salva a lista de livros do usuário em um arquivo JSON.
     *
     * @param nomeUsuario Nome do usuário (usado para formar o nome do arquivo).
     * @param livros Lista de livros a ser salva.
     */
    public static void salvarLivros(String nomeUsuario, List<Livro> livros) {
        // Garante que o diretório "data/" existe
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            boolean dirCriado = dataDir.mkdir();
            if (!dirCriado) {
                System.err.println("❌ Falha ao criar o diretório 'data/'");
                return;
            }
        }

        String nomeArquivo = DATA_DIRECTORY + LIVROS_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;

        // Serializa e grava os livros no arquivo
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            gson.toJson(livros, writer);
            System.out.println("✅ Livros salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar livros: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de livros do usuário a partir do arquivo JSON.
     * Se o arquivo estiver ausente, inválido ou vazio, retorna uma lista vazia.
     *
     * @param nomeUsuario Nome do usuário (usado para localizar o arquivo).
     * @return Lista de livros carregada (ou lista vazia em caso de erro).
     */
    public static List<Livro> carregarLivros(String nomeUsuario) {
        String nomeArquivo = DATA_DIRECTORY + LIVROS_FILE_PREFIX + nomeUsuario.toLowerCase() + FILE_EXTENSION;
        List<Livro> livros = new ArrayList<>();

        File arquivo = new File(nomeArquivo);

        // Se o arquivo não existir ou estiver vazio
        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("ℹ️ Arquivo de livros não encontrado ou vazio. Retornando lista vazia.");
            return livros;
        }

        // Tenta desserializar o conteúdo do arquivo
        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<ArrayList<Livro>>() {}.getType();
            livros = gson.fromJson(reader, tipoLista);

            if (livros == null) {
                livros = new ArrayList<>();
            }

            System.out.println("✅ Livros carregados com sucesso de: " + nomeArquivo);
        } catch (JsonSyntaxException | JsonIOException e) {
            System.out.println("⚠️ JSON inválido. Retornando lista vazia: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("⚠️ Erro ao ler arquivo. Retornando lista vazia: " + e.getMessage());
        }

        return livros;
    }
}
