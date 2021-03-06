/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form;

import dbd_lcd.gui.form.barang.DaftarBarangFrame;
import dbd_lcd.gui.form.peminjaman.DaftarPeminjamanFrame;
import dbd_lcd.gui.form.peminjam.DaftarPeminjamFrame;

/**
 *
 * @author user
 */
public class TampilanUtama extends dbd_lcd.gui.MyFrame {

    /**
     * Creates new form TampilanUtama
     */
    public TampilanUtama() {
        initComponents();
        this.setLocationRelativeTo(null);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        peminjamanButton = new javax.swing.JButton();
        barangButton = new javax.swing.JButton();
        peminjamButton = new javax.swing.JButton();
        laporanButton = new javax.swing.JButton();
        backgroundLabel = new dbd_lcd.gui.ImageLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        peminjamanButton.setBackground(new java.awt.Color(255, 0, 0));
        peminjamanButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        peminjamanButton.setText("Riwayat Peminjaman");
        peminjamanButton.setSelected(true);
        peminjamanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peminjamanButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(peminjamanButton, gridBagConstraints);

        barangButton.setBackground(new java.awt.Color(255, 0, 0));
        barangButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        barangButton.setText("Barang");
        barangButton.setSelected(true);
        barangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barangButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(barangButton, gridBagConstraints);

        peminjamButton.setBackground(new java.awt.Color(255, 0, 0));
        peminjamButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        peminjamButton.setText("Peminjam");
        peminjamButton.setSelected(true);
        peminjamButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                peminjamButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(peminjamButton, gridBagConstraints);

        laporanButton.setBackground(new java.awt.Color(255, 0, 0));
        laporanButton.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        laporanButton.setText("Laporan");
        laporanButton.setSelected(true);
        laporanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                laporanButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel1.add(laporanButton, gridBagConstraints);

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, 280, 240));

        backgroundLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/730 hu.jpg"))); // NOI18N
        jPanel2.add(backgroundLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 480));

        getContentPane().add(jPanel2, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void peminjamanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peminjamanButtonActionPerformed
        // TODO add your handling code here:
        setEnabled(false);
        DaftarPeminjamanFrame rp = new DaftarPeminjamanFrame();
        rp.refresh();
        setPopup(rp);
    }//GEN-LAST:event_peminjamanButtonActionPerformed

    private void barangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barangButtonActionPerformed
        // TODO add your handling code here:
        setEnabled(false);
        DaftarBarangFrame b = new DaftarBarangFrame();
        b.refresh();
        setPopup(b);
    }//GEN-LAST:event_barangButtonActionPerformed

    private void peminjamButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peminjamButtonActionPerformed
        // TODO add your handling code here:
        setEnabled(false);
        DaftarPeminjamFrame p = new DaftarPeminjamFrame();
        p.refresh();
        setPopup(p);
    }//GEN-LAST:event_peminjamButtonActionPerformed

    private void laporanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_laporanButtonActionPerformed
        // TODO add your handling code here:
        setEnabled(false);
        LaporanFrame p = new LaporanFrame();
        setPopup(p);
    }//GEN-LAST:event_laporanButtonActionPerformed

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
            java.util.logging.Logger.getLogger(TampilanUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TampilanUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TampilanUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TampilanUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TampilanUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private dbd_lcd.gui.ImageLabel backgroundLabel;
    private javax.swing.JButton barangButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton laporanButton;
    private javax.swing.JButton peminjamButton;
    private javax.swing.JButton peminjamanButton;
    // End of variables declaration//GEN-END:variables
}
