package br.com.musica.classes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.musica.interfaces.IGestaoCurso;

public class GestaoCurso {

    private final CursoMusicaFactory factory;
    private final Map<Integer, Curso> cursosPorId;
    private int proximoId;

    public GestaoCurso(CursoMusicaFactory factory) {
        this.factory = factory;
        this.cursosPorId = new LinkedHashMap<Integer, Curso>();
        this.proximoId = 1;
    }

    public int criarCurso(String nome, int duracaoMeses, int numeroAlunos, String nomeProfessor) throws Exception {
        IGestaoCurso novoCurso = factory.gerarCurso(nome, duracaoMeses, numeroAlunos, nomeProfessor);
        int id = proximoId;
        cursosPorId.put(id, (Curso) novoCurso);
        proximoId++;
        return id;
    }

    public Map<Integer, Curso> listarCursos() {
        return cursosPorId;
    }

    public Curso buscarPorId(int id) {
        return cursosPorId.get(id);
    }

    public List<Integer> pesquisarPorTermo(String termo) {
        List<Integer> idsEncontrados = new ArrayList<Integer>();
        String termoNormalizado = termo == null ? "" : termo.trim().toLowerCase();

        for (Map.Entry<Integer, Curso> entry : cursosPorId.entrySet()) {
            Curso curso = entry.getValue();
            String nome = curso.getNome() == null ? "" : curso.getNome().toLowerCase();
            String professor = curso.getProfessor() == null ? "" : curso.getProfessor().toLowerCase();

            if (nome.contains(termoNormalizado) || professor.contains(termoNormalizado)) {
                idsEncontrados.add(entry.getKey());
            }
        }

        return idsEncontrados;
    }

    public boolean atualizarCurso(int id, String nome, int duracaoMeses, int numeroAlunos, String nomeProfessor) throws Exception {
        if (!cursosPorId.containsKey(id)) {
            return false;
        }

        IGestaoCurso cursoAtualizado = factory.gerarCurso(nome, duracaoMeses, numeroAlunos, nomeProfessor);
        cursosPorId.put(id, (Curso) cursoAtualizado);
        return true;
    }

    public boolean removerCurso(int id) {
        return cursosPorId.remove(id) != null;
    }
}
