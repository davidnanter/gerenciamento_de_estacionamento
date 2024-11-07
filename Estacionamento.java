package gerenciamento_de_estacionamento;
import java.util.ArrayList;
import java.util.List;

public class Estacionamento {
    private List<Vaga> vagas;
    private List<Veiculo> veiculos; 
    private List<Registro> registros;

   
    public Estacionamento() {
        vagas = new ArrayList<>();
        veiculos = new ArrayList<>(); 
        registros = new ArrayList<>();
    }

    
    public Vaga buscarVagaDisponivel(String tamanho) {
        for (Vaga vaga : vagas) {
            if (vaga.isDisponibilidade() && vaga.getTamanho().equalsIgnoreCase(tamanho)) {
                return vaga;
            }
        }
        return null;
    }

    
    public List<Vaga> getVagas() {
        return vagas;
    }

    public List<Veiculo> getVeiculos() {
        return veiculos; 
    }

    public List<Registro> getRegistros() {
        return registros;
    }
}

