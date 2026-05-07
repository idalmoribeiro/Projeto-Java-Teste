package br.com.musica.excecoes;

public class Excecoes {

    public static class ExcecaoSemCurso extends Exception {
        public ExcecaoSemCurso() {
            super("O curso informado não existe no banco de dados");
        }
    }

    public static class ExcecaoNumAlunos extends Exception {
        public ExcecaoNumAlunos() {
            super("O numero de alunos informado é inválido");
        }
    }

    public static class ExcecaoDuracao extends Exception {
        public ExcecaoDuracao() {
            super("Duração de curso inválida");
        }
    }

    public static class ExcecaoProfessor extends Exception {
        public ExcecaoProfessor() {
            super("O professor informado não é registrado no banco de dados.");
        }
    }

}
