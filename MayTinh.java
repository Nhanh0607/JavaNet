/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.net.entity;

/**
 *
 * @author ASUS
 */
public class MayTinh {
    private int MaMay;
    private String TenMay;
    private String LoaiMay;
    private String CauHinh;
    private int giaThueTheoGio;
    private String tinhTrang;

    public MayTinh() {
    }

    public MayTinh(int MaMay, String TenMay, String LoaiMay, String CauHinh, int giaThueTheoGio, String tinhTrang) {
        this.MaMay = MaMay;
        this.TenMay = TenMay;
        this.LoaiMay = LoaiMay;
        this.CauHinh = CauHinh;
        this.giaThueTheoGio = giaThueTheoGio;
        this.tinhTrang = tinhTrang;
    }

    public int getMaMay() {
        return MaMay;
    }

    public void setMaMay(int MaMay) {
        this.MaMay = MaMay;
    }

    public String getTenMay() {
        return TenMay;
    }

    public void setTenMay(String TenMay) {
        this.TenMay = TenMay;
    }

    public String getLoaiMay() {
        return LoaiMay;
    }

    public void setLoaiMay(String LoaiMay) {
        this.LoaiMay = LoaiMay;
    }

    public String getCauHinh() {
        return CauHinh;
    }

    public void setCauHinh(String CauHinh) {
        this.CauHinh = CauHinh;
    }

    public int getGiaThueTheoGio() {
        return giaThueTheoGio;
    }

    public void setGiaThueTheoGio(int giaThueTheoGio) {
        this.giaThueTheoGio = giaThueTheoGio;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    
}
