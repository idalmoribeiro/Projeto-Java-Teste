package br.com.musica.classes;

import br.com.musica.interfaces.Avaliacao;

public class AvaliacaoSimples implements Avaliacao {

    @Override
    public void avaliacao() {
        System.out.println("Avaliação final executada.");
    }

}
