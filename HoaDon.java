/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.net.entity;

import java.util.Date;

/**
 *
 * @author ASUS
 */
public class HoaDon {
    private int MaHD;
    private String TenHD;
    private Date NgayLapHoaDon;
    private int MaNV;
    private int MaKH;

    public HoaDon() {
    }

    public HoaDon(int MaHD, String TenHD, Date NgayLapHoaDon, int MaNV, int MaKH) {
        this.MaHD = MaHD;
        this.TenHD = TenHD;
        this.NgayLapHoaDon = NgayLapHoaDon;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int MaHD) {
        this.MaHD = MaHD;
    }

    public String getTenHD() {
        return TenHD;
    }

    public void setTenHD(String TenHD) {
        this.TenHD = TenHD;
    }

    public Date getNgayLapHoaDon() {
        return NgayLapHoaDon;
    }

    public void setNgayLapHoaDon(Date NgayLapHoaDon) {
        this.NgayLapHoaDon = NgayLapHoaDon;
    }

    public int getMaNV() {
        return MaNV;
    }

    public void setMaNV(int MaNV) {
        this.MaNV = MaNV;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    
}
