/*
 * The MIT License
 *
 * Copyright 2022 ph757.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.prjprimeiro.telas;

import java.sql.*;
import com.prjprimeiro.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Classe Responsavel Por Gerenciamento de Ordens De Serviços.
 * @author Alexph7
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    //a variavel abaixo armazena um texto de acordo com a seleção do radioButton
    private String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conector();
        rbtOsOrcamento.setSelected(true);
        tipo = "orçamento";
    }

    /**
     * Metodo Para Pequisar Cliente No Banco de Dados e Exibir Na Tabela.
     */
    private void pesquisar_cliente() {
        try {
            pst = conexao.prepareStatement("select idcli as Id, nomecli as Nome, fonecli as Fone from tbclientes where nomecli like ?");
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblOs.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Método Para Setar Id de Cliente No Campo Id
     */
    private void setar_Id() {
        try {
            int setar = tblOs.getSelectedRow();
            txtCliId.setText(tblOs.getModel().getValueAt(setar, 0).toString());
            //lblNome.setText("".concat("Nome ")+tblOs.getModel().getValueAt(setar, 1).toString()); setar nome em lbl concatenado

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Campo Vazio");
        }
    }

    /**
     * método Para Limpar Campos
     */
    private void limpar_campos() {
        txtOsData.setText(null);
        txtOsNum.setText(null);
        txtCliPesquisar.setText(null);
        txtCliId.setText(null);
        cbOsSituacao.setSelectedIndex(0);
        txtOsEquipamento.setText(null);
        txtOsDefeito.setText(null);
        txtOsServico.setText(null);
        txtOsTecnico.setText(null);
        txtOsValor.setText(null);
        txtCliPesquisar.requestFocus();
        ((DefaultTableModel) tblOs.getModel()).setRowCount(0);
        btnOsImprimir.setEnabled(false);
        btnOsUpdate.setEnabled(false);
        btnOsDelete.setEnabled(false);
    }

    /**
     * Método para cadastrar uma Os no banco de dados deixando preparado para impressão.
     */
    private void emitir_os() {
        try {
            pst = conexao.prepareStatement("insert into tbos (tipo, situacao, equipamento, defeito, serviço, tecnico, valor, idcli) values (?,?,?,?,?,?,?,?)");
            pst.setString(1, tipo);
            pst.setString(2, cbOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsEquipamento.getText());
            pst.setString(4, txtOsDefeito.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTecnico.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            //validação campos obrigatórios
            if (txtCliId.getText().isEmpty() || txtOsEquipamento.getText().isEmpty() || txtOsDefeito.getText().isEmpty() || txtOsServico.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencher Campos Vazios ou Pesquise Cliente");
            } else if (cbOsSituacao.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(null, "Selecione a Situação do Equipamento");
            } else {
                int status = pst.executeUpdate();
                if (status > 0) {
                    JOptionPane.showMessageDialog(null, "O.S Emitida Com Sucesso");
                    //recuperar numero da os para ter a impressao passando o numero da os
                    recuperarNumOs();
                    btnOsCreate.setEnabled(false);
                    btnOsRead.setEnabled(false);
                    btnOsImprimir.setEnabled(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Método para Consultar Ordem de serviço.
     */
    private void consultar_os() {
        String num_os = JOptionPane.showInputDialog("Digite o Numero da O.s");
        if (num_os.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Campo Vazio, Sem Pesquisa");
        } else {
            try {
                pst = conexao.prepareStatement("select os,date_format(data_os,'%d/%m/%Y - %H:%i'),tipo,situacao,equipamento,defeito,serviço,tecnico,valor,idcli from tbos where os = " + num_os);
                rs = pst.executeQuery();
                if (rs.next()) {
                    txtOsNum.setText(rs.getString(1));
                    txtOsData.setText(rs.getString(2));
                    String rbtTipo = rs.getString(3);
                    if (rbtTipo.equals("os")) {
                        rbtOsOrdemServ.setSelected(true);
                        tipo = "os";
                    } else {
                        rbtOsOrcamento.setSelected(true);
                        tipo = "orçamento";
                    }
                    cbOsSituacao.setSelectedItem(rs.getString(4));
                    txtOsEquipamento.setText(rs.getString(5));
                    txtOsDefeito.setText(rs.getString(6));
                    txtOsServico.setText(rs.getString(7));
                    txtOsTecnico.setText(rs.getString(8));
                    txtOsValor.setText(rs.getString(9));
                    txtCliId.setText(rs.getString(10));
                    //evitando problemas após consulta
                    btnOsCreate.setEnabled(false);
                    txtCliPesquisar.setEnabled(false);
                    tblOs.setVisible(false);
                    //evitar troca de equipamento
                    txtOsEquipamento.setEditable(false);
                    txtOsEquipamento.setToolTipText("Não troca aparelho para modificação");
                    btnOsImprimir.setEnabled(true);
                    btnOsUpdate.setEnabled(true);
                    btnOsDelete.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "OS não encontrada");
                }
            } catch (SQLSyntaxErrorException e) {
                JOptionPane.showMessageDialog(null, "Dados inválidos");
            } catch (SQLException e2) {
                JOptionPane.showMessageDialog(null, e2);
            }
        }
    }

    /**
     * Método para Alterar Dados em uma Ordem de Serviço.
     */
    private void alterar_os() {
        try {
            pst = conexao.prepareStatement("update tbos set tipo=?, situacao=?, defeito=?, serviço=?, tecnico=?, valor=? where os=?");
            pst.setString(1, tipo);
            pst.setString(2, cbOsSituacao.getSelectedItem().toString());
            pst.setString(3, txtOsDefeito.getText());
            pst.setString(4, txtOsServico.getText());
            pst.setString(5, txtOsTecnico.getText());
            pst.setString(6, txtOsValor.getText().replace(",", "."));
            pst.setString(7, txtOsNum.getText());
            //validação campos obrigatórios
            if (txtOsDefeito.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencher Campo Defeito");
                txtOsDefeito.requestFocus();
            } else if (txtOsServico.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencher Campo Serviço");
                txtOsServico.requestFocus();
            } else if (txtOsTecnico.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencher Campo Técnico");
                txtOsTecnico.requestFocus();
            } else if (cbOsSituacao.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(null, "Selecione a Situação do Equipamento");
            } else {
                int status = pst.executeUpdate();
                if (status > 0) {
                    JOptionPane.showMessageDialog(null, "O.S Alterada Com Sucesso");
                    limpar_campos();
                    btnOsCreate.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblOs.setVisible(true);
                    txtOsEquipamento.setEditable(true);
                    txtOsEquipamento.setToolTipText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro Ao Alterar Dados");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * Método para Exluir uma Ordem de Serviço
     */
    private void excluir_os() {
        int confirm = JOptionPane.showConfirmDialog(null, "Tem Certeza que deseja excluir Os?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirm == 0) {
            try {
                pst = conexao.prepareStatement("delete from tbos where os = ?");
                pst.setString(1, txtOsNum.getText());
                int status = pst.executeUpdate();
                if (status > 0) {
                    JOptionPane.showMessageDialog(null, "Os Excluida com sucesso");
                    limpar_campos();
                    btnOsCreate.setEnabled(true);
                    txtCliPesquisar.setEnabled(true);
                    tblOs.setVisible(true);
                    txtOsEquipamento.setEditable(true);
                    txtOsEquipamento.setToolTipText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Erro Ao Exxcluir OS");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * Método para imprimir uma Ordem de Serviço.
     */
    private void imprimir_os() {
        //Gerando um relatório de clientes
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta O.S?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == 0) {
            //imprimindo relatorio com framework jasperReport
            try {
                //Usando HashMap para criar um filtro
                HashMap filtro1 = new HashMap();
                filtro1.put("os", Integer.parseInt(txtOsNum.getText()));//"os" é o nome do parametro utilizado no ireport
                //Usando a classe jasperPrint para preparar a impressão
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/Reports/O.S.jasper"), filtro1, conexao);
                //Linha abaixo exibe o relatorio através do jasperView
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * Método Para Recuperar número de Ultima Ordem de Serviço Cadastrado Para Preencher Parametro necessário Para Imprimir.
     */
    private void recuperarNumOs() {
        try {
            pst = conexao.prepareStatement("select max(os) from tbos");
            rs = pst.executeQuery();
            if (rs.next()) {
                txtOsNum.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOsNum = new javax.swing.JTextField();
        txtOsData = new javax.swing.JTextField();
        rbtOsOrdemServ = new javax.swing.JRadioButton();
        rbtOsOrcamento = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cbOsSituacao = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOs = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOsEquipamento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsDefeito = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOsTecnico = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOsServico = new javax.swing.JTextField();
        txtOsValor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnOsUpdate = new javax.swing.JButton();
        btnOsDelete = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnOsImprimir = new javax.swing.JButton();
        btnOsRead = new javax.swing.JButton();
        btnOsCreate = new javax.swing.JButton();

        setClosable(true);
        setTitle("OS - Ordem de Serviço");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/desconhecidoicon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(639, 505));
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Nº OS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("DATA");

        txtOsNum.setEditable(false);
        txtOsNum.setBackground(new java.awt.Color(225, 223, 223));
        txtOsNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsNum.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOsNum.setAutoscrolls(false);
        txtOsNum.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtOsNum.setFocusable(false);

        txtOsData.setEditable(false);
        txtOsData.setBackground(new java.awt.Color(225, 223, 223));
        txtOsData.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOsData.setAutoscrolls(false);
        txtOsData.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtOsData.setFocusable(false);

        buttonGroup1.add(rbtOsOrdemServ);
        rbtOsOrdemServ.setText("Ordem de Serviço");
        rbtOsOrdemServ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtOsOrdemServ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsOrdemServActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOsOrcamento);
        rbtOsOrcamento.setText("Orçamento");
        rbtOsOrcamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rbtOsOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsOrcamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(101, 101, 101)
                .addComponent(jLabel2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(txtOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(rbtOsOrcamento)
                .addGap(37, 37, 37)
                .addComponent(rbtOsOrdemServ))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOsNum, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOsData, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rbtOsOrcamento)
                    .addComponent(rbtOsOrdemServ)))
        );

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel3.setText("Situação");

        cbOsSituacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbOsSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado Pelo Cliente", "Na Bancada", "Retornou" }));
        cbOsSituacao.setBorder(new javax.swing.border.MatteBorder(null));
        cbOsSituacao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));
        jPanel2.setPreferredSize(new java.awt.Dimension(315, 164));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });
        jPanel2.add(txtCliPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 145, 30));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/iconpesq2.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, 30, 28));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("*Id");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, -1, -1));

        txtCliId.setEditable(false);
        txtCliId.setBackground(new java.awt.Color(226, 226, 226));
        txtCliId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCliId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliId.setAutoscrolls(false);
        txtCliId.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        txtCliId.setFocusable(false);
        jPanel2.add(txtCliId, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 70, 25));

        tblOs = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblOs.setFocusable(false);
        tblOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOs);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 51, 292, 100));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("*Equipameto");

        txtOsEquipamento.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("*Defeito");

        txtOsDefeito.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("*Serviço");

        txtOsTecnico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Técnico");

        txtOsServico.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txtOsValor.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtOsValor.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Valor Total");

        btnOsUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/updateicon.png"))); // NOI18N
        btnOsUpdate.setToolTipText("Modificar");
        btnOsUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsUpdate.setEnabled(false);
        btnOsUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsUpdateActionPerformed(evt);
            }
        });

        btnOsDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/deleteicon.png"))); // NOI18N
        btnOsDelete.setToolTipText("Deletar");
        btnOsDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsDelete.setEnabled(false);
        btnOsDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsDeleteActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setText("Criar O.S");

        jLabel12.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel12.setText("Verificar");

        jLabel13.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel13.setText("Alterar");

        jLabel14.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel14.setText("Deletar");

        btnOsImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnOsImprimir.setText("Imprimir");
        btnOsImprimir.setToolTipText("Imprimir");
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir.setEnabled(false);
        btnOsImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsImprimirActionPerformed(evt);
            }
        });

        btnOsRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/readicon.png"))); // NOI18N
        btnOsRead.setToolTipText("Pesquisar");
        btnOsRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsReadActionPerformed(evt);
            }
        });

        btnOsCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/addicon.png"))); // NOI18N
        btnOsCreate.setToolTipText("Adicionar");
        btnOsCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cbOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(jLabel11)
                                .addGap(107, 107, 107)
                                .addComponent(jLabel12)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel13)
                                .addGap(128, 128, 128)
                                .addComponent(jLabel14)
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnOsCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnOsRead, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnOsUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnOsDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(10, 10, 10)
                                .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel10)
                                .addGap(4, 4, 4)
                                .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbOsSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6))
                    .addComponent(txtOsEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel7))
                    .addComponent(txtOsDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8))
                    .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txtOsTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(btnOsImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnOsCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsRead, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOsDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel11))
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        setBounds(0, 0, 639, 505);
    }// </editor-fold>//GEN-END:initComponents

    private void btnOsUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsUpdateActionPerformed
        alterar_os();
    }//GEN-LAST:event_btnOsUpdateActionPerformed

    private void btnOsDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsDeleteActionPerformed
        excluir_os();
    }//GEN-LAST:event_btnOsDeleteActionPerformed

    private void btnOsImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsImprimirActionPerformed
        imprimir_os();

    }//GEN-LAST:event_btnOsImprimirActionPerformed

    private void btnOsReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsReadActionPerformed
        consultar_os();
    }//GEN-LAST:event_btnOsReadActionPerformed

    private void btnOsCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsCreateActionPerformed
        emitir_os();
    }//GEN-LAST:event_btnOsCreateActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOsMouseClicked
        setar_Id();
    }//GEN-LAST:event_tblOsMouseClicked

    private void rbtOsOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsOrcamentoActionPerformed
        tipo = "orçamento";
    }//GEN-LAST:event_rbtOsOrcamentoActionPerformed

    private void rbtOsOrdemServActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsOrdemServActionPerformed
        tipo = "os";
    }//GEN-LAST:event_rbtOsOrdemServActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened

    }//GEN-LAST:event_formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsCreate;
    private javax.swing.JButton btnOsDelete;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsRead;
    private javax.swing.JButton btnOsUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    public static javax.swing.JComboBox<String> cbOsSituacao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JRadioButton rbtOsOrcamento;
    public static javax.swing.JRadioButton rbtOsOrdemServ;
    private javax.swing.JTable tblOs;
    public static javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    public static javax.swing.JTextField txtOsData;
    public static javax.swing.JTextField txtOsDefeito;
    public static javax.swing.JTextField txtOsEquipamento;
    public static javax.swing.JTextField txtOsNum;
    public static javax.swing.JTextField txtOsServico;
    public static javax.swing.JTextField txtOsTecnico;
    public static javax.swing.JTextField txtOsValor;
    // End of variables declaration//GEN-END:variables
}
