package poly.net.entity;

/**
 *
 * @author YourName (hoặc pc)
 */
public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String quyen;
    private boolean trangThai;

    public TaiKhoan() {
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String quyen, boolean trangThai) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.quyen = quyen;
        this.trangThai = trangThai;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}