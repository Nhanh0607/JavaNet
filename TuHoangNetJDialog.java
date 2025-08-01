/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package poly.net.ui;

import java.awt.Color; // Import Color
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList; // Thêm import này
import java.util.HashMap;    // Thêm import này
import java.util.Map;        // Thêm import này

import javax.swing.Timer;
import poly.net.utils.XImage;
import poly.net.utils.Auth; // Thêm Auth để kiểm tra đăng nhập
import poly.net.utils.MsgBox; // Thêm MsgBox để hiển thị thông báo
import poly.net.dao.MayTinhDAO; // Thêm MayTinhDAO
import poly.net.entity.MayTinh; // Thêm MayTinh entity

/**
 *
 * @author ASUS
 */
public class TuHoangNetJDialog extends javax.swing.JDialog {

    private java.awt.Frame parentFrame;
    private MayTinhDAO mtDao = new MayTinhDAO(); // Khởi tạo MayTinhDAO


    public TuHoangNetJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.parentFrame = parent;
        init();
    }

    /**
     * Creates new form MainJDialog
     */
    void init() {
        setSize(1150, 800);

        setIconImage(XImage.getAppIcon());
        setLocationRelativeTo(null);
        setTitle("QUẢN LÝ HỆ THỐNG");
        new Timer(1000, new ActionListener() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss a");

            @Override
            public void actionPerformed(ActionEvent e) {
                lblDongHo.setText(format.format(new Date()));
                loadTrangThaiMayTinh(); // Cập nhật trạng thái máy tính mỗi giây
            }
        }).start();
        this.openWelcome();
        this.openLogin();
        loadTrangThaiMayTinh(); // Tải trạng thái ban đầu khi khởi động
    }

    void openWelcome() {
        new ChaoJDialog(this.parentFrame, true).setVisible(true);
    }

    void openLogin() {
        new DangNhapJDialog(this.parentFrame, true).setVisible(true);
    }

    void openDoiMatKhau() {
        if (Auth.isLogin()) {
            new DoiMatKhauJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void openQuanLyNhanVien() {
        if (Auth.isLogin()) {
            new QuanLyNhanVienJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void openQuanLyMayTinh() {
        if (Auth.isLogin()) {
            new QuanLyMayTinhJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }
    void openQuanLyTaiKhoan() {
        if (Auth.isLogin()) {
            new QuanLyTaiKhoanJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void openQuanLyDichVu() {
        if (Auth.isLogin()) {
            new QuanLyDichVuJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void openQuanLyHoaDon() {
        if (Auth.isLogin()) {
            new abcc(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }
    
    void openQuanLyKhachHang(){
        if(Auth.isLogin()){
            new QuanLyKhachHangJDialog(this.parentFrame, true).setVisible(true);
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void openBaoCaoThongKe() {
        if (Auth.isLogin()) {
            // Chỉ quản lý mới được xem báo cáo thống kê
            if (Auth.user.getVaiTro().equalsIgnoreCase("Quản lý")) { // Giả sử vai trò là "Quản lý"
                new BaoCaoThongKeJDialog(this.parentFrame, true).setVisible(true);
            } else {
                MsgBox.alert(this, "Bạn không có quyền truy cập chức năng này!");
            }
        } else {
            MsgBox.alert(this, "Vui lòng đăng nhập để sử dụng chức năng này!");
        }
    }

    void ketThuc() {
        if (MsgBox.confirm(this, "Bạn có muốn kết thúc ứng dụng?")) {
            System.exit(0);
        }
    }

    void dangXuat() {
        Auth.clear(); // Xóa thông tin người dùng
        MsgBox.alert(this, "Bạn đã đăng xuất!");
        this.openLogin(); // Mở lại cửa sổ đăng nhập
    }

    /**
     * Phương thức này tải trạng thái của các máy tính và cập nhật màu nền tương ứng.
     * Nó giả định rằng MaMay từ 1 đến 14 sẽ tương ứng với jPanel2 đến jPanel15.
     */
    private void loadTrangThaiMayTinh() {
        try {
            List<MayTinh> list = mtDao.selectAll(); // Lấy tất cả máy tính từ CSDL

            // Khởi tạo Map để ánh xạ MaMay tới JPanel
            // Đảm bảo rằng các jPanel đã được khởi tạo và là biến thành viên
            Map<Integer, javax.swing.JPanel> computerPanels = new HashMap<>();
            computerPanels.put(1, may1);
            computerPanels.put(2, may2);
            computerPanels.put(3, may3);
            computerPanels.put(4, may4);
            computerPanels.put(5, may5);
            computerPanels.put(6, may6);
            computerPanels.put(7, may7);
            computerPanels.put(8, may8);
            computerPanels.put(9, may9);
            computerPanels.put(10, may10);

            // Map for JLabels displaying computer names
            Map<Integer, javax.swing.JLabel> computerNameLabels = new HashMap<>();
            computerNameLabels.put(1, lblTenMay);
            computerNameLabels.put(2, lblTenMay1);
            computerNameLabels.put(3, lblTenMay2);
            computerNameLabels.put(4, lblTenMay3);
            computerNameLabels.put(5, lblTenMay4);
            computerNameLabels.put(6, lblTenMay5);
            computerNameLabels.put(7, lblTenMay6); // lblTenMay13 corresponds to may7
            computerNameLabels.put(8, lblTenMay7);
            computerNameLabels.put(9, lblTenMay8);
            computerNameLabels.put(10, lblTenMay9);


            for (MayTinh mt : list) {
                javax.swing.JPanel currentPanel = computerPanels.get(mt.getMaMay());
                javax.swing.JLabel currentNameLabel = computerNameLabels.get(mt.getMaMay());

                if (currentPanel != null) {
                    switch (mt.getTinhTrang()) {
                        case "Hoạt động": // Trạng thái hoạt động
                            currentPanel.setBackground(Color.GREEN); // Màu xanh nhạt
                            break;
                        case "Không hoạt động": // Trạng thái không hoạt động
                            currentPanel.setBackground(Color.RED); // Màu đỏ nhạt
                            break;
                        case "Đang sửa": // Trạng thái hỏng
                            currentPanel.setBackground(Color.GRAY); // Màu xám
                            break;
                        default:
                            currentPanel.setBackground(Color.LIGHT_GRAY); // Màu mặc định cho trạng thái không xác định
                            break;
                    }
                }
                if (currentNameLabel != null) {
                    currentNameLabel.setText(mt.getTenMay()); // Set the computer name
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi khi tải trạng thái máy tính: " + e.getMessage());
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        btnDoiMatKhau = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        btnNhanVien = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnHoaDon = new javax.swing.JButton();
        btnDichVu = new javax.swing.JButton();
        btnMayTinh = new javax.swing.JButton();
        btnBaoCao = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblTrangThai = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        may1 = new javax.swing.JPanel();
        lblTenMay = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        may2 = new javax.swing.JPanel();
        lblTenMay1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        may3 = new javax.swing.JPanel();
        lblTenMay2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        may4 = new javax.swing.JPanel();
        lblTenMay3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        may5 = new javax.swing.JPanel();
        lblTenMay4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        may6 = new javax.swing.JPanel();
        lblTenMay5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        may8 = new javax.swing.JPanel();
        lblTenMay6 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        may9 = new javax.swing.JPanel();
        lblTenMay8 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        may10 = new javax.swing.JPanel();
        lblTenMay9 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        may7 = new javax.swing.JPanel();
        lblTenMay7 = new javax.swing.JLabel();
        jButton14 = new javax.swing.JButton();
        may11 = new javax.swing.JPanel();
        lblTenMay10 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        may12 = new javax.swing.JPanel();
        lblTenMay11 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        may13 = new javax.swing.JPanel();
        lblTenMay12 = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        may14 = new javax.swing.JPanel();
        lblTenMay13 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniDoiMatKhau = new javax.swing.JMenuItem();
        mniDangXuat = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniKetThuc = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mniNhanVien = new javax.swing.JMenuItem();
        mniDichVu = new javax.swing.JMenuItem();
        mniTaiKhoan = new javax.swing.JMenuItem();
        mniHoaDon = new javax.swing.JMenuItem();
        mniKhachHang = new javax.swing.JMenuItem();
        mniMayTinh = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mniDTTheoNgay = new javax.swing.JMenuItem();
        mniDTTheoNV = new javax.swing.JMenuItem();
        mniDTTheoKH = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniTopDichVu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnDoiMatKhau.setText("ĐỔI MẬT KHẨU");
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        btnTaiKhoan.setText("TÀI KHOẢN");
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        btnDangXuat.setText("ĐĂNG XUẤT");
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        btnNhanVien.setText("NHÂN VIÊN");
        btnNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienActionPerformed(evt);
            }
        });

        btnKhachHang.setText("KHÁCH HÀNG");
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnHoaDon.setText("HÓA ĐƠN");
        btnHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoaDonActionPerformed(evt);
            }
        });

        btnDichVu.setText("DỊCH VỤ");
        btnDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDichVuActionPerformed(evt);
            }
        });

        btnMayTinh.setText("MÁY TÍNH");
        btnMayTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMayTinhActionPerformed(evt);
            }
        });

        btnBaoCao.setText("BÁO CÁO THÔNG KÊ");
        btnBaoCao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaoCaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(btnDoiMatKhau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKhachHang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMayTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnBaoCao)
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMayTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBaoCao, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTrangThai.setText("TU HOANG NET");

        lblDongHo.setText("12:12:12:AM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblTrangThai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblDongHo)
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrangThai)
                    .addComponent(lblDongHo))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Tình trạng máy");

        may1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay.setText("Máy 1");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may1Layout = new javax.swing.GroupLayout(may1);
        may1.setLayout(may1Layout);
        may1Layout.setHorizontalGroup(
            may1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(may1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblTenMay)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        may1Layout.setVerticalGroup(
            may1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay)
                .addGap(14, 14, 14))
        );

        may2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay1.setText("Máy 1");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout may2Layout = new javax.swing.GroupLayout(may2);
        may2.setLayout(may2Layout);
        may2Layout.setHorizontalGroup(
            may2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTenMay1)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(may2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );
        may2Layout.setVerticalGroup(
            may2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay1)
                .addGap(14, 14, 14))
        );

        may3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay2.setText("Máy 1");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may3Layout = new javax.swing.GroupLayout(may3);
        may3.setLayout(may3Layout);
        may3Layout.setHorizontalGroup(
            may3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTenMay2)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(may3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
        );
        may3Layout.setVerticalGroup(
            may3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay2)
                .addGap(14, 14, 14))
        );

        may4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay3.setText("Máy 1");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may4Layout = new javax.swing.GroupLayout(may4);
        may4.setLayout(may4Layout);
        may4Layout.setHorizontalGroup(
            may4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTenMay3)
                .addGap(39, 39, 39))
        );
        may4Layout.setVerticalGroup(
            may4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay3)
                .addGap(14, 14, 14))
        );

        may5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay4.setText("Máy 1");

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may5Layout = new javax.swing.GroupLayout(may5);
        may5.setLayout(may5Layout);
        may5Layout.setHorizontalGroup(
            may5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lblTenMay4)
                .addContainerGap(50, Short.MAX_VALUE))
            .addGroup(may5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        may5Layout.setVerticalGroup(
            may5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay4)
                .addGap(14, 14, 14))
        );

        may6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay5.setText("Máy 1");

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may6Layout = new javax.swing.GroupLayout(may6);
        may6.setLayout(may6Layout);
        may6Layout.setHorizontalGroup(
            may6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may6Layout.createSequentialGroup()
                .addGroup(may6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(may6Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblTenMay5))
                    .addGroup(may6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        may6Layout.setVerticalGroup(
            may6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may6Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay5)
                .addGap(14, 14, 14))
        );

        may8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay6.setText("Máy 1");

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may8Layout = new javax.swing.GroupLayout(may8);
        may8.setLayout(may8Layout);
        may8Layout.setHorizontalGroup(
            may8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may8Layout.createSequentialGroup()
                .addGroup(may8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(may8Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblTenMay6))
                    .addGroup(may8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        may8Layout.setVerticalGroup(
            may8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may8Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay6)
                .addGap(14, 14, 14))
        );

        may9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay8.setText("Máy 1");

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton11MousePressed(evt);
            }
        });

        javax.swing.GroupLayout may9Layout = new javax.swing.GroupLayout(may9);
        may9.setLayout(may9Layout);
        may9Layout.setHorizontalGroup(
            may9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may9Layout.createSequentialGroup()
                .addGroup(may9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(may9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblTenMay8))
                    .addGroup(may9Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        may9Layout.setVerticalGroup(
            may9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay8)
                .addGap(14, 14, 14))
        );

        may10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay9.setText("Máy 1");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may10Layout = new javax.swing.GroupLayout(may10);
        may10.setLayout(may10Layout);
        may10Layout.setHorizontalGroup(
            may10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may10Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTenMay9)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        may10Layout.setVerticalGroup(
            may10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may10Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay9)
                .addGap(14, 14, 14))
        );

        may7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay7.setText("Máy 1");

        jButton14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may7Layout = new javax.swing.GroupLayout(may7);
        may7.setLayout(may7Layout);
        may7Layout.setHorizontalGroup(
            may7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may7Layout.createSequentialGroup()
                .addGroup(may7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(may7Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(lblTenMay7))
                    .addGroup(may7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        may7Layout.setVerticalGroup(
            may7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay7)
                .addGap(14, 14, 14))
        );

        may11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay10.setText("Máy 1");

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may11Layout = new javax.swing.GroupLayout(may11);
        may11.setLayout(may11Layout);
        may11Layout.setHorizontalGroup(
            may11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may11Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTenMay10)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        may11Layout.setVerticalGroup(
            may11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may11Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay10)
                .addGap(14, 14, 14))
        );

        may12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay11.setText("Máy 1");

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may12Layout = new javax.swing.GroupLayout(may12);
        may12.setLayout(may12Layout);
        may12Layout.setHorizontalGroup(
            may12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may12Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTenMay11)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        may12Layout.setVerticalGroup(
            may12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay11)
                .addGap(14, 14, 14))
        );

        may13.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay12.setText("Máy 1");

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may13Layout = new javax.swing.GroupLayout(may13);
        may13.setLayout(may13Layout);
        may13Layout.setHorizontalGroup(
            may13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may13Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTenMay12)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        may13Layout.setVerticalGroup(
            may13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay12)
                .addGap(14, 14, 14))
        );

        may14.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTenMay13.setText("Máy 1");

        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/poly/net/icon/Computer.png"))); // NOI18N

        javax.swing.GroupLayout may14Layout = new javax.swing.GroupLayout(may14);
        may14.setLayout(may14Layout);
        may14Layout.setHorizontalGroup(
            may14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(may14Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(lblTenMay13)
                .addGap(38, 38, 38))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        may14Layout.setVerticalGroup(
            may14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, may14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTenMay13)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(may1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(may2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(may3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(may4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(may11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(may14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(may12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(may8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(may13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(may1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(may9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(may12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(may14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jMenu1.setText("Hệ thống");

        mniDoiMatKhau.setText("Đổi mật khẩu");
        mniDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDoiMatKhauActionPerformed(evt);
            }
        });
        jMenu1.add(mniDoiMatKhau);

        mniDangXuat.setText("Đăng xuất");
        mniDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDangXuatActionPerformed(evt);
            }
        });
        jMenu1.add(mniDangXuat);
        jMenu1.add(jSeparator1);

        mniKetThuc.setText("Kết thúc");
        mniKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKetThucActionPerformed(evt);
            }
        });
        jMenu1.add(mniKetThuc);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Quản lý");

        mniNhanVien.setText("Nhân viên");
        mniNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniNhanVienActionPerformed(evt);
            }
        });
        jMenu2.add(mniNhanVien);

        mniDichVu.setText("Dịch vụ");
        mniDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDichVuActionPerformed(evt);
            }
        });
        jMenu2.add(mniDichVu);

        mniTaiKhoan.setText("Tài khoản");
        mniTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTaiKhoanActionPerformed(evt);
            }
        });
        jMenu2.add(mniTaiKhoan);

        mniHoaDon.setText("Hóa Đơn");
        mniHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniHoaDonActionPerformed(evt);
            }
        });
        jMenu2.add(mniHoaDon);

        mniKhachHang.setText("Khách Hàng");
        mniKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniKhachHangActionPerformed(evt);
            }
        });
        jMenu2.add(mniKhachHang);

        mniMayTinh.setText("Máy tính");
        mniMayTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniMayTinhActionPerformed(evt);
            }
        });
        jMenu2.add(mniMayTinh);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Thống kê");

        mniDTTheoNgay.setText("Doanh thu theo ngày");
        mniDTTheoNgay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniDTTheoNgayActionPerformed(evt);
            }
        });
        jMenu3.add(mniDTTheoNgay);

        mniDTTheoNV.setText("Doanh thu theo nhân viên");
        jMenu3.add(mniDTTheoNV);

        mniDTTheoKH.setText("Doanh thu theo khách hàng");
        jMenu3.add(mniDTTheoKH);
        jMenu3.add(jSeparator2);

        mniTopDichVu.setText("Top dịch vụ bán chạy");
        jMenu3.add(mniTopDichVu);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(12, 12, 12)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienActionPerformed
        // TODO add your handling code here:
        openQuanLyNhanVien();
    }//GEN-LAST:event_btnNhanVienActionPerformed

    private void btnDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDichVuActionPerformed
        // TODO add your handling code here:
        openQuanLyDichVu();
    }//GEN-LAST:event_btnDichVuActionPerformed

    private void mniTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTaiKhoanActionPerformed
        // TODO add your handling code here:
        openQuanLyTaiKhoan();
    }//GEN-LAST:event_mniTaiKhoanActionPerformed

    private void mniDTTheoNgayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDTTheoNgayActionPerformed
        // TODO add your handling code here:
        openBaoCaoThongKe();
    }//GEN-LAST:event_mniDTTheoNgayActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void mniKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKetThucActionPerformed
        // TODO add your handling code here:
        ketThuc();
    }//GEN-LAST:event_mniKetThucActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        // TODO add your handling code here:
        openQuanLyKhachHang();
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoaDonActionPerformed
        // TODO add your handling code here:
        openQuanLyHoaDon();
    }//GEN-LAST:event_btnHoaDonActionPerformed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed
        // TODO add your handling code here:
        openDoiMatKhau();
    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        // TODO add your handling code here:
        openQuanLyTaiKhoan();
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnMayTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMayTinhActionPerformed
        // TODO add your handling code here:
        openQuanLyMayTinh();
    }//GEN-LAST:event_btnMayTinhActionPerformed

    private void mniDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDoiMatKhauActionPerformed
        // TODO add your handling code here:
        openDoiMatKhau();
    }//GEN-LAST:event_mniDoiMatKhauActionPerformed

    private void mniDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDangXuatActionPerformed
        // TODO add your handling code here:
        dangXuat();
    }//GEN-LAST:event_mniDangXuatActionPerformed

    private void mniNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniNhanVienActionPerformed
        // TODO add your handling code here:
        openQuanLyNhanVien();
    }//GEN-LAST:event_mniNhanVienActionPerformed

    private void mniDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniDichVuActionPerformed
        // TODO add your handling code here:
        openQuanLyDichVu();
    }//GEN-LAST:event_mniDichVuActionPerformed

    private void mniHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniHoaDonActionPerformed
        // TODO add your handling code here:
        openQuanLyHoaDon();
    }//GEN-LAST:event_mniHoaDonActionPerformed

    private void mniKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniKhachHangActionPerformed
        // TODO add your handling code here:
        openQuanLyKhachHang();
    }//GEN-LAST:event_mniKhachHangActionPerformed

    private void mniMayTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniMayTinhActionPerformed
        // TODO add your handling code here:
        openQuanLyMayTinh();
    }//GEN-LAST:event_mniMayTinhActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnBaoCaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaoCaoActionPerformed
        // TODO add your handling code here:
        openBaoCaoThongKe();
    }//GEN-LAST:event_btnBaoCaoActionPerformed

    private void jButton11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton11MousePressed

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
            java.util.logging.Logger.getLogger(TuHoangNetJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TuHoangNetJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TuHoangNetJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TuHoangNetJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TuHoangNetJDialog dialog = new TuHoangNetJDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBaoCao;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnDichVu;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnHoaDon;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnMayTinh;
    private javax.swing.JButton btnNhanVien;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblTenMay;
    private javax.swing.JLabel lblTenMay1;
    private javax.swing.JLabel lblTenMay10;
    private javax.swing.JLabel lblTenMay11;
    private javax.swing.JLabel lblTenMay12;
    private javax.swing.JLabel lblTenMay13;
    private javax.swing.JLabel lblTenMay2;
    private javax.swing.JLabel lblTenMay3;
    private javax.swing.JLabel lblTenMay4;
    private javax.swing.JLabel lblTenMay5;
    private javax.swing.JLabel lblTenMay6;
    private javax.swing.JLabel lblTenMay7;
    private javax.swing.JLabel lblTenMay8;
    private javax.swing.JLabel lblTenMay9;
    private javax.swing.JLabel lblTrangThai;
    private javax.swing.JPanel may1;
    private javax.swing.JPanel may10;
    private javax.swing.JPanel may11;
    private javax.swing.JPanel may12;
    private javax.swing.JPanel may13;
    private javax.swing.JPanel may14;
    private javax.swing.JPanel may2;
    private javax.swing.JPanel may3;
    private javax.swing.JPanel may4;
    private javax.swing.JPanel may5;
    private javax.swing.JPanel may6;
    private javax.swing.JPanel may7;
    private javax.swing.JPanel may8;
    private javax.swing.JPanel may9;
    private javax.swing.JMenuItem mniDTTheoKH;
    private javax.swing.JMenuItem mniDTTheoNV;
    private javax.swing.JMenuItem mniDTTheoNgay;
    private javax.swing.JMenuItem mniDangXuat;
    private javax.swing.JMenuItem mniDichVu;
    private javax.swing.JMenuItem mniDoiMatKhau;
    private javax.swing.JMenuItem mniHoaDon;
    private javax.swing.JMenuItem mniKetThuc;
    private javax.swing.JMenuItem mniKhachHang;
    private javax.swing.JMenuItem mniMayTinh;
    private javax.swing.JMenuItem mniNhanVien;
    private javax.swing.JMenuItem mniTaiKhoan;
    private javax.swing.JMenuItem mniTopDichVu;
    // End of variables declaration//GEN-END:variables
}
