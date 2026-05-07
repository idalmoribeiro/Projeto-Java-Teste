package br.com.musica.classes;

import br.com.musica.interfaces.Avaliacao;

public class AvaliacaoNaoElegivel implements Avaliacao {
    private final String motivo;

    public AvaliacaoNaoElegivel(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public void avaliacao() {
        System.out.println("Avaliação não disponível: " + motivo);
    }
}
