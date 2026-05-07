package br.com.musica.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import br.com.musica.main.Main_Musica;

public class MainMusicaConsoleTest {

    private String executarMainComEntrada(String entrada) {
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayInputStream in = new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out, true, StandardCharsets.UTF_8);

        System.setIn(in);
        System.setOut(ps);

        try {
            Main_Musica.main(new String[0]);
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
            ps.close();
        }

        return out.toString(StandardCharsets.UTF_8);
    }

    @Test
    void cadastrarCurso_exibeMensagemDeSucesso() {
        String entrada = String.join("\n",
                "1",
                "1",
                "6",
                "10",
                "Ana",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso cadastrado com sucesso. ID: 1"));
    }

    @Test
    void pesquisarCursos_semCadastro_exibeMensagemDeErro() {
        String entrada = String.join("\n",
                "3",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Nenhum curso cadastrado."));
    }

    @Test
    void atualizarCurso_idInexistente_exibeMensagemDeErro() {
        String entrada = String.join("\n",
                "4",
                "99",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso nao encontrado para o ID informado."));
    }

    @Test
    void atualizarCurso_sucesso_exibeMensagemDeConfirmacao() {
        String entrada = String.join("\n",
                "1",
                "1",
                "6",
                "10",
                "Ana",
                "4",
                "1",
                "2",
                "8",
                "9",
                "Bia",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso atualizado com sucesso."));
    }

    @Test
    void removerCurso_idInexistente_exibeMensagemDeErro() {
        String entrada = String.join("\n",
                "5",
                "10",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso nao encontrado para o ID informado."));
    }

    @Test
    void removerCurso_sucesso_exibeMensagemDeConfirmacao() {
        String entrada = String.join("\n",
                "1",
                "1",
                "6",
                "10",
                "Ana",
                "5",
                "1",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso removido com sucesso."));
    }

    @Test
    void executarCurso_idInexistente_exibeMensagemDeErro() {
        String entrada = String.join("\n",
                "6",
                "1",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Curso nao encontrado para o ID informado."));
    }

    @Test
    void executarCurso_avaliacaoAprovada_exibeMensagemDeSucesso() {
        String entrada = String.join("\n",
                "1",
                "1",
                "8",
                "1",
                "Ana",
                "6",
                "1",
                "s",
                "6",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Avaliação final executada."));
    }

    @Test
    void executarCurso_avaliacaoReprovada_exibeMensagemDeErro() {
        String entrada = String.join("\n",
                "1",
                "1",
                "8",
                "1",
                "Ana",
                "6",
                "1",
                "s",
                "5",
                "0",
                "");

        String saida = executarMainComEntrada(entrada);

        assertTrue(saida.contains("Reprovado: o curso não completou 75% da carga."));
    }
}
