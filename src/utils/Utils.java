/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mlima
 */
public class Utils {

    public static List<String> converterArquivo(File myObj) throws FileNotFoundException {
        List<String> linhas = new ArrayList<>();
        
        try {            
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                linhas.add(data);
            }
            myReader.close();
            
            return linhas;
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
            throw e;
        }
    }

}
