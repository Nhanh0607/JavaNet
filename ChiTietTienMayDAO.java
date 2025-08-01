/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.ChiTietTienMay;
import poly.net.utils.JdbcHelper;

/**
 * Lớp Data Access Object (DAO) cho bảng ChiTietTienMay.
 * Cung cấp các phương thức để thao tác với dữ liệu chi tiết tiền máy trong cơ sở dữ liệu.
 */
public class ChiTietTienMayDAO {

    /**
     * Thêm một chi tiết tiền máy mới vào cơ sở dữ liệu.
     *
     * @param model Đối tượng ChiTietTienMay chứa thông tin chi tiết tiền máy cần thêm.
     */
    public void insert(ChiTietTienMay model) {
        String sql = "INSERT INTO ChiTietTienMay (MaHD, MaMay, ThoiGianVao, ThoiGianRa, ThoiGianDung, ThanhTien, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.update(sql,
                model.getMaHD(),
                model.getMaMay(),
                model.getThoiGianVao(),
                model.getThoiGianRa(),
                model.getThoiGianDung(),
                model.getThanhTien(),
                model.getGhiChu());
    }

    /**
     * Cập nhật thông tin chi tiết tiền máy trong cơ sở dữ liệu.
     *
     * @param model Đối tượng ChiTietTienMay chứa thông tin chi tiết tiền máy cần cập nhật.
     */
    public void update(ChiTietTienMay model) {
        String sql = "UPDATE ChiTietTienMay SET ThoiGianVao=?, ThoiGianRa=?, ThoiGianDung=?, ThanhTien=?, GhiChu=? WHERE MaHD=? AND MaMay=?";
        JdbcHelper.update(sql,
                model.getThoiGianVao(),
                model.getThoiGianRa(),
                model.getThoiGianDung(),
                model.getThanhTien(),
                model.getGhiChu(),
                model.getMaHD(),
                model.getMaMay());
    }

    /**
     * Xóa một chi tiết tiền máy khỏi cơ sở dữ liệu dựa trên MaHD và MaMay.
     *
     * @param maHD Mã hóa đơn.
     * @param maMay Mã máy.
     */
    public void delete(int maHD, int maMay) {
        String sql = "DELETE FROM ChiTietTienMay WHERE MaHD=? AND MaMay=?";
        JdbcHelper.update(sql, maHD, maMay);
    }

    /**
     * Truy vấn tất cả các chi tiết tiền máy trong cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng ChiTietTienMay.
     */
    public List<ChiTietTienMay> selectAll() {
        String sql = "SELECT * FROM ChiTietTienMay";
        return selectBySql(sql);
    }

    /**
     * Truy vấn các chi tiết tiền máy dựa trên mã hóa đơn.
     *
     * @param maHD Mã hóa đơn cần tìm.
     * @return Danh sách các đối tượng ChiTietTienMay liên quan đến hóa đơn đó.
     */
    public List<ChiTietTienMay> selectByMaHD(int maHD) {
        String sql = "SELECT * FROM ChiTietTienMay WHERE MaHD=?";
        return selectBySql(sql, maHD);
    }
    
    /**
     * Truy vấn các chi tiết tiền máy dựa trên mã máy.
     *
     * @param maMay Mã máy cần tìm.
     * @return Danh sách các đối tượng ChiTietTienMay liên quan đến máy đó.
     */
    public List<ChiTietTienMay> selectByMaMay(int maMay) {
        String sql = "SELECT * FROM ChiTietTienMay WHERE MaMay=?";
        return selectBySql(sql, maMay);
    }

    /**
     * Truy vấn một chi tiết tiền máy cụ thể dựa trên MaHD và MaMay.
     *
     * @param maHD Mã hóa đơn.
     * @param maMay Mã máy.
     * @return Đối tượng ChiTietTienMay nếu tìm thấy, ngược lại trả về null.
     */
    public ChiTietTienMay selectByMaHDAndMaMay(int maHD, int maMay) {
        String sql = "SELECT * FROM ChiTietTienMay WHERE MaHD=? AND MaMay=?";
        List<ChiTietTienMay> list = selectBySql(sql, maHD, maMay);
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * Phương thức nội bộ để thực thi truy vấn SQL và ánh xạ kết quả vào danh sách ChiTietTienMay.
     *
     * @param sql Câu lệnh SQL.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Danh sách các đối tượng ChiTietTienMay.
     */
    protected List<ChiTietTienMay> selectBySql(String sql, Object... args) {
        List<ChiTietTienMay> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietTienMay entity = new ChiTietTienMay();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaMay(rs.getInt("MaMay"));
                entity.setThoiGianVao(rs.getTimestamp("ThoiGianVao")); // Sử dụng getTimestamp cho cột DATETIME
                entity.setThoiGianRa(rs.getTimestamp("ThoiGianRa"));   // Sử dụng getTimestamp cho cột DATETIME
                entity.setThoiGianDung(rs.getInt("ThoiGianDung"));
                entity.setThanhTien(rs.getInt("ThanhTien")); // Sử dụng getInt nếu DECIMAL có thể ép kiểu an toàn, hoặc getDouble/getBigDecimal
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close(); // Đóng kết nối sau khi sử dụng ResultSet
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi truy vấn dữ liệu ChiTietTienMay: " + ex.getMessage(), ex);
        }
        return list;
    }
}