/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
    
    public GeradorFluxoNovo(List<Instrucao> lista)  {
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
        return ultimaOperacaoTeste ? "" : 
                (ultimaOperacao + " " + SETA + " " + getOperacao(operacao) + QUEBRA_LINHA);
    }
    
    private String getCabecalho() {
        return GRAFICO + QUEBRA_LINHA + PARTIDA + QUEBRA_LINHA;
    }
    
    private String getStrTeste(String ultimaOperacao, String operacao, boolean ultimaOperacaoTeste) {
        return  ultimaOperacaoTeste ? "" : 
                (ultimaOperacao + " " + SETA + " " + getTeste(operacao) + QUEBRA_LINHA);
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
        String ultimaOperacaoPrintada = PARTIDA;
        boolean ultimaOperacaoTeste = false;
        
        for (Instrucao instrucao : instrucoes) {
            if (isOperacao(instrucao.getTipo())) {
                String operacao = getValor(instrucao.getStringLinha(), 2);
                fluxo.append(getStrOperacao(ultimaOperacaoPrintada, operacao, ultimaOperacaoTeste));
                ultimaOperacaoPrintada = operacao;
                ultimaOperacaoTeste = false;
            } else {
                String operacao = getValor(instrucao.getStringLinha(), 2);
                
                fluxo.append(getStrTeste(ultimaOperacaoPrintada, operacao, ultimaOperacaoTeste));
                fluxo.append(adicionarOperacaoTeste(operacao, instrucao, true));
                fluxo.append(adicionarOperacaoTeste(operacao, instrucao, false));
                
                ultimaOperacaoPrintada = operacao;
                ultimaOperacaoTeste = true;
            }
        }
        
        criarArquivo(fluxo.toString());
        
        return cliente.gerarImagemFluxo(INPUT).sucesso();
    }
    
}
