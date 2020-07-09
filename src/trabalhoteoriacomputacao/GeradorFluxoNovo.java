/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import modelos.Instrucao;
import utils.InstrucaoTipoEnum;

/**
 *
 * @author kelvin
 */
public class GeradorFluxoNovo {

    private final List<Instrucao> instrucoes;
    private final String PARTIDA = "partida(partida)";
    private final String SETA = "-->";
    private final String PARADA = "parada(parada)";
    private final String OP_VERDADE = "|sim|";
    private final String OP_FALSA = "|nao|";
    private final String GRAFICO = "graph TD";
    private final String QUEBRA_LINHA = "\n";
    private final String INPUT = "/tmp/input";
    private final MermaidCli cliente;

    public GeradorFluxoNovo(List<Instrucao> lista) {
        this.instrucoes = lista;
        this.cliente = new MermaidCli();
    }

    private boolean isOperacao(String tipo) {
        return InstrucaoTipoEnum.OPERACAO.getTipo().equals(tipo);
    }

    private String getValor(String linha, Integer posicao) {
        return linha.split(" ")[posicao];
    }

    private int getValorVerdadeiro(String linha) {
        return Integer.parseInt(linha.split(" ")[5]) - 1;
    }

    private int getValorFalse(String linha) {
        return Integer.parseInt(linha.split(" ")[8]) - 1;
    }

    private boolean naoEhParada(int posicao) {
        return posicao >= 0 && posicao < instrucoes.size();
    }

    private String getTeste(String rotulo) {
        return rotulo + "{" + rotulo + "}";
    }

    private String getOperacao(String rotulo) {
        return rotulo + "[" + rotulo + "]";
    }

    private String getStrOperacao(String ultimaOperacao, String operacao, boolean ultimaOperacaoTeste) {
        return ultimaOperacaoTeste ? ""
                : (ultimaOperacao + " " + SETA + " " + getOperacao(operacao) + QUEBRA_LINHA);
    }

    private String getCabecalho() {
        return GRAFICO + QUEBRA_LINHA + PARTIDA + QUEBRA_LINHA;
    }

    private String getStrTeste(String ultimaOperacao, String operacao, boolean ultimaOperacaoTeste) {
        return ultimaOperacaoTeste ? ""
                : (ultimaOperacao + " " + SETA + " " + getTeste(operacao) + QUEBRA_LINHA);
    }

    private String getStrTesteCasos(String origem, String opTipo, String destino) {
        return getTeste(origem) + " " + SETA + " " + opTipo + " " + destino + QUEBRA_LINHA;
    }

    private String adicionarOperacaoTeste(String operacao, Instrucao instrucao, boolean operacaoVerdadeira) {
        int posicao = operacaoVerdadeira ? getValorVerdadeiro(instrucao.getStringLinha()) : getValorFalse(instrucao.getStringLinha());
        String tipo = operacaoVerdadeira ? OP_VERDADE : OP_FALSA;

        if (naoEhParada(posicao)) {
            String operacaoTeste = getValor(instrucoes.get(posicao).getStringLinha(), 2);
            return getStrTesteCasos(operacao, tipo, operacaoTeste);
        } else {
            return getStrTesteCasos(operacao, tipo, PARADA);
        }
    }

    private void criarArquivo(String fluxo) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(INPUT, "UTF-8");
        writer.print(fluxo);
        writer.close();
    }

    public boolean gerar() throws FileNotFoundException, UnsupportedEncodingException, Exception {
        StringBuilder fluxo = new StringBuilder();

        fluxo.append(getCabecalho());
        fluxo.append("partida --> ");

        if (isOperacao(instrucoes.get(0).getTipo())) {
            fluxo.append(instrucoes.get(0).getResultado(true)[0]);
        } else {
            fluxo.append(instrucoes.get(0).getRotuloTeste());
        }
        fluxo.append(QUEBRA_LINHA);

        String ultimaOperacaoPrintada = PARTIDA;
        boolean ultimaOperacaoTeste = false;

        for (Instrucao instrucao : instrucoes) {
            if (isOperacao(instrucao.getTipo())) {
                fluxo.append(instrucao.getResultado(true)[0]);
                fluxo.append(" --> ");

                Instrucao i = getInstrucaoPorRotuloLinha(instrucao.getResultadoVerdadeiro()[1]);
                if (i == null) {
                    fluxo.append("parada(parada)");
                } else {
                    if (isOperacao(i.getTipo())) {
                        fluxo.append(i.getResultadoVerdadeiro()[0]);
                    } else {
                        fluxo.append(createTeste(i.getRotuloTeste()));
                    }
                }
                fluxo.append(QUEBRA_LINHA);
            } else {
                for (boolean resultado : new boolean[]{true, false}) {
                    fluxo.append(createTeste(instrucao.getRotuloTeste()));
                    fluxo.append(" --> | ");
                    fluxo.append(resultado ? "sim" : "nao");
                    fluxo.append(" | ");

                    Instrucao i = getInstrucaoPorRotuloLinha(instrucao.getResultado(resultado)[1]);
                    if (i == null) {
                        fluxo.append("parada(parada)");
                    } else {
                        if (isOperacao(i.getTipo())) {
                            fluxo.append(i.getResultado(resultado)[0]);
                        } else {
                            fluxo.append(createTeste(i.getRotuloTeste()));
                        }
                    }
                    fluxo.append(QUEBRA_LINHA);
                }
            }
        }

        criarArquivo(fluxo.toString());

        return cliente.gerarImagemFluxo(INPUT).sucesso();
    }

    private String createTeste(String rotulo) {
        return rotulo + "{" + rotulo + "}";
    }

    private Instrucao getInstrucaoPorRotuloLinha(String rotuloLinha) {
        try {
            return instrucoes.stream().filter(item -> item.getRotuloLinha().equals(rotuloLinha)).collect(Collectors.toList()).get(0);
        } catch (Exception e) {
            return null;
        }
    }

}
