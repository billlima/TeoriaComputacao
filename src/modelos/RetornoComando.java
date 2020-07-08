/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author kelvin
 */
public class RetornoComando {
    
    private final boolean sucesso;
    private final int codigoSaida;
    private final String saida;
    
    public RetornoComando(boolean sucesso, int codigoSaida, String saida) {
        this.sucesso = sucesso;
        this.codigoSaida = codigoSaida;
        this.saida = saida;
    }
    
    public boolean sucesso() {
        return this.sucesso;
    }
    
}
