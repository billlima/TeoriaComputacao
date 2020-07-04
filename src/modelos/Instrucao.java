/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author mlima
 */
public class Instrucao {
    
    private Integer indice;
    private String tipo;
    private String rotuloLinha;
    private String rotuloTeste;
    private String stringLinha;
    private String[] resultadoVerdadeiro;    
    private String[] resultadoFalso;
    private boolean resultadoVerdadeiroParada;
    private boolean resultadoFalsoParada;

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRotuloLinha() {
        return rotuloLinha;
    }

    public void setRotuloLinha(String rotuloLinha) {
        this.rotuloLinha = rotuloLinha;
    }

    public String getStringLinha() {
        return stringLinha;
    }

    public void setStringLinha(String stringLinha) {
        this.stringLinha = stringLinha;
    }

    public String[] getResultadoVerdadeiro() {
        return resultadoVerdadeiro;
    }

    public void setResultadoVerdadeiro(String[] resultadoVerdadeiro) {
        this.resultadoVerdadeiro = resultadoVerdadeiro;
    }

    public String[] getResultadoFalso() {
        return resultadoFalso;
    }

    public void setResultadoFalso(String[] resultadoFalso) {
        this.resultadoFalso = resultadoFalso;
    }
    
    public void setResultado(boolean verdadeiro, String faca, String vaPara) {
        String[] arr = new String[]{faca, vaPara};
        if (verdadeiro) {
            this.resultadoVerdadeiro = arr;
        } else {
            this.resultadoFalso = arr;
        }
        
    }

    public String getRotuloTeste() {
        return rotuloTeste;
    }

    public void setRotuloTeste(String rotuloTeste) {
        this.rotuloTeste = rotuloTeste;
    }

    public boolean isResultadoVerdadeiroParada() {
        return resultadoVerdadeiroParada;
    }

    public void setResultadoVerdadeiroParada(boolean resultadoVerdadeiroParada) {
        this.resultadoVerdadeiroParada = resultadoVerdadeiroParada;
    }

    public boolean isResultadoFalsoParada() {
        return resultadoFalsoParada;
    }

    public void setResultadoFalsoParada(boolean resultadoFalsoParada) {
        this.resultadoFalsoParada = resultadoFalsoParada;
    }
    
    public boolean isResultadoParada(boolean verdadeiroFalso) {
        return verdadeiroFalso ? isResultadoVerdadeiroParada() : isResultadoFalsoParada();
    }
    
    public String[] getResultado(boolean verdadeiroFalso) {
        return verdadeiroFalso ? resultadoVerdadeiro : resultadoFalso;
    }
    
    public boolean isParada(boolean verdadeiroFalso) {
        return verdadeiroFalso ? isResultadoVerdadeiroParada() : isResultadoFalsoParada();
    }

    
}
