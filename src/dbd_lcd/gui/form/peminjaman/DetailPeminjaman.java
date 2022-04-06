/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd_lcd.gui.form.peminjaman;

import dbd_lcd.PopupRunnable;
import dbd_lcd.RequireLoginRunnable;
import dbd_lcd.Util;
import dbd_lcd.gui.form.DialogKonfirmasi;
import dbd_lcd.gui.form.FormLogin;
import dbd_lcd.gui.form.peminjam.DetailPeminjamFrame;
import dbd_lcd.system.structs.Barang;
import dbd_lcd.system.structs.IdBarang;
import dbd_lcd.system.structs.Peminjam;
import dbd_lcd.system.structs.Peminjaman;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author admin
 */
public class DetailPeminjaman extends dbd_lcd.gui.MyFrame {

    /**
     * Creates new form DetailPeminjaman
     */
    BarangTableModel barangTableModel;
    boolean readOnly = false;
    Peminjaman peminjaman;
    Color fieldBackground;
    public DetailPeminjaman() {
        initComponents();
        fieldBackground = idPeminjamField.getBackground();
    }
    
    public void load(Peminjaman peminjaman){
        idPeminjamField.setText(peminjaman.peminjam.getIdPeminjam());
        idPeminjamField.setBackground(peminjaman.getBlacklistColor().get(false));
        if(peminjaman.peminjam.isPeminjamTerblacklist()){
            idPeminjamField.setToolTipText(peminjaman.peminjam.getKeteranganBlacklist());
        }else{
            idPeminjamField.setToolTipText(null);
        }
        namaPeminjamLabel.setText(peminjaman.peminjam.getNamaPeminjam());
        nomorHpLabel.setText(peminjaman.peminjam.getNomorHpPeminjam());
        statusCB.setSelectedItem(peminjaman.getStatusString());
        statusCB.setBackground(peminjaman.getStatusColor().get(false));
        waktuPinjamField.setText(Util.formatDateTime(peminjaman.getWaktuPinjam()));
        waktuKembaliField.setText(Util.formatDateTime(peminjaman.getShownWaktuKembali()));
        waktuKembaliField.setBackground(peminjaman.getShownWaktuKembaliColor().get(false));
        keteranganTA.setText(Util.stringify(peminjaman.keteranganPeminjaman));
        
        loadBarang(peminjaman);
        
        daftarKeperluanPanel.load(peminjaman);
        
        this.peminjaman = peminjaman;
        
        setReadOnly(true);
        
        repaint();
    }
    
