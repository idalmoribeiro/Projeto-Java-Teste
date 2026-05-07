package br.com.musica.classes;
import br.com.musica.interfaces.Avaliacao;
public class CursoGuitarra extends Curso  {

	//construtores

	public CursoGuitarra(String nome, int duracao_Meses, int numero_Alunos, String professor) {
		super(nome, duracao_Meses, numero_Alunos, professor);
		
	}

	//métodos abstratos herdados da Classe Curso

	@Override
	public void iniciarCurso() {
		// iniciar lógica do curso de guitarra (sem I/O)
	}

	@Override
	public void encerrarCurso() {
		// encerrar lógica do curso de guitarra (sem I/O)
	}

	@Override
	public Avaliacao provafinal() {
		if (podeRealizarProvaFinal()) {
			return new AvaliacaoSimples();

		} else {
			return new AvaliacaoNaoElegivel("O curso não completou 75% da carga ou não possui alunos suficientes.");
		}
	}


	
}
