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
public class ChiTietTienMay {
    private int MaHD;
    private int MaMay;
    private Date ThoiGianVao;
    private Date ThoiGianRa;
    private int ThoiGianDung;
    private int ThanhTien;
    private String GhiChu;

    public ChiTietTienMay() {
    }

    public ChiTietTienMay(int MaHD, int MaMay, Date ThoiGianVao, Date ThoiGianRa, int ThoiGianDung, int ThanhTien, String GhiChu) {
        this.MaHD = MaHD;
        this.MaMay = MaMay;
        this.ThoiGianVao = ThoiGianVao;
        this.ThoiGianRa = ThoiGianRa;
        this.ThoiGianDung = ThoiGianDung;
        this.ThanhTien = ThanhTien;
        this.GhiChu = GhiChu;
    }

    public int getMaHD() {
        return MaHD;
    }

    public void setMaHD(int MaHD) {
        this.MaHD = MaHD;
    }

    public int getMaMay() {
        return MaMay;
    }

    public void setMaMay(int MaMay) {
        this.MaMay = MaMay;
    }

    public Date getThoiGianVao() {
        return ThoiGianVao;
    }

    public void setThoiGianVao(Date ThoiGianVao) {
        this.ThoiGianVao = ThoiGianVao;
    }

    public Date getThoiGianRa() {
        return ThoiGianRa;
    }

    public void setThoiGianRa(Date ThoiGianRa) {
        this.ThoiGianRa = ThoiGianRa;
    }

    public int getThoiGianDung() {
        return ThoiGianDung;
    }

    public void setThoiGianDung(int ThoiGianDung) {
        this.ThoiGianDung = ThoiGianDung;
    }

    public int getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
    
   
}
