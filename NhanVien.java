/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.net.entity;

/**
 *
 * @author ASUS
 */
public class NhanVien {

    private String maNV;
    private String SDT; // Changed from int to String
    private String hoTen;
    private String tenDangNhap;
    private String vaiTro;
    private String matKhau;

    public NhanVien() {
    }

    public NhanVien(String maNV, String SDT, String hoTen, String tenDangNhap, String vaiTro) {
        this.maNV = maNV;
        this.SDT = SDT;
        this.hoTen = hoTen;
        this.tenDangNhap = tenDangNhap;
        this.vaiTro = vaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getSDT() { // Changed return type to String
        return SDT;
    }

    public void setSDT(String SDT) { // Changed parameter type to String
        this.SDT = SDT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public NhanVien(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    @Override
    public String toString() {
        return this.getHoTen();
    }
}
