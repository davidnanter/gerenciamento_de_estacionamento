package gerenciamento_de_estacionamento;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Principal {
    private static Estacionamento estacionamento = new Estacionamento();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarVaga();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    registrarEntradaVeiculo();
                    break;
                case 4:
                    registrarSaidaVeiculo();
                    break;
                case 5:
                    gerarRelatorioVagasOcupadas();
                    break;
                case 6:
                    gerarHistoricoVeiculos();
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n--- Sistema de Gerenciamento de Estacionamento ---");
        System.out.println("1 - Cadastrar Vaga");
        System.out.println("2 - Cadastrar Veículo");
        System.out.println("3 - Registrar Entrada de Veículo");
        System.out.println("4 - Registrar Saída de Veículo");
        System.out.println("5 - Gerar Relatório de Vagas Ocupadas");
        System.out.println("6 - Gerar Histórico de Veículos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarVaga() {
        int numero = lerNumero("Número da vaga: ");
        String tamanho = lerString("Tamanho da vaga (pequeno, médio, grande): ");
        Vaga vaga = new Vaga(numero, tamanho, true);
        estacionamento.getVagas().add(vaga);
        System.out.println("Vaga cadastrada com sucesso!");
    }

    private static void cadastrarVeiculo() {
        String placa = lerString("Placa do veículo: ");
        String modelo = lerString("Modelo do veículo: ");
        String tamanho = lerString("Tamanho do veículo (pequeno, médio, grande): ");
        Veiculo veiculo = new Veiculo(placa, modelo, tamanho, null, null);
        estacionamento.getVeiculos().add(veiculo); 
        System.out.println("Veículo cadastrado com sucesso!");
    }

    private static void registrarEntradaVeiculo() {
        String placa = lerString("Placa do veículo: ");
        String modelo = lerString("Modelo do veículo: ");
        String tamanho = lerString("Tamanho do veículo (pequeno, médio, grande): ");
        Vaga vagaDisponivel = estacionamento.buscarVagaDisponivel(tamanho);

        if (vagaDisponivel != null) {
            Veiculo veiculo = new Veiculo(placa, modelo, tamanho, LocalDateTime.now(), null);
            Registro registro = new Registro(veiculo, vagaDisponivel, LocalDateTime.now(), null, 0);
            estacionamento.getRegistros().add(registro);
            vagaDisponivel.setDisponibilidade(false);
            System.out.println("Entrada registrada com sucesso!");
        } else {
            System.out.println("Não há vaga disponível para o tamanho do veículo.");
        }
    }

    private static void registrarSaidaVeiculo() {
        String placa = lerString("Placa do veículo: ");

        for (Registro registro : estacionamento.getRegistros()) {
            if (registro.getVeiculo().getPlaca().equalsIgnoreCase(placa) && registro.getHoraSaida() == null) {
                registro.setHoraSaida(LocalDateTime.now());
                long tempoPermanencia = registro.calcularTempoPermanencia();
                double valorPago = estacionamento.calcularValorPagamento(tempoPermanencia);
                registro.setValorPago(valorPago);
                registro.getVaga().setDisponibilidade(true);
                System.out.printf("Saída registrada! Tempo de permanência: %d minutos. Valor a ser pago: R$ %.2f\n",
                        tempoPermanencia, valorPago);
                return;
            }
        }
        System.out.println("Veículo não encontrado ou já saiu.");
    }

    private static void gerarRelatorioVagasOcupadas() {
        System.out.println("\n--- Vagas Ocupadas ---");
        for (Vaga vaga : estacionamento.getVagas()) {
            if (!vaga.isDisponibilidade()) {
                System.out.printf("Número da Vaga: %d, Tamanho: %s\n", vaga.getNumero(), vaga.getTamanho());
            }
        }
    }

    private static void gerarHistoricoVeiculos() {
        System.out.println("\n--- Histórico de Veículos ---");
        for (Registro registro : estacionamento.getRegistros()) {
            System.out.printf("Placa do Veículo: %s, Entrada: %s, Saída: %s, Valor Pago: R$ %.2f\n",
                    registro.getVeiculo().getPlaca(),
                    registro.getHoraEntrada(),
                    registro.getHoraSaida() != null ? registro.getHoraSaida() : "Em aberto",
                    registro.getValorPago());
        }
    }

    private static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerNumero(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextInt();
    }
}
