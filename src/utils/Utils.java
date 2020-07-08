/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import trabalhoteoriacomputacao.MermaidCli;

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
    
    public static ImageIcon getScaledImageIcon(String path, int maxHeigth) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));

        int perc = getPercentual(maxHeigth, image.getHeight());
        int newWidth = scale(image.getWidth(), perc);
        int newHeigth = scale(image.getHeight(), perc);
        
        return new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(newWidth, newHeigth, Image.SCALE_SMOOTH));
    }
    
    private static int getPercentual(int max, int atual) {
        return (atual*100) / max;
    }
    
    private static int scale(int scale, int currentPerc) {
        return (scale*100) / currentPerc;
    }

}
