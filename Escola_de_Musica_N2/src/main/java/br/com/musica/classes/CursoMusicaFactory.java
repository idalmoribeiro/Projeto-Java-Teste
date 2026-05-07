package br.com.musica.classes;

import br.com.musica.excecoes.Excecoes;
import br.com.musica.excecoes.Excecoes.ExcecaoDuracao;
import br.com.musica.excecoes.Excecoes.ExcecaoNumAlunos;
import br.com.musica.excecoes.Excecoes.ExcecaoProfessor;
import br.com.musica.interfaces.IGestaoCurso;

public class CursoMusicaFactory {
	
	public IGestaoCurso gerarCurso (String Nome, int duracaoMeses, int numeroAlunos, String NomeProfessor) throws Excecoes.ExcecaoSemCurso, ExcecaoDuracao, ExcecaoNumAlunos, ExcecaoProfessor {
		// validações simples: lançar exceções para argumentos inválidos
		// evitar NomeProfessor.equals(null) — isso lança NullPointerException quando NomeProfessor é null
		if (NomeProfessor == null || NomeProfessor.trim().isEmpty()) throw new Excecoes.ExcecaoProfessor();

		if (Nome == null) throw new Excecoes.ExcecaoSemCurso();
		
		if (duracaoMeses < 2 || duracaoMeses > 12) throw new Excecoes.ExcecaoDuracao();
		
		if (numeroAlunos <= 0) throw new Excecoes.ExcecaoNumAlunos();


		switch (Nome) {
			case "CursoGuitarra":
				return new CursoGuitarra(Nome, duracaoMeses, numeroAlunos, NomeProfessor);
			case "CursoPiano":
				return new CursoPiano(Nome, duracaoMeses, numeroAlunos, NomeProfessor);
			case "CursoBateria":
				return new CursoBateria(Nome, duracaoMeses, numeroAlunos, NomeProfessor);
			default:
				throw new Excecoes.ExcecaoSemCurso();
		}
	}
	
}