    public static JTable createBarangTable(DetailPeminjaman dp){
        JTable ret = new JTable(){
            //https://stackoverflow.com/questions/16970824/jtable-with-different-types-of-cells-depending-on-data-type
            private Class editingClass;
            
            @Override
            
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                
                int modelColumn = convertColumnIndexToModel(column);
                if(modelColumn != 2) return super.prepareRenderer(renderer, row, column);
                
                Object value = getValueAt(row, column);

                boolean isSelected = false;
                boolean hasFocus = false;

                // Only indicate the selection and focused cell if not printing
                if (!isPaintingForPrint()) {
                    isSelected = isCellSelected(row, column);

                    boolean rowIsLead =
                        (selectionModel.getLeadSelectionIndex() == row);
                    boolean colIsLead =
                        (columnModel.getSelectionModel().getLeadSelectionIndex() == column);

                    hasFocus = (rowIsLead && colIsLead) && isFocusOwner();
                }
                
                value = Util.determineReturnDateClass(value, dp.getBarangTableModel().isCellEditable());
                return renderer.getTableCellRendererComponent(this, value,
                                                              isSelected, hasFocus,
                                                              row, column);
            }
            
            @Override
            public Component prepareEditor(TableCellEditor editor, int row, int column){
                int modelColumn = convertColumnIndexToModel(column);
                if(modelColumn != 2) return super.prepareEditor(editor, row, column);
                
                Object value = getValueAt(row, column);
                
                value = Util.determineReturnDateClass(value, dp.getBarangTableModel().isCellEditable());
                
                boolean isSelected = isCellSelected(row, column);
                Component comp = editor.getTableCellEditorComponent(this, value, isSelected,
                                                          row, column);
                if (comp instanceof JComponent) {
                    JComponent jComp = (JComponent)comp;
                    if (jComp.getNextFocusableComponent() == null) {
                        jComp.setNextFocusableComponent(this);
                    }
                }
                return comp;
            }

            
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                editingClass = null;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 2) {
                    Object value = getModel().getValueAt(row, modelColumn);
                    value =  Util.determineReturnDateClass(value, dp.getBarangTableModel().isCellEditable());
                    if(value == null) return super.getCellRenderer(row, column);
                    Class rowClass =value.getClass();
                    
                    return getDefaultRenderer(rowClass);
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                editingClass = null;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 2) {
                    Object value = getModel().getValueAt(row, modelColumn);
                    value =  Util.determineReturnDateClass(value, dp.getBarangTableModel().isCellEditable());
                    if(value == null) return super.getCellEditor(row, column);
                    editingClass = value.getClass();
                    return getDefaultEditor(editingClass);
                } else {
                    return super.getCellEditor(row, column);
                }
            }
            //  This method is also invoked by the editor when the value in the editor
            //  component is saved in the TableModel. The class was saved when the
            //  editor was invoked so the proper class can be created.

            @Override
            public Class getColumnClass(int column) {
                if(convertColumnIndexToModel(column)==2){
                    return editingClass != null ? editingClass : super.getColumnClass(column);
                }
                return super.getColumnClass(column);
            }
        };
        return ret;
    }
    public void loadBarang(Peminjaman peminjaman){
        BarangTableModel dtm = new BarangTableModel();
        dtm.addColumn("Barang");
        dtm.addColumn("Nomor");
        dtm.addColumn("Dikembalikan");
        int bl = peminjaman.getBarangCount();
        boolean[] borrowables = new boolean[bl];
        for(int i = 0; i < bl; ++i){
            Barang b = peminjaman.getBarang(i);
            dtm.addRow(new Object[]{
                b.getTipeString(),
                b.getNomor(),
                b.getEndTime()
            });
            borrowables[i] = b.getEndTime() == null || Barang.Status.ADA.equals(b.getCurrent().getStatus());
        }
        dtm.setBorrowables(borrowables);
        dtm.setSource(peminjaman.getBarang());
        this.barangTableModel = dtm;
        barangTable.setModel(dtm);
    }
    
    public BarangTableModel getBarangTableModel(){
        return barangTableModel;
    }
    
    public Peminjaman read(){
        Peminjaman pm = new Peminjaman(peminjaman);
        pm.setStatusPeminjaman(Peminjaman.Status.FROM_STRING.get(statusCB.getSelectedItem()));
        pm.keteranganPeminjaman = Util.nullify(keteranganTA.getText());
        pm.setBarang(readBarang());
        pm.setKeperluanPinjam(daftarKeperluanPanel.read());
        return pm;
    }
    
    public Barang[] readBarang(){
        int rc = barangTable.getRowCount();
        int bl = peminjaman.getBarangCount();
        Barang[] ret = new Barang[rc];
        for(int i = 0; i < bl; ++i){
            Barang b = new Barang(peminjaman.getBarang(i));
            Object value = barangTable.getValueAt(i, 2);
            if(value != null && value instanceof Boolean){
                if(Boolean.TRUE.equals(value)) b.kembalikan();
                if(Boolean.FALSE.equals(value)) b.pinjam(peminjaman.getNomorPeminjaman());
            }
            ret[i] = b;
        }
        for(int i = bl; i < rc; ++i){
            ret[i] = Util.readBarang(barangTable, i);
        }
        for(int i = 0; i < rc; ++i){
            if(ret[i] == null) throw new RuntimeException("NULL AT " + i + " BL " + bl);
        }
        return ret;
    }

    public void setReadOnly(boolean readOnly){
        this.readOnly = readOnly;
        daftarKeperluanPanel.setReadOnly(readOnly);
        
        barangButtonPanel.setVisible(!readOnly);
        barangTableModel.setCellEditable(!readOnly);
        barangTable.repaint();
        
        keteranganTA.setEnabled(!readOnly);
        
        closeButton.setVisible(readOnly);
        cancelBorrowButton.setVisible(readOnly && !Peminjaman.Status.BATAL.equals(peminjaman.getStatus()));
        completeButton.setVisible(readOnly && !Peminjaman.Status.SELESAI.equals(peminjaman.getStatus()));
        editButton.setVisible(readOnly);
        
        cancelEditButton.setVisible(!readOnly);
        saveButton.setVisible(!readOnly);
    }
    public void ubahStatus(String status, String konfirmasi){
        int cek = peminjaman.cekUbahStatus(status);
        switch(cek){
            case Peminjaman.Error.OK:{
                break;
            }
            case Peminjaman.Error.BELUM_DIKEMBALIKAN:{
                DialogKonfirmasi dk = new DialogKonfirmasi();
                dk.setQuestion(
                        Peminjaman.Error.TO_STRING.get(cek)
                        + " Apa anda ingin menandai semua barang untuk peminjaman ini sebagai sudah dikembalikan?"
                );
                dk.setOnDispose(new RequireLoginRunnable(){
                    @Override
                    public void onLogin(){
                        peminjaman.kembalikan();
                        load(peminjaman);
                        ubahStatus(status, konfirmasi);
                        dk.dispose();
                    }
                });
                setPopup(dk);
                return;
            }
            default:{
                throw new RuntimeException(Peminjaman.Error.TO_STRING.get(cek));
            }
        }
        DialogStatusPeminjaman dk = new DialogStatusPeminjaman();
        dk.setQuestion(konfirmasi);
        dk.setOnDispose(new RequireLoginRunnable(){
            @Override
            public void onLogin(){
                peminjaman.ubahStatus(status, dk.getKeterangan());
                changed = true;
                load(peminjaman);
                dk.dispose();
            }
        });
        setPopup(dk);
    }
    
    public void lihatPeminjam(){
        DetailPeminjamFrame tup = new DetailPeminjamFrame();
        tup.load(peminjaman.peminjam);
        setPopup(tup);
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

        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        idPeminjamField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        namaPeminjamLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nomorHpLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        statusCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        waktuPinjamField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        waktuKembaliField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        keteranganTA = new javax.swing.JTextArea();
        ButtonPlusPeminjam = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        barangTable = createBarangTable(this);
        barangButtonPanel = new javax.swing.JPanel();
        removeBarangButton = new javax.swing.JButton();
        addBarangButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        daftarKeperluanPanel = new dbd_lcd.gui.form.peminjaman.DaftarKeperluanPanel();
        jPanel3 = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();
        cancelBorrowButton = new javax.swing.JButton();
        cancelEditButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        completeButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 102));
        getContentPane().setLayout(new java.awt.CardLayout());

        jPanel5.setBackground(new java.awt.Color(0, 102, 102));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 1, 21)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Detail Peminjaman");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        jPanel5.add(jLabel1, gridBagConstraints);

        jTabbedPane1.setBackground(new java.awt.Color(0, 102, 102));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("ID Peminjam     ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel4, gridBagConstraints);

        idPeminjamField.setEditable(false);
        idPeminjamField.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        idPeminjamField.setOpaque(false);
        idPeminjamField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                idPeminjamFieldMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(idPeminjamField, gridBagConstraints);
        new dbd_lcd.gui.LimitDocumentFilter(18).attach(idPeminjamField);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel5.setText("Nama               ");
        jLabel5.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel5, gridBagConstraints);

        namaPeminjamLabel.setText("John Doe");
        namaPeminjamLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(namaPeminjamLabel, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("No HP              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel7, gridBagConstraints);

        nomorHpLabel.setText("0808080808");
        nomorHpLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(nomorHpLabel, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("Status              ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel6, gridBagConstraints);

        statusCB.setModel(new javax.swing.DefaultComboBoxModel<String>(Peminjaman.Status.STRING));
        statusCB.setEnabled(false);
        statusCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(statusCB, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Waktu Pinjam   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        waktuPinjamField.setEditable(false);
        waktuPinjamField.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        waktuPinjamField.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(waktuPinjamField, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("Waktu Kembali ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel3, gridBagConstraints);

        waktuKembaliField.setEditable(false);
        waktuKembaliField.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        waktuKembaliField.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(waktuKembaliField, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("Keterangan");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel8, gridBagConstraints);

        keteranganTA.setColumns(20);
        keteranganTA.setRows(5);
        jScrollPane2.setViewportView(keteranganTA);
        new dbd_lcd.gui.LimitDocumentFilter(256).attach(keteranganTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        ButtonPlusPeminjam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/KC PMBESAR.png"))); // NOI18N
        ButtonPlusPeminjam.setContentAreaFilled(false);
        ButtonPlusPeminjam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonPlusPeminjamActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = -24;
        gridBagConstraints.ipady = -6;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(ButtonPlusPeminjam, gridBagConstraints);

        jTabbedPane1.addTab("Peminjaman", jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 153, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        barangTable.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        barangTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barang", "Nomor", "Dikembalikan"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(barangTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        barangButtonPanel.setOpaque(false);
        barangButtonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        removeBarangButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/MIN.png"))); // NOI18N
        removeBarangButton.setContentAreaFilled(false);
        removeBarangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBarangButtonActionPerformed(evt);
            }
        });
        barangButtonPanel.add(removeBarangButton);

        addBarangButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dbd_lcd/gui/image/TAMBAH.png"))); // NOI18N
        addBarangButton.setContentAreaFilled(false);
        addBarangButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBarangButtonActionPerformed(evt);
            }
        });
        barangButtonPanel.add(addBarangButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel2.add(barangButtonPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jPanel2, gridBagConstraints);

        jTabbedPane1.addTab("Barang", jPanel6);

        jPanel1.setBackground(new java.awt.Color(255, 153, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(daftarKeperluanPanel, gridBagConstraints);

        jTabbedPane1.addTab("Keperluan", jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel5.add(jTabbedPane1, gridBagConstraints);

        jPanel3.setOpaque(false);
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        closeButton.setBackground(new java.awt.Color(204, 255, 255));
        closeButton.setText("CLOSE");
        closeButton.setSelected(true);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        jPanel3.add(closeButton);

        cancelBorrowButton.setBackground(new java.awt.Color(204, 255, 255));
        cancelBorrowButton.setText("BATAL");
        cancelBorrowButton.setSelected(true);
        cancelBorrowButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBorrowButtonActionPerformed(evt);
            }
        });
        jPanel3.add(cancelBorrowButton);

        cancelEditButton.setBackground(new java.awt.Color(204, 255, 255));
        cancelEditButton.setText("BATAL");
        cancelEditButton.setSelected(true);
        cancelEditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelEditButtonActionPerformed(evt);
            }
        });
        jPanel3.add(cancelEditButton);

        editButton.setBackground(new java.awt.Color(204, 255, 255));
        editButton.setText("UBAH");
        editButton.setSelected(true);
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        jPanel3.add(editButton);

        completeButton.setBackground(new java.awt.Color(204, 255, 255));
        completeButton.setText("SELESAI");
        completeButton.setSelected(true);
        completeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeButtonActionPerformed(evt);
            }
        });
        jPanel3.add(completeButton);

        saveButton.setBackground(new java.awt.Color(204, 255, 255));
        saveButton.setText("SIMPAN");
        saveButton.setSelected(true);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel3.add(saveButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jPanel3, gridBagConstraints);

        getContentPane().add(jPanel5, "card3");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void removeBarangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBarangButtonActionPerformed
        // TODO add your handling code here:
        
        int[] rows = barangTable.getSelectedRows();
        int bl = peminjaman.getBarangCount();
        for(int i = rows.length-1; i >= 0; --i){
            if(rows[i] < bl) continue;
            barangTableModel.removeRow(rows[i]);
        }
    }//GEN-LAST:event_removeBarangButtonActionPerformed

    private void addBarangButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBarangButtonActionPerformed
        // TODO add your handling code here:
        barangTableModel.addRow(new Object[]{null, null, false});
    }//GEN-LAST:event_addBarangButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // TODO add your handling code here:
        setReadOnly(false);
    }//GEN-LAST:event_editButtonActionPerformed

    private void cancelBorrowButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBorrowButtonActionPerformed
        // TODO add your handling code here:
        
        this.ubahStatus(Peminjaman.Status.BATAL, "Apa anda yakin ingin membatalkan peminjaman ini?");
    }//GEN-LAST:event_cancelBorrowButtonActionPerformed

    private void cancelEditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelEditButtonActionPerformed
        // TODO add your handling code here:
        load(peminjaman);
    }//GEN-LAST:event_cancelEditButtonActionPerformed

    private void completeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completeButtonActionPerformed
        // TODO add your handling code here:
        this.ubahStatus(Peminjaman.Status.SELESAI, "Apa anda yakin peminjaman ini sudah selesai?");
    }//GEN-LAST:event_completeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        final Peminjaman pm;
        try{
            pm = read();
        }catch(Exception ex){
            Util.handleError(this, ex);
            return;
        }
        if(pm.equals(peminjaman)){
            setReadOnly(true);
            return;
        }
        FormLogin.cekDanMintaSesi(this, new PopupRunnable(){
            @Override
            public boolean run(boolean success){
                if(success){
                    pm.update();
                    load(pm);
                    changed = true;
                    setReadOnly(true);
                }
                return true;
            }
        });
    }//GEN-LAST:event_saveButtonActionPerformed

    private void statusCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusCBActionPerformed
        // TODO add your handling code here:
        
        statusCB.setBackground(Peminjaman.Status.getColor((String)statusCB.getSelectedItem()).get(false));
    }//GEN-LAST:event_statusCBActionPerformed

    private void ButtonPlusPeminjamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonPlusPeminjamActionPerformed
        // TODO add your handling code here:
        lihatPeminjam();
    }//GEN-LAST:event_ButtonPlusPeminjamActionPerformed

    private void idPeminjamFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_idPeminjamFieldMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() != 2) return;
        
        lihatPeminjam();
    }//GEN-LAST:event_idPeminjamFieldMouseClicked

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
            java.util.logging.Logger.getLogger(DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetailPeminjaman.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetailPeminjaman().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonPlusPeminjam;
    private javax.swing.JButton addBarangButton;
    private javax.swing.JPanel barangButtonPanel;
    private javax.swing.JTable barangTable;
    private javax.swing.JButton cancelBorrowButton;
    private javax.swing.JButton cancelEditButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton completeButton;
    private dbd_lcd.gui.form.peminjaman.DaftarKeperluanPanel daftarKeperluanPanel;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField idPeminjamField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea keteranganTA;
    private javax.swing.JLabel namaPeminjamLabel;
    private javax.swing.JLabel nomorHpLabel;
    private javax.swing.JButton removeBarangButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox<String> statusCB;
    private javax.swing.JTextField waktuKembaliField;
    private javax.swing.JTextField waktuPinjamField;
    // End of variables declaration//GEN-END:variables
}
