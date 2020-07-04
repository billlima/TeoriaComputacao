/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import modelos.Instrucao;
import trabalhoteoriacomputacao.Interpretador;
import utils.Utils;

/**
 *
 * @author mlima
 */
public class Principal extends javax.swing.JFrame {

    final JFileChooser fc = new JFileChooser();

    public Principal() {
        initComponents();

        taOrigem.setText("Exemplos: \n"
                + "\n"
                + "1: faça F vá_para 2\n"
                + "2: se T1 então vá_para 1 senão vá_para 3\n"
                + "3: faça G vá_para 4\n"
                + "4: se T2 então vá_para 5 senão vá_para 1\n"
                + "\n"
                + "1: faça G vá_para 2\n"
                + "2: se T2 então vá_para 1 senão vá_para 3\n"
                + "\n"
                + "1: faça G vá_para 2\n"
                + "2: se T2 então vá_para 3 senão vá_para 4\n"
                + "3: se T3 então vá_para 2 senão vá_para 4\n"
                + "\n"
                + "1: faça G vá_para 2\n"
                + "2: se T2 então vá_para 3 senão vá_para 4\n"
                + "3: se T3 então vá_para 2 senão vá_para 4");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taDestino = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        taOrigem = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        taDestino.setEditable(false);
        taDestino.setColumns(20);
        taDestino.setRows(5);
        jScrollPane1.setViewportView(taDestino);

        taOrigem.setColumns(20);
        taOrigem.setRows(5);
        jScrollPane2.setViewportView(taOrigem);

        jLabel1.setText("Programa Monolítico Com Instruções Rotuladas:");

        jButton1.setText("Carregar de um Arquivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Programa Monilítico com Instruções Rotuladas Compostas:");

        jButton2.setText("Converter");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 568, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(124, 124, 124))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        buscarArquivo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        converter();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buscarArquivo() {
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            try {
                taOrigem.setText(String.join("\n", Utils.converterArquivo(file)));
            } catch (FileNotFoundException ex) {
                taDestino.setText("");
            }
        }
    }

    private void converter() {
        if (taOrigem.getText() == null || taOrigem.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(this, "Informe o programa monolítico!", "Erro", 0);
        }

        List<String> linhasPrograma = Arrays.asList(taOrigem.getText().split("\n"));

        Interpretador i = new Interpretador();
        try {
            List<Instrucao> lista = i.interpretarProgramaMonolitico(linhasPrograma);
            imprimirResultado(lista, i.temCiclo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Programa inválido: " + ex.getMessage(), "Erro", 0);
            taDestino.setText("");
        }
    }

    private void imprimirResultado(List<Instrucao> instrucoesLista, boolean temCiclo) {
        String result = "";
        for (Instrucao i : instrucoesLista) {
            result += i.getRotuloLinha() + ": ";
            result += getResultado(true, i) + " " + getResultado(false, i) + "\n";
        }
        if (temCiclo) {
            result += "w: (ciclo, w), (ciclo, w)";
        }
        taDestino.setText(result);
    }

    private String getResultado(boolean resultado, Instrucao i) {
        if (i.isParada(resultado)) {
            return ("(parada, e)");
        }
        return "(" + i.getResultado(resultado)[0] + ", " + i.getResultado(resultado)[1] + ")";

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea taDestino;
    private javax.swing.JTextArea taOrigem;
    // End of variables declaration//GEN-END:variables
}
