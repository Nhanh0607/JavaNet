package poly.net.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import poly.net.dao.*;
import poly.net.entity.*;
import poly.net.utils.MsgBox;
import poly.net.utils.XDate;
import poly.net.utils.XImage;

public class QuanLyHoaDonJDialog extends javax.swing.JDialog {

    //<editor-fold defaultstate="collapsed" desc=" KHAI BÁO BIẾN VÀ DAO ">
    DichVuDAO dvDAO = new DichVuDAO();
    MayTinhDAO mtDAO = new MayTinhDAO();
    KhachHangDAO khDAO = new KhachHangDAO();
    NhanVienDAO nvDAO = new NhanVienDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    ChiTietDichVuDAO ctdvDAO = new ChiTietDichVuDAO();
    ChiTietTienMayDAO cttmDAO = new ChiTietTienMayDAO();

    DefaultTableModel modelTblChon;
    DefaultTableModel modelTblDaChon;
    TableRowSorter<DefaultTableModel> sorter;
    //</editor-fold>

    public QuanLyHoaDonJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //init();
    }
//
//    private void init() {
//        this.setIconImage(XImage.getAppIcon());
//        this.setTitle("QUẢN LÝ HÓA ĐƠN");
//        this.setLocationRelativeTo(null);
//        
//        modelTblChon = (DefaultTableModel) tblChon.getModel();
//        modelTblDaChon = (DefaultTableModel) tblDaChon.getModel();
//        
//        sorter = new TableRowSorter<>(modelTblChon);
//        tblChon.setRowSorter(sorter);
//        
//        loadItemsToSelectionTable();
//        fillComboBoxKhachHang();
//        fillComboBoxNhanVien();
//        
//        jcaNgayLap.setDate(new Date());
//        
//        txtTimKiem.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) { search(); }
//            @Override
//            public void removeUpdate(DocumentEvent e) { search(); }
//            @Override
//            public void changedUpdate(DocumentEvent e) { search(); }
//        });
//        
//        tblChon.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (e.getClickCount() == 2) {
//                    addItemToInvoice();
//                }
//            }
//        });
//        
//        // Thêm listener cho cboKhachHang
//        cboKhachHang.addActionListener(e -> updateKhachHangInfo());
//    }
//    
//    //<editor-fold defaultstate="collapsed" desc=" CÁC PHƯƠNG THỨC TẢI DỮ LIỆU ">
//    private void loadItemsToSelectionTable() {
//        modelTblChon.setRowCount(0);
//        
//        // Thêm cột ẩn để lưu đối tượng gốc
//        if (modelTblChon.getColumnCount() == 3) {
//            modelTblChon.addColumn("DATA_OBJECT");
//            tblChon.getColumnModel().getColumn(3).setMinWidth(0);
//            tblChon.getColumnModel().getColumn(3).setMaxWidth(0);
//        }
//
//        try {
//            // SỬA LỖI HIỆU NĂNG (N+1 QUERY)
//            for (DichVu dv : dvDAO.selectAll()) {
//                double giaDV = dv.getGiaDV(); // Lấy giá trực tiếp từ đối tượng đã có
//                modelTblChon.addRow(new Object[]{dv.getTenDV(), "Dịch vụ", String.format("%,.0f VND", giaDV), dv});
//            }
//            for (MayTinh mt : mtDAO.selectAll()) {
//                double giaThue = mt.getGiaThueTheoGio();
//                modelTblChon.addRow(new Object[]{mt.getTenMay(), "Máy tính", String.format("%,.0f VND/giờ", giaThue), mt});
//            }
//        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi tải danh sách sản phẩm!");
//            e.printStackTrace();
//        }
//    }
//    
//    private void fillComboBoxKhachHang() {
//        DefaultComboBoxModel<KhachHang> model = (DefaultComboBoxModel) cboKhachHang.getModel();
//        model.removeAllElements();
//        try {
//            for (KhachHang kh : khDAO.selectAll()) {
//                model.addElement(kh);
//            }
//        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi tải danh sách khách hàng!");
//        }
//    }
//    
//    private void fillComboBoxNhanVien() {
//        DefaultComboBoxModel<NhanVien> model = (DefaultComboBoxModel) cboNhanVien.getModel();
//        model.removeAllElements();
//        try {
//            for (NhanVien nv : nvDAO.selectAll()) {
//                model.addElement(nv);
//            }
//        } catch (Exception e) {
//            MsgBox.alert(this, "Lỗi tải danh sách nhân viên!");
//        }
//    }
//    //</editor-fold>
//    
//    //<editor-fold defaultstate="collapsed" desc=" CÁC PHƯƠNG THỨC XỬ LÝ ">
//    private void search() {
//        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txtTimKiem.getText(), 0));
//    }
//    
//    private void addItemToInvoice() {
//        int selectedRow = tblChon.getSelectedRow();
//        if (selectedRow == -1) return;
//
//        int modelRow = tblChon.convertRowIndexToModel(selectedRow);
//        Object item = modelTblChon.getValueAt(modelRow, 3);
//        
//        if (modelTblDaChon.getColumnCount() == 5) {
//            modelTblDaChon.addColumn("DATA_OBJECT");
//            tblDaChon.getColumnModel().getColumn(5).setMinWidth(0);
//            tblDaChon.getColumnModel().getColumn(5).setMaxWidth(0);
//        }
//
//        if (item instanceof DichVu) {
//            DichVu dv = (DichVu) item;
//            String soLuongStr = MsgBox.prompt(this, "Nhập số lượng cho " + dv.getTenDV());
//            try {
//                int soLuong = Integer.parseInt(soLuongStr);
//                if (soLuong <= 0) throw new NumberFormatException();
//                double donGia = dv.getGiaDV();
//                modelTblDaChon.addRow(new Object[]{dv.getTenDV(), "Dịch vụ", soLuong, donGia, soLuong * donGia, dv});
//            } catch (NumberFormatException e) { MsgBox.alert(this, "Số lượng không hợp lệ!"); }
//        } else if (item instanceof MayTinh) {
//            MayTinh mt = (MayTinh) item;
//            String soPhutStr = MsgBox.prompt(this, "Nhập số phút sử dụng cho " + mt.getTenMay());
//            try {
//                int soPhut = Integer.parseInt(soPhutStr);
//                if (soPhut <= 0) throw new NumberFormatException();
//                double donGiaGio = mt.getGiaThueTheoGio();
//                double thanhTien = (donGiaGio / 60) * soPhut;
//                modelTblDaChon.addRow(new Object[]{mt.getTenMay(), "Máy tính", soPhut, donGiaGio, thanhTien, mt});
//            } catch (NumberFormatException e) { MsgBox.alert(this, "Số phút không hợp lệ!"); }
//        }
//        updateTotal();
//    }
//    
//    private void updateKhachHangInfo() {
//        if (cboKhachHang.getSelectedItem() instanceof KhachHang) {
//            KhachHang selectedKH = (KhachHang) cboKhachHang.getSelectedItem();
//            jTextField4.setText(String.valueOf(selectedKH.getSDT()));
//        }
//    }
//    
//    private void updateTotal() {
//        double total = 0;
//        for (int i = 0; i < modelTblDaChon.getRowCount(); i++) {
//            total += (double) modelTblDaChon.getValueAt(i, 4);
//        }
//        lblTongTien.setText(String.format("%,.0f VND", total));
//    }
//    
//    private void clearForm() {
//        if (cboKhachHang.getItemCount() > 0) cboKhachHang.setSelectedIndex(0);
//        if (cboNhanVien.getItemCount() > 0) cboNhanVien.setSelectedIndex(0);
//        jcaNgayLap.setDate(new Date());
//        txtGhiChu.setText("");
//        modelTblDaChon.setRowCount(0);
//        updateTotal();
//    }
//    
//    private void saveInvoice() {
//        if (!(cboKhachHang.getSelectedItem() instanceof KhachHang) || !(cboNhanVien.getSelectedItem() instanceof NhanVien)) {
//            MsgBox.alert(this, "Vui lòng chọn khách hàng và nhân viên!");
//            return;
//        }
//        if (modelTblDaChon.getRowCount() == 0) {
//            MsgBox.alert(this, "Hóa đơn phải có ít nhất một mục!");
//            return;
//        }
//
//        try {
//            HoaDon hd = new HoaDon();
//            KhachHang kh = (KhachHang) cboKhachHang.getSelectedItem();
//            NhanVien nv = (NhanVien) cboNhanVien.getSelectedItem();
//            
//            hd.setMaKH(kh.getMaKH()); // Giả sử getMaKH() trả về int
//            hd.setMaNV(nv.getMaNV()); // Giả sử getMaNV() trả về int
//            hd.setNgayLapHoaDon(jcaNgayLap.getDate());
//            hd.setTenHD("Hóa đơn " + kh.getTenKH() + " - " + XDate.toString(new Date(), "dd/MM/yy"));
//            
//            hdDAO.insert(hd);
//
//            HoaDon lastHD = hdDAO.selectLastInserted();
//            if (lastHD == null) {
//                MsgBox.alert(this, "Lỗi: không lấy được hóa đơn vừa tạo!");
//                return;
//            }
//            int maHD = lastHD.getMaHD();
//
//            for(int i=0; i < modelTblDaChon.getRowCount(); i++){
//                Object item = modelTblDaChon.getValueAt(i, 5);
//                int soLuongHoacPhut = (int) modelTblDaChon.getValueAt(i, 2);
//                double thanhTien = (double) modelTblDaChon.getValueAt(i, 4);
//
//                if(item instanceof DichVu){
//                    DichVu dv = (DichVu) item;
//                    ChiTietDichVu ctdv = new ChiTietDichVu(maHD, dv.getMaDV(), soLuongHoacPhut);
//                    ctdvDAO.insert(ctdv);
//                } else if (item instanceof MayTinh){
//                    // SỬA LỖI LOGIC: Cung cấp đầy đủ thông tin cho ChiTietTienMay
//                    MayTinh mt = (MayTinh) item;
//                    ChiTietTienMay cttm = new ChiTietTienMay();
//                    cttm.setMaHD(maHD);
//                    cttm.setMaMay(mt.getMaMay());
//                    cttm.setThoiGianDung(soLuongHoacPhut);
//                    cttm.setThanhTien((int)thanhTien);
//                    
//                    // Bổ sung các trường còn thiếu để tránh lỗi SQL
//                    // Giả định thời gian vào là thời điểm tạo hóa đơn, và thời gian ra là thời điểm hiện tại cộng với số phút dùng.
//                    Date thoiGianVao = new Date();
//                    long thoiGianRaMillis = thoiGianVao.getTime() + (long)soLuongHoacPhut * 60 * 1000;
//                    Date thoiGianRa = new Date(thoiGianRaMillis);
//                    
//                    cttm.setThoiGianVao(thoiGianVao);
//                    cttm.setThoiGianRa(thoiGianRa);
//                    cttm.setGhiChu(txtGhiChu.getText());
//                    
//                    cttmDAO.insert(cttm);
//                }
//            }
//            
//            MsgBox.alert(this, "Lưu hóa đơn thành công! Mã HD: " + maHD);
//            clearForm();
//
//        } catch (Exception e) {
//            MsgBox.alert(this, "Lưu hóa đơn thất bại!");
//            e.printStackTrace();
//        }
//    }
//
//    private void printInvoice() {
//        MsgBox.alert(this, "Chức năng in hóa đơn đang được phát triển."); //
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblChon = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        cboKhachHang = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        txtSDT = new javax.swing.JLabel();
        txtSDT1 = new javax.swing.JLabel();
        txtSDT2 = new javax.swing.JLabel();
        txtSDT3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jcaNgayLap = new com.toedter.calendar.JDateChooser();
        cboNhanVien = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDaChon = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnLuu = new javax.swing.JButton();
        btnInHD = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("QUẢN LÝ HÓA ĐƠN");

        btnTimKiem.setText("Tìm kiếm");

        tblChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên mục", "Loại", "Đơn giá"
            }
        ));
        jScrollPane3.setViewportView(tblChon);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnTimKiem)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("BẢNG HÓA ĐƠN");
        jLabel2.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jLabel2ComponentRemoved(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Khách hàng: ");

        txtSDT.setText("SĐT");

        txtSDT1.setText("Nhân viên");

        txtSDT2.setText("Ngày lập");

        txtSDT3.setText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboKhachHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT)
                            .addComponent(txtSDT2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcaNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSDT1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboNhanVien, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtSDT3)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSDT1)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT3)
                    .addComponent(txtSDT2)
                    .addComponent(jcaNgayLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 255));
        jLabel4.setText("THÔNG TIN");
        jLabel4.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentRemoved(java.awt.event.ContainerEvent evt) {
                jLabel4ComponentRemoved(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblDaChon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên mục", "Loại", "Thời gian/Phút", "Đơn giá", "Thành tiền"
            }
        ));
        jScrollPane2.setViewportView(tblDaChon);

        jLabel5.setText("Tổng tiền: ");

        jLabel6.setText("Khách trả: ");

        lblTongTien.setText("0 VND");

        jLabel8.setText("0VND");

        btnLuu.setText("Lưu");
        btnLuu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuActionPerformed(evt);
            }
        });

        btnInHD.setText("In hóa đơn");

        btnLamMoi.setText("Làm mới");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(btnLuu, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnInHD, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblTongTien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLuu)
                    .addComponent(btnInHD)
                    .addComponent(btnLamMoi))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jLabel2ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel2ComponentRemoved

    private void jLabel4ComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jLabel4ComponentRemoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel4ComponentRemoved

    private void btnLuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuuActionPerformed

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
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyHoaDonJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                QuanLyHoaDonJDialog dialog = new QuanLyHoaDonJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnInHD;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLuu;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JComboBox<String> cboKhachHang;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField4;
    private com.toedter.calendar.JDateChooser jcaNgayLap;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JTable tblChon;
    private javax.swing.JTable tblDaChon;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JLabel txtSDT;
    private javax.swing.JLabel txtSDT1;
    private javax.swing.JLabel txtSDT2;
    private javax.swing.JLabel txtSDT3;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
