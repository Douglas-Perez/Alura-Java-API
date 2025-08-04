package br.com.alura.screenmatch.excecao;

public class ConversaoAnoExeption extends RuntimeException {
    public String mensagem;

    public ConversaoAnoExeption(String s) {
        this.mensagem = s;
    }

    @Override
    public String getMessage() {
        return this.mensagem;
    }
}
