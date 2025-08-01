package poly.net.entity;

/**
 *
 * @author ASUS
 */
public class KhachHang {

    private String MaKH;
    private String TenKH;
    private String GioiTinh;
    private int SDT;
    private String GhiChu;
    private String TenDangNhap;

    public KhachHang() {
    }

    // Constructor cũng được sửa lại cho đúng tham số
    public KhachHang(String MaKH, String TenKH, String GioiTinh, int SDT, String GhiChu, String tenDangNhap) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.GioiTinh = GioiTinh;
        this.SDT = SDT;
        this.GhiChu = GhiChu;
        this.TenDangNhap = tenDangNhap;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.TenDangNhap = tenDangNhap;
    }

    @Override
    public String toString() {
        return this.getTenKH();
    }
}