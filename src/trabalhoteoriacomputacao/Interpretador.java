/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import modelos.Instrucao;
import utils.InstrucaoTipoEnum;

/**
 *
 * @author mlima
 */
public class Interpretador {

    List<Instrucao> instrucoesLista;
    List<String> deteccaoCicloInfinitoLista;
    public boolean temCiclo = false;

    public List<Instrucao> interpretarProgramaMonolitico(List<String> linhasPrograma) throws Exception {
        
        this.instrucoesLista = converterLinhasParaInstrucoes(linhasPrograma);

        instrucoesLista = instrucoesLista.stream().map(i -> definirResultadosInstrucao1(i)).collect(Collectors.toList());
        definirParadas();
        definirResultadosInstrucao2();        
        
        return instrucoesLista;
    }

    /**
     * Define os resultados verdadeiros e falsos, se for do tipo Teste e o teste
     * preenche somente o rótulo da linha destino
     *
     * @param i
     * @return
     */
    private Instrucao definirResultadosInstrucao1(Instrucao i) {
        String[] palavras = removeRotuloInstrucao(i.getStringLinha());

        if (i.getTipo().equals(InstrucaoTipoEnum.OPERACAO.getTipo())) {
            i = definirResultadosIstrucaoOperacao(i, palavras);
        } else {
            i = definirRotuloEResultadoFalsoInstrucaoTeste(i, palavras);
        }

        return i;
    }

    /**
     * Define os as operacoes nos resultados das instrucoes do tipo e detecção de ciclo
     *
     * @param i
     * @return
     */
    private void definirResultadosInstrucao2() throws Exception {
        for (Instrucao i : instrucoesLista) {
            if (i.getTipo().equals(InstrucaoTipoEnum.OPERACAO.getTipo())) {
                if (i.getResultado(true)[1].equals(i.getRotuloLinha())) {
                    temCiclo = true;
                    i.setResultado(true, "ciclo", "w");
                }
                if (i.getResultado(false)[1].equals(i.getRotuloLinha())) {
                    temCiclo = true;
                    i.setResultado(false, "ciclo", "w");
                }
                
            } else {
                for (boolean resultado : new boolean[]{true, false}) {

                    if (i.getResultado(resultado)[0].equals("")) {
                        deteccaoCicloInfinitoLista = new ArrayList<>();

                        String res = getInstrucaoOperacaoResultado(resultado, i.getRotuloLinha());

                        switch (res) {
                            case "e":
                                i.setResultado(resultado, "parada", "e");
                                break;
                            case "e*":
                                temCiclo = true;
                                i.setResultado(resultado, "ciclo", "w");
                                break;
                            default:
                                i.setResultado(resultado, res, i.getResultado(resultado)[1]);
                        }
                    }
                }
            }
        }
    }

    private String getInstrucaoOperacaoResultado(boolean resultado, String rotuloLinha) throws Exception {
        if (deteccaoCicloInfinitoLista.contains(rotuloLinha)) {
            return "e*";
        }

        deteccaoCicloInfinitoLista.add(rotuloLinha);

        Instrucao i = buscarInstrucaoPorRotulo(instrucoesLista, rotuloLinha);
        
        if (i.getTipo().equals(InstrucaoTipoEnum.OPERACAO.getTipo())) {
           return i.getResultado(resultado)[0];
        }
        if (i.isResultadoParada(resultado)) {
            return "e";
        }
        if (i.getResultado(resultado)[0].equals("")) {
            return getInstrucaoOperacaoResultado(resultado, i.getResultado(resultado)[1]);
        } else {
            return i.getResultado(resultado)[0];
        }
    }

    private void definirParadas() throws Exception {
        for (Instrucao instrucao : instrucoesLista) {
            instrucao.setResultadoVerdadeiroParada(buscarInstrucaoPorRotulo(instrucoesLista, instrucao.getResultadoVerdadeiro()[1]) == null);
            instrucao.setResultadoFalsoParada(buscarInstrucaoPorRotulo(instrucoesLista, instrucao.getResultadoFalso()[1]) == null);
        }
    }

    private Instrucao definirResultadosIstrucaoOperacao(Instrucao i, String[] palavras) {
        i.setResultado(true, palavras[1], palavras[3]);
        i.setResultado(false, palavras[1], palavras[3]);

        return i;
    }

    private Instrucao definirRotuloEResultadoFalsoInstrucaoTeste(Instrucao i, String[] palavras) {
        i.setRotuloTeste(palavras[1]);
        i.setResultado(true, "", palavras[4]);
        i.setResultado(false, "", palavras[7]);

        return i;
    }

    private List<Instrucao> converterLinhasParaInstrucoes(List<String> linhas) {
        return linhas.stream().map(linha -> converterLinhaParaInstrucao1(linha)).collect(Collectors.toList());
    }

    private Instrucao converterLinhaParaInstrucao1(String linha) {
        linha = linha.replaceAll(";", "");
        
        Instrucao i = new Instrucao();

        i.setStringLinha(linha);

        i.setRotuloLinha(getRotuloLinha(linha));
        i.setTipo(getTipoInstrucaoPreliminar(linha));

        return i;
    }

    private String getRotuloLinha(String linha) {
        return linha.split(":")[0];
    }

    /**
     * Busca primeiro se é TESTE ou OPERACAO
     *
     * @param linha
     * @return
     */
    private String getTipoInstrucaoPreliminar(String linha) {
        String t = removeRotuloInstrucao(linha)[0];

        if (t.toLowerCase().equals("se")) {
            return InstrucaoTipoEnum.TESTE.getTipo();
        } else {
            return InstrucaoTipoEnum.OPERACAO.getTipo();
        }
    }

    private String[] removeRotuloInstrucao(String linha) {
        //Remove rótulo inicial e após espaços em branco no início e fim
        String t = linha.split(":")[1].trim();
        //Divide por espaço vazio
        return t.split(" ");
    }

    Instrucao buscarInstrucaoPorRotulo(List<Instrucao> instrucoes, String rotulo) throws Exception {
        List<Instrucao> lista = instrucoes.stream().filter(i -> i.getRotuloLinha().equals(rotulo)).collect(Collectors.toList());

        if (lista.size() > 1) {
            throw new Exception("Rótulo da linha " + rotulo + " especificado mais de uma vez");
        }

        return lista.isEmpty() ? null : lista.get(0);
    }

}
