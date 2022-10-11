package com.prjprimeiro.telas;

import java.sql.*;
import com.prjprimeiro.dal.ModuloConexao;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void limpar_campos() {
        txtCliId.setText(null);
        txtCliPesquisa.setText(null);
        txtCliNome.setText(null);
        txtCliEnderec.setText(null);
        txtCliNumero.setText(null);
        txtCliFone.setText(null);
        txtCliPesquisa.requestFocus();
        ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);
    }

    private void adicionar() {
        String sql = "insert into tbclientes (nomecli, enderecocli, numerocli, fonecli) values (?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEnderec.getText());
            pst.setString(3, txtCliNumero.getText());
            pst.setString(4, txtCliFone.getText());

            //Validação dos Campos
            if (txtCliNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Nome");
                txtCliNome.requestFocus();
            } else if (txtCliEnderec.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Endereço");
                txtCliEnderec.requestFocus();
            } else if (txtCliNumero.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Numero");
                txtCliNumero.requestFocus();
            } else {
                //A linha Abaixo atualiza a tabela com os dados do formulario
                int adicionado = pst.executeUpdate();
                //A Linha Abaixo confirma a inserção dos dados                
                if (adicionado > 0) {

                    JOptionPane.showMessageDialog(null, "Cliente Adicionado Com Sucesso!");
                    limpar_campos();
                }
            }
        } catch (HeadlessException | SQLException e) {
            //System.out.println(e);
            JOptionPane.showMessageDialog(null, e);//"Erro Ao Adicionar Cliente.");
        }
    }

//Metodo para pesquisar clientes com filtro.
    private void pesquisar_cliente() {
        String sql = "select idcli as id, nomecli as Nome, enderecocli as Endereço, numerocli as Numero, fonecli as Fone, formapag as Pagamento from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //Passando o conteúdo da caixa de pesquisa para o ?
            //Atenção ao simbolo de % que é a continuação do comando sql
            pst.setString(1, txtCliPesquisa.getText() + "%");
            rs = pst.executeQuery();
            //A linha Abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Método para setar conteúdo na tabela
    public void setar_campos() {
        try {
            int setar = tblClientes.getSelectedRow();
            txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
            txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
            txtCliEnderec.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
            txtCliNumero.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
            txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
            btnClieCreate.setEnabled(false);

        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Pesquisa Vazia");
        }
    }

    public void alterar_cliente() {
        String sql = "update tbclientes set nomecli=?, enderecocli=?, numerocli=?, fonecli=?, formapag=? where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEnderec.getText());
            pst.setString(3, txtCliNumero.getText());
            pst.setString(4, txtCliFone.getText());
            pst.setString(6, txtCliId.getText());

            //Validação dos Campos
            if (txtCliNome.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Nome");
                txtCliNome.requestFocus();
            } else if (txtCliEnderec.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Endereço");
                txtCliEnderec.requestFocus();
            } else if (txtCliNumero.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha o Campo Numero");
                txtCliNumero.requestFocus();
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente Alterado com sucesso");
                    limpar_campos();
                    btnClieCreate.setEnabled(true);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void deletar() {
        String sql = "delete from tbclientes where idcli = ?";
        try {
            pst = conexao.prepareStatement(sql);

            pst.setString(1, txtCliId.getText());

            if (txtCliId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Escolha um cliente para exclusão");
            } else {
                int statusDel;
                int excluido = JOptionPane.showConfirmDialog(null, "Deseja Reallmente Excluir Cliente?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
                if (excluido == 0) {
                    statusDel = pst.executeUpdate();
                    if (statusDel == 1) {
                        JOptionPane.showMessageDialog(null, "Cliente Excluído com sucesso");
                        limpar_campos();
                        btnClieCreate.setEnabled(true);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir cliente");
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

        jLabel11 = new javax.swing.JLabel();
        btnClieCreate = new javax.swing.JButton();
        btnClieUpdate = new javax.swing.JButton();
        btnClieDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliEnderec = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCliPesquisa = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCliNumero = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();

        setClosable(true);
        setTitle("Clientes");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/okicon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(640, 484));
        setVisible(true);

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setText("Deletar");

        btnClieCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/addicon.png"))); // NOI18N
        btnClieCreate.setToolTipText("Adicionar");
        btnClieCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClieCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClieCreateActionPerformed(evt);
            }
        });

        btnClieUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/updateicon.png"))); // NOI18N
        btnClieUpdate.setToolTipText("Modificar");
        btnClieUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClieUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClieUpdateActionPerformed(evt);
            }
        });

        btnClieDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/deleteicon.png"))); // NOI18N
        btnClieDelete.setToolTipText("Deletar");
        btnClieDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClieDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClieDeleteActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Adicionar");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setText("Alterar");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("*Nome");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel2.setText("*Endereço");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel3.setText("Telefone");

        txtCliNome.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        txtCliFone.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        txtCliEnderec.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel5.setText("*Campos Obrigatórios");

        txtCliPesquisa.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtCliPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisaKeyReleased(evt);
            }
        });

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/iconpesq2.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setText("*Nº");

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "id", "Nome", "Endereço", "Telefone", "Numero", "Forma De Pagamento"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setText("Id");

        txtCliId.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtCliId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnClieCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(btnClieUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnClieDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel7)
                        .addGap(146, 146, 146)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(96, 96, 96))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jLabel1))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCliEnderec, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txtCliPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel12))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(75, 75, 75)
                                .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCliPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliEnderec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtCliNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClieCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClieUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClieDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel11)))
                .addContainerGap())
        );

        setBounds(0, 0, 640, 506);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClieCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClieCreateActionPerformed
        adicionar();
    }//GEN-LAST:event_btnClieCreateActionPerformed

    private void txtCliPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisaKeyReleased
        //Este Evento é do tipo "Enquanto for digitando".
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisaKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // Este evento seta o conteúdo da tabela "ao dar clique no mouse)
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnClieUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClieUpdateActionPerformed
        alterar_cliente();
    }//GEN-LAST:event_btnClieUpdateActionPerformed

    private void btnClieDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClieDeleteActionPerformed
        deletar();
    }//GEN-LAST:event_btnClieDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClieCreate;
    private javax.swing.JButton btnClieDelete;
    private javax.swing.JButton btnClieUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEnderec;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtCliNumero;
    private javax.swing.JTextField txtCliPesquisa;
    // End of variables declaration//GEN-END:variables
}
