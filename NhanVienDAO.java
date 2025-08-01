/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.net.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.NhanVien;
import poly.net.utils.JdbcHelper;

/**
 *
 * @author ASUS
 */
public class NhanVienDAO { // Removed extends PolyNetDAO<NhanVien, String>

    private final String INSERT_SQL = "INSERT INTO NhanVien (HoTen, SDT, VaiTro, TenDangNhap) VALUES (?, ?, ?, ?)";
    private final String UPDATE_SQL = "UPDATE NhanVien SET HoTen = ?, SDT = ?, VaiTro = ?, TenDangNhap = ? WHERE MaNV = ?";
    private final String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
    private final String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
    private final String SELECT_BY_ID_SQL = "SELECT * FROM NhanVien WHERE MaNV = ?";
    private final String SELECT_BY_TENDANGNHAP_SQL = "SELECT * FROM NhanVien WHERE TenDangNhap = ?"; // New SQL query

    public void insert(NhanVien entity) {
        JdbcHelper.update(INSERT_SQL, entity.getHoTen(), entity.getSDT(), entity.getVaiTro(), entity.getTenDangNhap());
    }

    public void update(NhanVien entity) {
        JdbcHelper.update(UPDATE_SQL, entity.getHoTen(), entity.getSDT(), entity.getVaiTro(), entity.getTenDangNhap(), entity.getMaNV());
    }

    public void delete(String id) {
        JdbcHelper.update(DELETE_SQL, id);
    }

    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public NhanVien selectByTenDangNhap(String tenDangNhap) {
        List<NhanVien> list = selectBySql(SELECT_BY_TENDANGNHAP_SQL, tenDangNhap);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try (ResultSet rs = JdbcHelper.query(sql, args)) {
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setSDT(rs.getString("SDT"));
                entity.setVaiTro(rs.getString("VaiTro"));
                entity.setTenDangNhap(rs.getString("TenDangNhap"));
                list.add(entity);
            }
        } catch (Exception e) {
            System.err.println("Lỗi truy vấn dữ liệu nhân viên: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return list;
    }
}