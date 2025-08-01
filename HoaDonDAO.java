package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.HoaDon;
import poly.net.utils.JdbcHelper;

/**
 *
 * @author pc
 */
public class HoaDonDAO {

    /**
     * Thêm một hóa đơn mới vào cơ sở dữ liệu. MaHD là IDENTITY nên không cần
     * truyền vào khi INSERT. GhiChu không có trong entity nhưng là NULLABLE
     * trong DB nên được bỏ qua.
     *
     * @param model Đối tượng HoaDon chứa thông tin hóa đơn cần thêm.
     */
    public void insert(HoaDon model) {
        String sql = "INSERT INTO HoaDon (TenHD, NgayLapHoaDon, MaNV, MaKH) VALUES (?, ?, ?, ?)";
        JdbcHelper.update(sql,
                model.getTenHD(),
                model.getNgayLapHoaDon(),
                model.getMaNV(),
                model.getMaKH());
    }

    /**
     * Cập nhật thông tin hóa đơn trong cơ sở dữ liệu. GhiChu không có trong
     * entity nhưng là NULLABLE trong DB nên được bỏ qua.
     *
     * @param model Đối tượng HoaDon chứa thông tin hóa đơn cần cập nhật.
     */
    public void update(HoaDon model) {
        String sql = "UPDATE HoaDon SET TenHD=?, NgayLapHoaDon=?, MaNV=?, MaKH=? WHERE MaHD=?";
        JdbcHelper.update(sql,
                model.getTenHD(),
                model.getNgayLapHoaDon(),
                model.getMaNV(),
                model.getMaKH(),
                model.getMaHD()); // Chuyển String MaHD sang int
    }

    /**
     * Xóa một hóa đơn khỏi cơ sở dữ liệu dựa trên mã hóa đơn.
     *
     * @param maHD Mã hóa đơn cần xóa (String, sẽ được chuyển sang int).
     */
    public void delete(String maHD) {
        String sql = "DELETE FROM HoaDon WHERE MaHD=?";
        JdbcHelper.update(sql, Integer.parseInt(maHD)); // Chuyển String MaHD sang int
    }

    /**
     * Truy vấn tất cả các hóa đơn trong cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng HoaDon.
     */
    public List<HoaDon> selectAll() {
        String sql = "SELECT * FROM HoaDon";
        return selectBySql(sql);
    }

    /**
     * Truy vấn một hóa đơn dựa trên mã hóa đơn.
     *
     * @param maHD Mã hóa đơn cần tìm (String, sẽ được chuyển sang int).
     * @return Đối tượng HoaDon nếu tìm thấy, ngược lại trả về null.
     */
    public HoaDon selectById(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE MaHD=?";
        List<HoaDon> list = selectBySql(sql, Integer.parseInt(maHD)); // Chuyển String MaHD sang int
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * Phương thức nội bộ để thực thi truy vấn SQL và ánh xạ kết quả vào danh
     * sách HoaDon.
     *
     * @param sql Câu lệnh SQL.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Danh sách các đối tượng HoaDon.
     */
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD")); // Đọc int từ DB, chuyển thành String cho entity
                entity.setTenHD(rs.getString("TenHD"));
                entity.setNgayLapHoaDon(rs.getDate("NgayLapHoaDon"));
                entity.setMaNV(rs.getInt("MaNV"));
                entity.setMaKH(rs.getInt("MaKH"));
                // rs.getString("GhiChu") được đọc từ DB nhưng không được gán vào entity do thiếu trường
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi truy vấn dữ liệu HoaDon: " + ex.getMessage(), ex);
        }
        return list;
    }
    // Dán đoạn mã này vào trong file HoaDonDAO.java
// Đặt trong lớp HoaDonDAO.java

    public HoaDon selectLastInserted() {
        String sql = "SELECT TOP 1 * FROM HoaDon ORDER BY MaHD DESC";
        List<HoaDon> list = this.selectBySql(sql);
        return list.isEmpty() ? null : list.get(0);
    }
}
