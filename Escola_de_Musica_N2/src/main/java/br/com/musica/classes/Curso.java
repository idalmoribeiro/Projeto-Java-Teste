package br.com.musica.classes;

import br.com.musica.interfaces.Avaliacao;
import br.com.musica.interfaces.IGestaoCurso;

public abstract class Curso implements IGestaoCurso{

	//atributos
	private String Nome;
	private int Duracao_Meses;
	private int Numero_Alunos;
	private String Professor;

	// percentual de carga horária concluída (0.0 - 1.0)
	private double percentualConcluido = 0.0;

	// mínimo de alunos para liberar prova
	private static int MIN_ALUNOS = 5;
	
	//construtores
	public Curso(String nome, int duracao_Meses, int numero_Alunos, String professor) {
		super();
		Nome = nome;
		Duracao_Meses = duracao_Meses;
		Numero_Alunos = numero_Alunos;
		Professor = professor;
	}
	
	public Avaliacao avaliar() {
		return null;
	}

	//getters e setters
	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public int getDuracao_Meses() {
		return Duracao_Meses;
	}

	public void setDuracao_Meses(int duracao_Meses) {
		Duracao_Meses = duracao_Meses;
	}

	public int getNumero_Alunos() {
		return Numero_Alunos;
	}

	public void setNumero_Alunos(int numero_Alunos) {
		Numero_Alunos = numero_Alunos;
	}

	public String getProfessor() {
		return Professor;
	}

	public void setProfessor(String professor) {
		Professor = professor;
	}	

	public double getPercentualConcluido() {
		return percentualConcluido;
	}

	public void setPercentualConcluido(double percentualConcluido) {
		
		//tratamento do valor percentual para evitar valores inválidos
		if (percentualConcluido < 0.0) percentualConcluido = 0.0;
		if (percentualConcluido > 1.0) percentualConcluido = 1.0;
		this.percentualConcluido = percentualConcluido;
	}

	public boolean podeRealizarProvaFinal() {

		//validação para verificar se o curso já completou 75% da carga ou se possui alunos suficientes
		return this.percentualConcluido >= 0.75 || this.getNumero_Alunos() >= MIN_ALUNOS;
	}
}
