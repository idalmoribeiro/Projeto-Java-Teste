package br.com.musica.tests;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.musica.classes.AvaliacaoNaoElegivel;
import br.com.musica.classes.AvaliacaoSimples;
import br.com.musica.classes.Curso;
import br.com.musica.classes.CursoGuitarra;
import br.com.musica.classes.CursoMusicaFactory;
import br.com.musica.classes.GestaoCurso;
import br.com.musica.interfaces.Avaliacao;

public class ExecucaoCursoTest {

    @Test
    void executarCurso_idInexistente_retornaNull() {
        GestaoCurso gestao = new GestaoCurso(new CursoMusicaFactory());

        Curso curso = gestao.buscarPorId(999);

        assertNull(curso);
    }

    @Test
    void avaliacao_quandoPercentualAtinge75_retornaAvaliacaoSimples() {
        Curso curso = new CursoGuitarra("CursoGuitarra", 8, 1, "Ana");
        curso.setPercentualConcluido(0.75);

        Avaliacao avaliacao = curso.provafinal();

        assertTrue(avaliacao instanceof AvaliacaoSimples);
    }

    @Test
    void avaliacao_quandoTemMinimoAlunos_retornaAvaliacaoSimples() {
        Curso curso = new CursoGuitarra("CursoGuitarra", 8, 5, "Ana");
        curso.setPercentualConcluido(0.5);

        Avaliacao avaliacao = curso.provafinal();

        assertTrue(avaliacao instanceof AvaliacaoSimples);
    }

    @Test
    void avaliacao_quandoNaoAtingeRegras_retornaNaoElegivel() {
        Curso curso = new CursoGuitarra("CursoGuitarra", 8, 1, "Ana");
        curso.setPercentualConcluido(0.5);

        Avaliacao avaliacao = curso.provafinal();

        assertTrue(avaliacao instanceof AvaliacaoNaoElegivel);
    }
}
