package poly.net.ui;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import poly.net.dao.TaiKhoanDAO;
import poly.net.entity.TaiKhoan;
// Import JDBCHelper nếu chưa có (cần để thao tác với CSDL)
// import poly.net.utils.JdbcHelper; 

/**
 *
 * @author pc
 */
public class QuanLyTaiKhoanJDialog extends javax.swing.JDialog {

    TaiKhoanDAO dao = new TaiKhoanDAO(); // Đối tượng DAO để thao tác với bảng TaiKhoan
    int row = -1; // Biến lưu chỉ số dòng hiện tại được chọn trên bảng

    /**
     * Creates new form QuanLyTaiKhoanJDialog
     */
    public QuanLyTaiKhoanJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init(); // Gọi hàm khởi tạo ban đầu
    }

    private void init() {
        this.setLocationRelativeTo(null); // Đặt cửa sổ ra giữa màn hình
        this.fillTable(); // Đổ dữ liệu vào bảng khi khởi tạo
        this.updateStatus(); // Cập nhật trạng thái các nút
        setTitle("QUẢN LÝ TÀI KHOẢN");
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblTaiKhoan.getModel();
        model.setRowCount(0); // Xóa tất cả các hàng hiện có trên bảng
        try {
            List<TaiKhoan> list = dao.selectAll(); // Lấy tất cả tài khoản từ CSDL
            for (TaiKhoan tk : list) {
                Object[] rowData = {
                    tk.getTenDangNhap(),
                    tk.getMatKhau(),
                    tk.getQuyen(),
                    tk.isTrangThai() ? "Hoạt động" : "Khóa" // Hiển thị trạng thái dưới dạng chữ
                };
                model.addRow(rowData); // Thêm hàng vào bảng
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void setForm(TaiKhoan tk) {
        jTextField1.setText(tk.getTenDangNhap());
        jTextField3.setText(tk.getMatKhau());
        if (tk.getQuyen().equals("Admin")) {
            jRadioButton1.setSelected(true);
        } else if (tk.getQuyen().equals("NhanVien")) {
            jRadioButton2.setSelected(true);
        } else {
            jRadioButton3.setSelected(true); // Khách hàng
        }
        jTextField4.setText(tk.isTrangThai() ? "Hoạt động" : "Khóa"); // Hiển thị trạng thái
    }

    TaiKhoan getForm() {
        TaiKhoan tk = new TaiKhoan();
        tk.setTenDangNhap(jTextField1.getText());
        tk.setMatKhau(jTextField3.getText());
        if (jRadioButton1.isSelected()) {
            tk.setQuyen("Admin");
        } else if (jRadioButton2.isSelected()) {
            tk.setQuyen("NhanVien");
        } else {
            tk.setQuyen("Khach");
        }
        tk.setTrangThai(jTextField4.getText().equals("Hoạt động")); // Đặt trạng thái từ text
        return tk;
    }

    void clearForm() {
        TaiKhoan tk = new TaiKhoan();
        this.setForm(tk); // Đặt các trường về rỗng hoặc giá trị mặc định
        buttonGroup1.clearSelection(); // Xóa lựa chọn radio button
        jTextField4.setText(""); // Xóa trạng thái
        this.row = -1; // Reset chỉ số dòng
        this.updateStatus(); // Cập nhật trạng thái nút
    }

    void edit() {
        String tenDangNhap = (String) tblTaiKhoan.getValueAt(this.row, 0); // Lấy tên đăng nhập từ bảng
        TaiKhoan tk = dao.selectById(tenDangNhap); // Tìm tài khoản theo tên đăng nhập
        if (tk != null) {
            this.setForm(tk); // Hiển thị thông tin tài khoản lên form
            tabs.setSelectedIndex(0); // Chuyển sang tab "Quản lý"
            this.updateStatus(); // Cập nhật trạng thái nút
        }
    }

    void insert() {
        TaiKhoan tk = getForm();
        try {
            dao.insert(tk);
            this.fillTable();
            this.clearForm();
            JOptionPane.showMessageDialog(this, "Thêm mới thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thêm mới thất bại! Tên đăng nhập có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void update() {
        TaiKhoan tk = getForm();
        try {
            dao.update(tk);
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    void delete() {
        if (JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa tài khoản này?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            String tenDangNhap = jTextField1.getText();
            try {
                dao.delete(tenDangNhap);
                this.fillTable();
                this.clearForm();
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    void updateStatus() {
        boolean edit = (this.row >= 0); // true nếu có hàng được chọn
        boolean first = (this.row == 0); // true nếu là hàng đầu tiên
        boolean last = (this.row == tblTaiKhoan.getRowCount() - 1); // true nếu là hàng cuối cùng

        jTextField1.setEditable(!edit); // Tên đăng nhập chỉ được chỉnh sửa khi thêm mới
        btnThem.setEnabled(!edit); // Nút Thêm chỉ enabled khi không có hàng nào được chọn
        btnSua.setEnabled(edit); // Nút Sửa chỉ enabled khi có hàng được chọn
        btnXoa.setEnabled(edit); // Nút Xóa chỉ enabled khi có hàng được chọn
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
        jLabel1 = new javax.swing.JLabel();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTaiKhoan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ TÀI KHOẢN");

        jLabel2.setText("Tên đăng nhập");

        jLabel3.setText("Quyền");

        jLabel4.setText("Mật khẩu");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jLabel5.setText("Trạng thái");

        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Quản lý");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Nhân viên");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Khách hàng");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnThem)
                        .addGap(33, 33, 33)
                        .addComponent(btnXoa)
                        .addGap(37, 37, 37)
                        .addComponent(btnSua)
                        .addGap(32, 32, 32)
                        .addComponent(btnMoi))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jRadioButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jRadioButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(202, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3))
                        .addGap(58, 58, 58)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnMoi))
                .addContainerGap(198, Short.MAX_VALUE))
        );

        tabs.addTab("Quản lý", jPanel2);

        tblTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên đăng nhập", "Mật khẩu", "Quyền", "Trạng thái"
            }
        ));
        tblTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblTaiKhoanMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblTaiKhoan);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Danh sách", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblTaiKhoanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTaiKhoanMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblTaiKhoan.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblTaiKhoanMousePressed

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
            java.util.logging.Logger.getLogger(QuanLyTaiKhoanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyTaiKhoanJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyTaiKhoanJDialog dialog = new QuanLyTaiKhoanJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
