/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prjprimeiro.telas;

import java.text.DateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author ph757
 */
public class TelaPrincipal extends javax.swing.JFrame {
   
    
    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop = new javax.swing.JDesktopPane();
        lblUsuario = new javax.swing.JLabel();
        lblData = new javax.swing.JLabel();
        lblLogoAgua = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        menCacl = new javax.swing.JMenu();
        menCadClien = new javax.swing.JMenuItem();
        menCadOs = new javax.swing.JMenuItem();
        menCadUsuario = new javax.swing.JMenuItem();
        menRelat = new javax.swing.JMenu();
        menRelServ = new javax.swing.JMenuItem();
        menAjud = new javax.swing.JMenu();
        menAjudSobre = new javax.swing.JMenuItem();
        menOpç = new javax.swing.JMenu();
        menOpçSair = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela Principal");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        desktop.setPreferredSize(new java.awt.Dimension(680, 484));

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        lblUsuario.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblUsuario.setText("USUÁRIO");

        lblData.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblData.setText("Data");

        lblLogoAgua.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        menCacl.setText("Cadastro");

        menCadClien.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        menCadClien.setText("Cliente");
        menCadClien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadClienActionPerformed(evt);
            }
        });
        menCacl.add(menCadClien);

        menCadOs.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        menCadOs.setText("Os");
        menCadOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadOsActionPerformed(evt);
            }
        });
        menCacl.add(menCadOs);

        menCadUsuario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.ALT_MASK));
        menCadUsuario.setText("Usuários");
        menCadUsuario.setEnabled(false);
        menCadUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menCadUsuarioActionPerformed(evt);
            }
        });
        menCacl.add(menCadUsuario);

        Menu.add(menCacl);

        menRelat.setText("Relatório");
        menRelat.setEnabled(false);

        menRelServ.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        menRelServ.setText("Serviços");
        menRelat.add(menRelServ);

        Menu.add(menRelat);

        menAjud.setText("Ajuda");

        menAjudSobre.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        menAjudSobre.setText("Sobre");
        menAjudSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAjudSobreActionPerformed(evt);
            }
        });
        menAjud.add(menAjudSobre);

        Menu.add(menAjud);

        menOpç.setText("Opções");

        menOpçSair.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menOpçSair.setText("Sair");
        menOpçSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpçSairActionPerformed(evt);
            }
        });
        menOpç.add(menOpçSair);

        Menu.add(menOpç);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(desktop, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblData))
                        .addContainerGap(38, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblLogoAgua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(lblData)
                .addGap(43, 43, 43)
                .addComponent(lblLogoAgua, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(963, 566));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        //A Linha Abaixo substituem a label lblData pela data atual do sistema
        //ao iniciar o form
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.SHORT);
        lblData.setText(formatador.format(data));
        
    }//GEN-LAST:event_formWindowActivated

    private void menOpçSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpçSairActionPerformed
        // Exibe uma caixa de dialogo ao sair
        int sair = JOptionPane.showConfirmDialog(null, "Deseja Realmente Sair?","Atenção",JOptionPane.YES_NO_OPTION);
        
        if(sair == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_menOpçSairActionPerformed

    private void menAjudSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAjudSobreActionPerformed
        //chamando a tela "sobre"
        TelaSobre sobre = new TelaSobre();
        sobre.setVisible(true);
        
    }//GEN-LAST:event_menAjudSobreActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // Tratamento de imagem
        ImageIcon icon = new ImageIcon("src/com/prjprimeiro/icones/logoAgua.jpg");
        icon.setImage(icon.getImage().getScaledInstance(lblLogoAgua.getWidth(), lblLogoAgua.getHeight(), 1));
        lblLogoAgua.setIcon(icon);
    }//GEN-LAST:event_formWindowOpened

    private void menCadUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadUsuarioActionPerformed
        // As Linhas abaixo chamam a tela usuarios dentro do desktopPane
        TelaUsuario usuario = new TelaUsuario();
        usuario.setVisible(true);
        desktop.add(usuario);
    }//GEN-LAST:event_menCadUsuarioActionPerformed

    private void menCadClienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadClienActionPerformed
        TelaCliente cliente = new TelaCliente();
        cliente.setVisible(true);
        desktop.add(cliente);
    }//GEN-LAST:event_menCadClienActionPerformed

    private void menCadOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menCadOsActionPerformed
        //chamando tela de ordem de serviço
        TelaOs os = new TelaOs();
        os.setVisible(true);
        desktop.add(os);
    }//GEN-LAST:event_menCadOsActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel lblData;
    private javax.swing.JLabel lblLogoAgua;
    public static javax.swing.JLabel lblUsuario;
    private javax.swing.JMenu menAjud;
    private javax.swing.JMenuItem menAjudSobre;
    private javax.swing.JMenu menCacl;
    private javax.swing.JMenuItem menCadClien;
    private javax.swing.JMenuItem menCadOs;
    public static javax.swing.JMenuItem menCadUsuario;
    private javax.swing.JMenu menOpç;
    private javax.swing.JMenuItem menOpçSair;
    public static javax.swing.JMenuItem menRelServ;
    public static javax.swing.JMenu menRelat;
    // End of variables declaration//GEN-END:variables
}
