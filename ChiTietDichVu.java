package poly.net.entity;

/**
 * Lớp Entity cho bảng ChiTietDichVu.
 * Ánh xạ các cột trong bảng ChiTietDichVu vào các thuộc tính của đối tượng Java.
 */
public class ChiTietDichVu {
    private int maHD;
    private int maDV;
    private int soLuongBan;

    public ChiTietDichVu() {
    }

    public ChiTietDichVu(int maHD, int maDV, int soLuongBan) {
        this.maHD = maHD;
        this.maDV = maDV;
        this.soLuongBan = soLuongBan;
    }

    public int getMaHD() {
        return maHD;
    }

    public void setMaHD(int maHD) {
        this.maHD = maHD;
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
    
}    
