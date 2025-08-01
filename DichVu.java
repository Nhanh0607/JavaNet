package poly.net.entity;

public class DichVu {
    private int maDV;
    private String tenDV;
    private int soLuong;
    private int giaDV;
    private String ghiChu;

    public DichVu() {
    }

    public DichVu(int maDV, String tenDV, int soLuong, int giaDV, String ghiChu) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.soLuong = soLuong;
        this.giaDV = giaDV;
        this.ghiChu = ghiChu;
    }

    public int getMaDV() {
        return maDV;
    }

    public void setMaDV(int maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaDV() {
        return giaDV;
    }

    public void setGiaDV(int giaDV) {
        this.giaDV = giaDV;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
}