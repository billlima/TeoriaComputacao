/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import modelos.Instrucao;
import utils.InstrucaoTipoEnum;

/**
 *
 * @author kelvin
 */
public class GeradorFluxo {

    private final String PARTIDA_VAR = "st=>start: partida";
    private final String PARADA_VAR = "e=>end: parada";
    private final String P = "PARTIDA";
    private final String E = "PARADA";
    private final String S = "SETA";
    private final Integer LABEL = 2;
    private final Integer VERDADEIRO = 5;
    private final Integer FALSO = 8;

    private final List<Instrucao> instrucoesLista;
    private List<String> variaveis = new ArrayList<>();
    private HashMap<String, String> operadores = new HashMap<>();
    private boolean ultimoTipoTeste;
    
    private final String INPUT_FLOWCHART = "/tmp/entrada.flowchart";

    public StringBuilder saida;

    public GeradorFluxo(List<Instrucao> instrucoesLista) {
        this.instrucoesLista = instrucoesLista;
        this.criarOperadores();
        this.criarVariaveis();
        this.ultimoTipoTeste = false;
    }

    private void criarOperadores() {
        operadores.put("SETA", "->");
        operadores.put("PARTIDA", "st");
        operadores.put("PARADA", "e");
    }

    private String getValor(String linha, int posicao) {
        return linha.split(" ")[posicao];
    }

    private boolean isTeste(String tipo) {
        return tipo.equals(InstrucaoTipoEnum.TESTE.getTipo());
    }

    private boolean isOperacao(String tipo) {
        return tipo.equals(InstrucaoTipoEnum.OPERACAO.getTipo());
    }

    private void add(Instrucao i, String operacao) {
        if (variavelNaoExiste(operacao)) {
            if (isOperacao(i.getTipo())) {
                addOperacao(operacao);
            }

            if (isTeste(i.getTipo())) {
                addTeste(operacao);
            }
        }
    }

    private void addOperacao(String operacao) {
        operadores.put(operacao.toLowerCase(), operacao.toLowerCase());
        variaveis.add(operacao.toLowerCase() + "=>operation: " + operacao);
    }

    private void addTeste(String operacao) {
        operadores.put(operacao.toLowerCase(), operacao.toLowerCase());
        variaveis.add(operacao.toLowerCase() + "=>condition: " + operacao);
    }

    private boolean variavelNaoExiste(String variavel) {
        return !operadores.containsKey(variavel);
    }

    private void criarVariaveis() {
        variaveis.add(PARTIDA_VAR);
        variaveis.add(PARADA_VAR);

        instrucoesLista.forEach((i) -> {
            add(i, getValor(i.getStringLinha(), LABEL));
        });
    }

    private void addOperadorFaca(Instrucao i) {
        if (isOperacao(i.getTipo())) {
            saida.append(operadores.get(getValor(i.getStringLinha(), LABEL).toLowerCase()));
            saida.append(operadores.get(S));
            ultimoTipoTeste = false;
        }
    }

    private boolean isParada(int indice) {
        return (indice > (instrucoesLista.size() - 1));
    }

    private void addOperacaoVerdadeira(int operacaoTrue, String operacao) {
        saida.append(operadores.get(operacao)).append("(yes)");
        saida.append(operadores.get(S));
            
        if (isParada(operacaoTrue)) {
            saida.append(operadores.get(E)).append("\n");
        } else {
            saida.append(getValor(instrucoesLista.get(operacaoTrue).getStringLinha(), LABEL).toLowerCase()).append("\n");
        }
    }

    private void addOperacaoFalsa(int operacaoFalse, String operacao) {
        saida.append(operadores.get(operacao)).append("(no)");
        saida.append(operadores.get(S));
            
        if (isParada(operacaoFalse)) {    
            saida.append(operadores.get(E)).append("\n");
        } else {
            saida.append(getValor(instrucoesLista.get(operacaoFalse).getStringLinha(), LABEL).toLowerCase()).append("\n");
        }
    }

    private void addOperadorTeste(Instrucao i, String operacao) {
        if (isTeste(i.getTipo())) {
            if (!ultimoTipoTeste) {
                saida.append(operadores.get(operacao)).append("\n");
            }

            int operacaoTrue = Integer.parseInt(getValor(i.getStringLinha(), VERDADEIRO)) - 1;
            addOperacaoVerdadeira(operacaoTrue, operacao);

            int operacaoFalse = Integer.parseInt(getValor(i.getStringLinha(), FALSO)) - 1;
            addOperacaoFalsa(operacaoFalse, operacao);

            ultimoTipoTeste = true;
        }
    }

    public void gerar() {
        PrintWriter writer = null;
        try {
            
            saida = new StringBuilder();
            variaveis.forEach((linha) -> {
                saida.append(linha).append("\n");
            }); saida.append(operadores.get(P)).append(operadores.get(S));
            instrucoesLista.stream().map((i) -> {
                addOperadorFaca(i);
                return i;
            }).forEachOrdered((i) -> {
                addOperadorTeste(i, getValor(i.getStringLinha(), LABEL).toLowerCase());
            }); 
            
            writer = new PrintWriter(INPUT_FLOWCHART, "UTF-8");
            writer.print(saida.toString());
            writer.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("deu ruim");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("deu ruim");
        } finally {
            writer.close();
        }
    }

}
