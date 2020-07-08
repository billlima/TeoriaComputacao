/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoteoriacomputacao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import modelos.RetornoComando;

/**
 *
 * @author kelvin
 */
public class MermaidCli {
    
    public final static String SAIDA = "/tmp/saida.png";
    private final String CONFIG = "/tmp/config.json";
    private final String FLAG_INPUT = "-i";
    private final String FLAG_OUTPUT = "-o";
    private final String FLAG_CONFIG = "-p";
    private final String FLAG_BACKGROUND = "-b";
    private final String FLAG_THEME = "-t";
    private final String THEME = "forest";
    private final String COLOR = "transparent";
    private final String COMANDO = "/usr/local/bin/fluxo-teoria-computacao";
    
    public boolean isSucesso(int codigoSaida) {
        return codigoSaida == 0;
    }
    
    public void criarArquivoConfiguracaoLinux() throws FileNotFoundException, UnsupportedEncodingException {
        String texto = "{ \"args\": [\"--no-sandbox\"] }";
        PrintWriter writer = new PrintWriter(CONFIG, "UTF-8");
        writer.print(texto);
        writer.close();
    }
    
    public RetornoComando gerarImagemFluxo(String input) throws Exception {
        List<String> comando = new ArrayList<>();
        
        comando.add(COMANDO);
        comando.add(FLAG_INPUT);
        comando.add(input);
        comando.add(FLAG_OUTPUT);
        comando.add(SAIDA);
        comando.add(FLAG_THEME);
        comando.add(THEME);
        comando.add(FLAG_BACKGROUND);
        comando.add(COLOR);
        comando.add(FLAG_CONFIG);
        comando.add(CONFIG);
        
        return gerarImagemFluxo(comando.toArray(new String[comando.size()]));
    }
    
    public RetornoComando gerarImagemFluxo(String comando []) throws Exception {
        criarArquivoConfiguracaoLinux();
        
        Process p;
        
        String output = "";
        
        try {
            p = Runtime.getRuntime().exec(comando);
            p.waitFor();
                       
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            
            while ((line = reader.readLine()) != null) {
                output += (!output.equals("") ? "\n" : "" ) + line;
            }
            
            return new RetornoComando(isSucesso(p.exitValue()), p.exitValue(), output);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
