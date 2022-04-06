/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjam;

import dbd_lcd.PopupRunnable;
import dbd_lcd.RequireLoginRunnable;
import dbd_lcd.Util;
import dbd_lcd.gui.form.DialogKonfirmasi;
import dbd_lcd.gui.form.FormLogin;
import dbd_lcd.system.LoginModule;
import dbd_lcd.system.structs.Peminjam;

/**
 *
 * @author USER
 */
public class BlacklistPeminjam extends dbd_lcd.gui.MyFrame {
    /**
     * Creates new form No_HP
     */
    class Mode{
        public static final int TAMBAH = 0;
        public static final int EDIT = 1;
        public static final int LIHAT = 2;
    }
    private int mode = 0;
    Peminjam peminjam;
    
    public void setMode(int mode){
        this.mode = mode;
        switch(mode){
            case Mode.TAMBAH:{
                keteranganTA.setEnabled(true);
                
                closeButton.setVisible(true);
                addButton.setVisible(true);
                
                saveButton.setVisible(false);
                okButton.setVisible(false);
                removeButton.setVisible(false);
                editButton.setVisible(false);
                cancelEditButton.setVisible(false);
                break;
            }
            case Mode.EDIT:{
                keteranganTA.setEnabled(true);
                
                cancelEditButton.setVisible(true);
                saveButton.setVisible(true);
                
                addButton.setVisible(false);
                closeButton.setVisible(false);
                okButton.setVisible(false);
                removeButton.setVisible(false);
                editButton.setVisible(false);
                break;
            }
            case Mode.LIHAT:{
                keteranganTA.setEnabled(false);
                
                removeButton.setVisible(true);
                okButton.setVisible(true);
                editButton.setVisible(true);
                
                addButton.setVisible(false);
                cancelEditButton.setVisible(false);
                saveButton.setVisible(false);
                closeButton.setVisible(false);
                break;
            }
        }
    }
    
    public void load(Peminjam pm){
        this.peminjam = pm;//new Peminjam(pm);
        if(pm.isPeminjamTerblacklist()) setMode(Mode.LIHAT);
        else setMode(Mode.TAMBAH);
        load();
    }
    public void load(){
        idLabel.setText(peminjam.getIdPeminjam());
        nameLabel.setText(peminjam.getNamaPeminjam());
        String ket = peminjam.getKeteranganBlacklist();
        if(!Util.isNullOrEmpty(ket)){
            keteranganTA.setText(ket);
        }
    }
    public Peminjam read(){
        Peminjam pm = new Peminjam(peminjam);
        pm.setKeteranganBlacklist(Util.nullify(keteranganTA.getText()));
        return pm;
    }
    public BlacklistPeminjam() {
        initComponents();
    }
    
    public void removeBlacklist(){
        DialogKonfirmasi dk = new DialogKonfirmasi();
        dk.setQuestion(String.format("Apa anda yakin ingin menghilangkan status blacklist %s?", peminjam.getNamaPeminjam()));
        dk.setOnDispose(new RequireLoginRunnable(){
            @Override
            public void onLogin(){
                Peminjam pm = read();
                pm.setKeteranganBlacklist(null);
                pm.update();
                dk.dispose();
                dispose(true);
            }
        });
        setPopup(dk);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        idLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        keteranganTA = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        removeButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        cancelEditButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 51), 3));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 153, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(20, 375));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Blacklist");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel3.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("No. Identitas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 20, 10, 10);
        jPanel3.add(jLabel4, gridBagConstraints);

        idLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idLabel.setForeground(new java.awt.Color(255, 255, 255));
        idLabel.setText("H00000000");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 10, 10, 10);
        jPanel3.add(idLabel, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nama");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 20, 10, 10);
        jPanel3.add(jLabel6, gridBagConstraints);

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nameLabel.setForeground(new java.awt.Color(255, 255, 255));
        nameLabel.setText("John Doe");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 10, 10, 10);
        jPanel3.add(nameLabel, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Keterangan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 20, 10, 10);
        jPanel3.add(jLabel2, gridBagConstraints);

        keteranganTA.setColumns(20);
        keteranganTA.setRows(5);
        jScrollPane1.setViewportView(keteranganTA);
        new dbd_lcd.gui.LimitDocumentFilter(256).attach(keteranganTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 20);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jPanel4.setOpaque(false);

        removeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/SILANG.png"))); // NOI18N
        removeButton.setContentAreaFilled(false);
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(removeButton);

        closeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/SILANG.png"))); // NOI18N
        closeButton.setContentAreaFilled(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jPanel4.add(closeButton);

        cancelEditButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/SILANG.png"))); // NOI18N
        cancelEditButton.setContentAreaFilled(false);
        cancelEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditButtonActionPerformed(evt);
            }
        });
        jPanel4.add(cancelEditButton);

        editButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/PENGATURAN.png"))); // NOI18N
        editButton.setContentAreaFilled(false);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jPanel4.add(editButton);

        okButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        okButton.setContentAreaFilled(false);
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        jPanel4.add(okButton);

        saveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        saveButton.setContentAreaFilled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel4.add(saveButton);

        addButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/CENTANG.png"))); // NOI18N
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        jPanel4.add(addButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        jPanel3.add(jPanel4, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        // TODO add your handling code here:
        removeBlacklist();
    }//GEN-LAST:event_removeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        final Peminjam pm = read();
        if(Util.isNullOrEmpty(keteranganTA.getText())){
            removeBlacklist();
            return;
        }
        FormLogin.cekDanMintaSesi(this, new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    pm.update();
                    peminjam = pm;
                    changed = true;
                    setMode(Mode.LIHAT);
                }
                return true;
            }
        });
    }//GEN-LAST:event_saveButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        setMode(Mode.EDIT);
    }//GEN-LAST:event_editButtonActionPerformed

    private void cancelEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditButtonActionPerformed
        // TODO add your handling code here:
        load();
        setMode(Mode.LIHAT);
    }//GEN-LAST:event_cancelEditButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
        dispose(changed);
    }//GEN-LAST:event_okButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        // TODO add your handling code here:
        Peminjam pm = read();
        if(!pm.isPeminjamTerblacklist()){
            Util.handleError(this, new RuntimeException("Keterangan blacklist harus diisi"));
            return;
        }
        FormLogin.cekDanMintaSesi(this, new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    pm.update();
                    dispose(true);
                }
                return true;
            }
        });
    }//GEN-LAST:event_addButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BlacklistPeminjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BlacklistPeminjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BlacklistPeminjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BlacklistPeminjam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BlacklistPeminjam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelEditButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel idLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea keteranganTA;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
