package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.DichVu;
import poly.net.utils.JdbcHelper;

public class DichVuDAO {

    // Sửa lại các câu lệnh SQL cho chính xác
    final String INSERT_SQL = "INSERT INTO DichVu (TenDV, SoLuongTon, GiaDV, GhiChu) VALUES (?, ?, ?, ?)";
    final String UPDATE_SQL = "UPDATE DichVu SET TenDV = ?, SoLuongTon = ?, GiaDV = ?, GhiChu = ? WHERE MaDV = ?";
    final String DELETE_SQL = "DELETE FROM DichVu WHERE MaDV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM DichVu";
    final String SELECT_BY_ID_SQL = "SELECT * FROM DichVu WHERE MaDV = ?";
    final String SELECT_BY_KEYWORD_SQL = "SELECT * FROM DichVu WHERE TenDV LIKE ?";

    public void insert(DichVu entity) {
        JdbcHelper.update(INSERT_SQL,
                entity.getTenDV(),
                entity.getSoLuong(),
                entity.getGiaDV(),
                entity.getGhiChu());
    }

    public void update(DichVu entity) {
        JdbcHelper.update(UPDATE_SQL,
                entity.getTenDV(),
                entity.getSoLuong(),
                entity.getGiaDV(),
                entity.getGhiChu(),
                entity.getMaDV());
    }

    public void delete(int id) { // Sửa kiểu dữ liệu của ID thành int
        JdbcHelper.update(DELETE_SQL, id);
    }

    public DichVu selectById(int id) { // Sửa kiểu dữ liệu của ID thành int
        List<DichVu> list = this.selectBySql(SELECT_BY_ID_SQL, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<DichVu> selectAll() {
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public List<DichVu> selectByKeyword(String keyword) {
        return this.selectBySql(SELECT_BY_KEYWORD_SQL, "%" + keyword + "%");
    }

    protected List<DichVu> selectBySql(String sql, Object... args) {
        List<DichVu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                DichVu entity = new DichVu();
                // Lấy dữ liệu theo đúng kiểu và tên cột
                entity.setMaDV(rs.getInt("MaDV"));
                entity.setTenDV(rs.getString("TenDV"));
                entity.setSoLuong(rs.getInt("SoLuongTon"));
                entity.setGiaDV(rs.getInt("GiaDV"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    // Đặt trong lớp DichVuDAO.java

    public double getGiaDV(int maDV) {
        String sql = "SELECT GiaDV FROM DichVu WHERE MaDV = ?";
        try {
            java.sql.ResultSet rs = poly.net.utils.JdbcHelper.query(sql, maDV);
            if (rs.next()) {
                double gia = rs.getDouble("GiaDV");
                rs.getStatement().getConnection().close(); // Quan trọng: Đóng kết nối
                return gia;
            }
            rs.getStatement().getConnection().close(); // Đóng cả khi không tìm thấy
        } catch (java.sql.SQLException e) {
            throw new RuntimeException("Lỗi truy vấn giá dịch vụ", e);
        }
        return 0; // Trả về 0 nếu không tìm thấy
    }
}
