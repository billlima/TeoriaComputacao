/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Objects;

/**
 *
 * @author mlima
 */
public enum InstrucaoTipoEnum {
    TESTE("teste"),
    OPERACAO("operacao");
    
    private final String tipo;
    
    private InstrucaoTipoEnum(String tipo) {
        this.tipo = tipo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public static InstrucaoTipoEnum fromString(String tipo) throws Exception {
        for (InstrucaoTipoEnum e : InstrucaoTipoEnum.values()) {
            if (Objects.equals(e.getTipo(), tipo)) {
                return e;
            }
        }
        
        throw new Exception("Tipo não encontrado");
    }
}

