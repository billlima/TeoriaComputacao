/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    public boolean gerouGrafico = false;
    
    public List<Instrucao> interpretarProgramaMonolitico(List<String> linhasPrograma) throws Exception {
        
        validarLinhas(linhasPrograma);

        this.instrucoesLista = converterLinhasParaInstrucoes(linhasPrograma);

        instrucoesLista = instrucoesLista.stream().map(i -> definirResultadosEtapa1(i)).collect(Collectors.toList());
        
        GeradorFluxoNovo geradorFluxoNovo = new GeradorFluxoNovo(instrucoesLista);
        gerouGrafico = geradorFluxoNovo.gerar();
        
        definirParadas();
        definirResultadosInstrucaoTeste();

        return instrucoesLista;
    }
    
    private void validarLinhas(List<String> linhas) throws Exception {
        for (int x = 0; x < linhas.size(); x++) {
            
            String linha = linhas.get(x);            
            String[] linhaSplit = linha.split(" ");
            
            if (linhaSplit.length != 5 && linhaSplit.length != 9) {
                throw new Exception("Linha " + (x+1) + " inválida!");
            }
            
            if (linhaSplit[0].split(":").length != 1) {
                throw new Exception("Linha " + (x+1) + " inválida: Rótulo inválido");
            }
            
            if (linhaSplit.length == 5) {
                validarPalavra(linhaSplit[1], new String[]{"faça", "faca"}, x + 1);
                validarPalavra(linhaSplit[3], new String[]{"va_para", "vá_para"}, x + 1);
            } else {
                validarPalavra(linhaSplit[1], new String[]{"se"}, x + 1);
                validarPalavra(linhaSplit[3], new String[]{"entao", "então"}, x + 1);
                validarPalavra(linhaSplit[4], new String[]{"va_para", "vá_para"}, x + 1);
                validarPalavra(linhaSplit[6], new String[]{"senao", "senão"}, x + 1);
                validarPalavra(linhaSplit[7], new String[]{"va_para", "vá_para"}, x + 1);
            }
        }
    }
    
    private void validarPalavra(String valor, String[] palavras, int linha) throws Exception {
        if (!Arrays.asList(palavras).contains(valor.toLowerCase())) {
            throw new Exception("Linha " + linha + " inválida: Palavra '"+valor+"' inválida");
        }
    }

    /**
     * Define os resultados verdadeiros e falsos, se for do tipo Teste e o teste
     * preenche somente o rótulo da linha destino
     *
     * @param i
     * @return
     */
    private Instrucao definirResultadosEtapa1(Instrucao i) {
        String[] palavras = removeRotuloInstrucao(i.getStringLinha());

        if (i.getTipo().equals(InstrucaoTipoEnum.OPERACAO.getTipo())) {
            i = definirResultadosIstrucaoOperacao(i, palavras);
        } else {
            i = definirRotuloEEncaminhamentosTeste(i, palavras);
        }

        return i;
    }
    
    /**
     * Define os as operacoes nos resultados das instrucoes do tipo TESTE e
     * detecta ciclo e parada
     *
     * @param i
     * @return
     */
    private void definirResultadosInstrucaoTeste() throws Exception {
        for (Instrucao i : instrucoesLista) {
            if (i.getTipo().equals(InstrucaoTipoEnum.TESTE.getTipo())) {
                for (boolean resultado : new boolean[]{true, false}) {

                    if (i.getResultado(resultado)[0].equals("")) {
                        deteccaoCicloInfinitoLista = new ArrayList<>();

                        String res = getOperacaoInstrucaoTeste(resultado, i.getRotuloLinha());

                        switch (res) {
                            case "e":
                            case "parada":
                                i.setResultado(resultado, "parada", "e");
                                break;
                            case "e*":
                            case "ciclo":
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

    /**
     * Procura as operações para as instruções que são TESTE
     * 
     * @param resultado
     * @param rotuloLinha
     * @return
     * @throws Exception 
     */
    private String getOperacaoInstrucaoTeste(boolean resultado, String rotuloLinha) throws Exception {
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
            return getOperacaoInstrucaoTeste(resultado, i.getResultado(resultado)[1]);
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

    private Instrucao definirRotuloEEncaminhamentosTeste(Instrucao i, String[] palavras) {
        i.setRotuloTeste(palavras[1]);
        i.setResultado(true, "", palavras[4]);
        i.setResultado(false, "", palavras[7]);

        return i;
    }

    private List<Instrucao> converterLinhasParaInstrucoes(List<String> linhas) {
        List<Instrucao> lista = new ArrayList<>();
        for (int indice = 0; indice<linhas.size();indice++) {
            lista.add(converterLinhaParaInstrucao1(linhas.get(indice), indice));
        }
        return lista;
    }

    private Instrucao converterLinhaParaInstrucao1(String linha, Integer indice) {
        linha = linha.replaceAll(";", "");

        Instrucao i = new Instrucao();
        i.setIndice(indice);
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
