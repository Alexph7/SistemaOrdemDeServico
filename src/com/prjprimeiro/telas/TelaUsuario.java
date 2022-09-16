package com.prjprimeiro.telas;

import java.sql.*;
import com.prjprimeiro.dal.ModuloConexao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    //Usando a variavel de conexão do pacote prjprimeiro.Dal
    Connection conexao = null;
    //Criando variaveis especiais para conexão com o banco de dados.
    //PreparedStatment e ResultSet são frameworks do pacote java.sql
    //e servem para preparar e executar as instruções sql
    PreparedStatement pst = null;
    ResultSet rs = null;

    private void limparCampos() {
        //As Linhas abaixo limpam os campos
        txtUsuId.setText(null);
        txtUsuNome.setText(null);
        txtUsuFone.setText(null);
        txtUsuLogin.setText(null);
        txtUsuSenha.setText(null);
        CboUsuPerfil.setSelectedIndex(0);
    }

    /**
     * Creates new form TelaUsuarios
     */
    public TelaUsuario() {//Construtor
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //Método para Consultar Usuarios
    private void consultar() {
        String sql = "select * from tbusuarios where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtUsuNome.setText(rs.getString(2));
                txtUsuFone.setText(rs.getString(3));
                txtUsuLogin.setText(rs.getString(4));
                txtUsuSenha.setText(rs.getString(5));
                //A Linha abaixo se refere a um combobox.
                CboUsuPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não Cadastrado");
                limparCampos();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //Métodos para adicionar usuários
    private void adicionar() throws SQLException {
        String sql = "Insert into tbusuarios(iduser,usuario,fone,login,senha,perfil) values(?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuId.getText());
            pst.setString(2, txtUsuNome.getText());
            pst.setString(3, txtUsuFone.getText());
            pst.setString(4, txtUsuLogin.getText());
            pst.setString(5, txtUsuSenha.getText());
            pst.setString(6, CboUsuPerfil.getSelectedItem().toString());
            //validação dos campos obrigatorios
            if (txtUsuId.getText().isEmpty() || txtUsuNome.getText().isEmpty() || txtUsuLogin.getText().isEmpty() || txtUsuSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else if (CboUsuPerfil.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(null, "Selecione um Perfil");
            } else {
                //A linha Abaixo atualiza a tabela com os dados do formulario
                int adicionado = pst.executeUpdate(); //este comando é usado tanto para inserir ou alterar dados na tabela
                //A Linha Abaixo confirma a inserção dos dados                
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Adicionado Com Sucesso!");
                    limparCampos();
                }
            }
        } catch (SQLIntegrityConstraintViolationException exception) {
            JOptionPane.showMessageDialog(null, "Usuário já Cadastrado");
            //System.out.println(e);
        }
    }

    //Criando metodo Alterar dados do usuário.
    private void alterar() {
        String sql = "update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=? where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuNome.getText());
            pst.setString(2, txtUsuFone.getText());
            pst.setString(3, txtUsuLogin.getText());
            pst.setString(4, txtUsuSenha.getText());
            pst.setString(5, CboUsuPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuId.getText());

            if (txtUsuId.getText().isEmpty() || txtUsuNome.getText().isEmpty() || txtUsuLogin.getText().isEmpty() || txtUsuSenha.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");
            } else if (CboUsuPerfil.getSelectedItem().equals("Selecione")) {
                JOptionPane.showMessageDialog(null, "Selecione um Perfil");
            } else {
                //A linha Abaixo altera a tabela com os dados do formulario
                int alterado = pst.executeUpdate(); //este comando é usado tanto para inserir ou alterar dados na tabela
                //A estrutura Abaixo confirma a alteração dos dados
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do Usuário Alterados Com Sucesso!");
                    limparCampos();
                }
            }
        } catch (Exception e) {
            //A linha abaixo comentada é usada para ajudar a indentificar o erro           
            //System.out.println(e);
            JOptionPane.showMessageDialog(null, "Erro ao Alterar Usuário!");
        }
    }

    //Metodo Responsavel pela remoção de usuarios
    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Remover Usuário?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbusuarios where iduser = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuId.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário Removido com Sucesso");
                    limparCampos();
                } else if (!txtUsuId.getText().equals(rs.getString(1))) {
                    JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");
                }

            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado.");

            } catch (Exception e) {
                //A linha abaixo comentada é usada para ajudar a indentificar o erro           
                //System.out.println(e);
                JOptionPane.showMessageDialog(null, "Erro ao Alterar Usuário!");
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuNome = new javax.swing.JTextField();
        txtUsuId = new javax.swing.JTextField();
        txtUsuSenha = new javax.swing.JTextField();
        CboUsuPerfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtUsuFone = new javax.swing.JTextField();
        btnUsuCreate = new javax.swing.JButton();
        btnUsuRead = new javax.swing.JButton();
        btnUsuUpdate = new javax.swing.JButton();
        btnUsuDelete = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtUsuLogin = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setClosable(true);
        setTitle("Usuários");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/okicon.png"))); // NOI18N
        setPreferredSize(new java.awt.Dimension(640, 484));
        setVisible(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("*Id");
        jLabel1.setPreferredSize(new java.awt.Dimension(5, 18));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("*Login");
        jLabel2.setPreferredSize(new java.awt.Dimension(5, 18));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("*Nome");
        jLabel3.setPreferredSize(new java.awt.Dimension(5, 18));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("*Senha");
        jLabel4.setPreferredSize(new java.awt.Dimension(5, 18));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("*Perfil");
        jLabel5.setPreferredSize(new java.awt.Dimension(5, 18));

        txtUsuNome.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        txtUsuId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        txtUsuSenha.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        CboUsuPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "admin", "user" }));
        CboUsuPerfil.setToolTipText("Selecione");
        CboUsuPerfil.setName(""); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fone");
        jLabel6.setPreferredSize(new java.awt.Dimension(5, 18));

        txtUsuFone.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        btnUsuCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/addicon.png"))); // NOI18N
        btnUsuCreate.setToolTipText("Adicionar");
        btnUsuCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuCreateActionPerformed(evt);
            }
        });

        btnUsuRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/readicon.png"))); // NOI18N
        btnUsuRead.setToolTipText("Pesquisar");
        btnUsuRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuReadActionPerformed(evt);
            }
        });

        btnUsuUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/updateicon.png"))); // NOI18N
        btnUsuUpdate.setToolTipText("Modificar");
        btnUsuUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuUpdateActionPerformed(evt);
            }
        });

        btnUsuDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/prjprimeiro/icones/deleteicon.png"))); // NOI18N
        btnUsuDelete.setToolTipText("Deletar");
        btnUsuDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuDeleteActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
        jLabel10.setText("*Campos Obrigatorios");

        txtUsuLogin.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setText("Adicionar");

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setText("Verificar");

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setText("Alterar");

        jLabel11.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel11.setText("Deletar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel7)
                .addGap(98, 98, 98)
                .addComponent(jLabel8)
                .addGap(109, 109, 109)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(55, 55, 55))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(22, 22, 22)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(59, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUsuFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsuLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CboUsuPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUsuCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuRead, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addGap(20, 20, 20))
        );

        setBounds(0, 0, 640, 508);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuCreateActionPerformed
        try {
            adicionar();
        } catch (SQLException ex) {
            Logger.getLogger(TelaUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUsuCreateActionPerformed

    private void btnUsuReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuReadActionPerformed
        consultar();
    }//GEN-LAST:event_btnUsuReadActionPerformed

    private void btnUsuUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuUpdateActionPerformed
        alterar();
    }//GEN-LAST:event_btnUsuUpdateActionPerformed

    private void btnUsuDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuDeleteActionPerformed
        remover();
    }//GEN-LAST:event_btnUsuDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CboUsuPerfil;
    private javax.swing.JButton btnUsuCreate;
    private javax.swing.JButton btnUsuDelete;
    private javax.swing.JButton btnUsuRead;
    private javax.swing.JButton btnUsuUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtUsuFone;
    private javax.swing.JTextField txtUsuId;
    private javax.swing.JTextField txtUsuLogin;
    private javax.swing.JTextField txtUsuNome;
    private javax.swing.JTextField txtUsuSenha;
    // End of variables declaration//GEN-END:variables
}
