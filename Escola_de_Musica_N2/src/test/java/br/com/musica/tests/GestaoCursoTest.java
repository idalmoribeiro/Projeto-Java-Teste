package br.com.musica.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.musica.classes.Curso;
import br.com.musica.classes.CursoMusicaFactory;
import br.com.musica.classes.GestaoCurso;
import br.com.musica.excecoes.Excecoes;

public class GestaoCursoTest {

    private GestaoCurso criarGestao() {
        return new GestaoCurso(new CursoMusicaFactory());
    }

    @Test
    void cadastrarCurso_valido_retornaIdEArmazenaCurso() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoGuitarra", 6, 10, "Ana");

        assertEquals(1, id);
        Curso curso = gestao.buscarPorId(id);
        assertNotNull(curso);
        assertEquals("CursoGuitarra", curso.getNome());
        assertEquals(6, curso.getDuracao_Meses());
        assertEquals(10, curso.getNumero_Alunos());
        assertEquals("Ana", curso.getProfessor());
    }

    @Test
    void cadastrarCurso_cursoInvalido_lancaExcecao() {
        GestaoCurso gestao = criarGestao();

        assertThrows(Excecoes.ExcecaoSemCurso.class,
                () -> gestao.criarCurso("CursoViolino", 6, 5, "Ana"));
    }

    @Test
    void cadastrarCurso_duracaoForaDoIntervalo_lancaExcecao() {
        GestaoCurso gestao = criarGestao();

        assertThrows(Excecoes.ExcecaoDuracao.class,
                () -> gestao.criarCurso("CursoPiano", 1, 5, "Ana"));
        assertThrows(Excecoes.ExcecaoDuracao.class,
                () -> gestao.criarCurso("CursoPiano", 13, 5, "Ana"));
    }

    @Test
    void cadastrarCurso_numeroAlunosInvalido_lancaExcecao() {
        GestaoCurso gestao = criarGestao();

        assertThrows(Excecoes.ExcecaoNumAlunos.class,
                () -> gestao.criarCurso("CursoBateria", 6, 0, "Ana"));
    }

    @Test
    void cadastrarCurso_professorVazio_lancaExcecao() {
        GestaoCurso gestao = criarGestao();

        assertThrows(Excecoes.ExcecaoProfessor.class,
                () -> gestao.criarCurso("CursoBateria", 6, 5, ""));
    }

    @Test
    void pesquisarCursos_semCadastro_retornaListaVazia() {
        GestaoCurso gestao = criarGestao();

        List<Integer> ids = gestao.pesquisarPorTermo("piano");

        assertTrue(ids.isEmpty());
    }

    @Test
    void pesquisarCursos_caseInsensitive_retornaIds() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoPiano", 6, 5, "Carlos");

        List<Integer> ids = gestao.pesquisarPorTermo("piAnO");

        assertEquals(1, ids.size());
        assertEquals(id, ids.get(0));
    }

    @Test
    void pesquisarCursos_retornaDadosCompletos() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoBateria", 8, 7, "Beatriz");

        List<Integer> ids = gestao.pesquisarPorTermo("Beatriz");
        Curso curso = gestao.buscarPorId(ids.get(0));

        assertNotNull(curso);
        assertEquals("CursoBateria", curso.getNome());
        assertEquals(8, curso.getDuracao_Meses());
        assertEquals(7, curso.getNumero_Alunos());
        assertEquals("Beatriz", curso.getProfessor());
    }

    @Test
    void atualizarCurso_idInexistente_retornaFalse() throws Exception {
        GestaoCurso gestao = criarGestao();

        boolean atualizado = gestao.atualizarCurso(99, "CursoPiano", 6, 5, "Ana");

        assertFalse(atualizado);
    }

    @Test
    void atualizarCurso_camposInvalidos_lancaExcecao() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoPiano", 6, 5, "Ana");

        assertThrows(Excecoes.ExcecaoDuracao.class,
                () -> gestao.atualizarCurso(id, "CursoPiano", 1, 5, "Ana"));
    }

    @Test
    void atualizarCurso_sucesso_substituiDados() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoGuitarra", 6, 10, "Ana");

        boolean atualizado = gestao.atualizarCurso(id, "CursoBateria", 9, 8, "Joao");
        Curso curso = gestao.buscarPorId(id);

        assertTrue(atualizado);
        assertNotNull(curso);
        assertEquals("CursoBateria", curso.getNome());
        assertEquals(9, curso.getDuracao_Meses());
        assertEquals(8, curso.getNumero_Alunos());
        assertEquals("Joao", curso.getProfessor());
    }

    @Test
    void removerCurso_idInexistente_retornaFalse() {
        GestaoCurso gestao = criarGestao();

        boolean removido = gestao.removerCurso(100);

        assertFalse(removido);
    }

    @Test
    void removerCurso_sucesso_removeCurso() throws Exception {
        GestaoCurso gestao = criarGestao();

        int id = gestao.criarCurso("CursoGuitarra", 6, 10, "Ana");

        boolean removido = gestao.removerCurso(id);

        assertTrue(removido);
        assertTrue(gestao.listarCursos().isEmpty());
    }
}
