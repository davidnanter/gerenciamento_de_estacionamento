package gerenciamento_de_estacionamento;

public class Vaga {
    private int numero;
    private String tamanho;
    private boolean disponibilidade;

    public Vaga(int numero, String tamanho, boolean disponibilidade) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.disponibilidade = disponibilidade;
    }

    public int getNumero() {
        return numero;
    }
    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTamanho() {
        return tamanho;
    }
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }
    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }
}
