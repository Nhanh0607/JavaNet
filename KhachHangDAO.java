package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.KhachHang;
import poly.net.utils.JdbcHelper;

/**
 *
 * @author pc
 */
public class KhachHangDAO {

    /**
     * Thêm một khách hàng mới vào cơ sở dữ liệu.
     * MaKH là IDENTITY nên không cần truyền vào khi INSERT.
     *
     * @param model Đối tượng KhachHang chứa thông tin khách hàng cần thêm.
     */
    public void insert(KhachHang model) {
        String sql = "INSERT INTO KhachHang (TenKH, GioiTinh, SDT, GhiChu, TenDangNhap) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.update(sql,
                model.getTenKH(),
                model.getGioiTinh(),
                String.valueOf(model.getSDT()), // Chuyển int SDT sang String để khớp với DB
                model.getGhiChu(),
                model.getTenDangNhap());
    }

    /**
     * Cập nhật thông tin khách hàng trong cơ sở dữ liệu.
     *
     * @param model Đối tượng KhachHang chứa thông tin khách hàng cần cập nhật.
     */
    public void update(KhachHang model) {
        String sql = "UPDATE KhachHang SET TenKH=?, GioiTinh=?, SDT=?, GhiChu=?, TenDangNhap=? WHERE MaKH=?";
        JdbcHelper.update(sql,
                model.getTenKH(),
                model.getGioiTinh(),
                String.valueOf(model.getSDT()), // Chuyển int SDT sang String để khớp với DB
                model.getGhiChu(),
                model.getTenDangNhap(),
                Integer.parseInt(model.getMaKH())); // Chuyển String MaKH sang int để khớp với DB
    }

    /**
     * Xóa một khách hàng khỏi cơ sở dữ liệu dựa trên mã khách hàng.
     *
     * @param maKH Mã khách hàng cần xóa (String, sẽ được chuyển sang int).
     */
    public void delete(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        JdbcHelper.update(sql, Integer.parseInt(maKH)); // Chuyển String MaKH sang int để khớp với DB
    }

    /**
     * Truy vấn tất cả các khách hàng trong cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng KhachHang.
     */
    public List<KhachHang> selectAll() {
        String sql = "SELECT * FROM KhachHang";
        return selectBySql(sql);
    }

    /**
     * Truy vấn một khách hàng dựa trên mã khách hàng.
     *
     * @param maKH Mã khách hàng cần tìm (String, sẽ được chuyển sang int).
     * @return Đối tượng KhachHang nếu tìm thấy, ngược lại trả về null.
     */
    public KhachHang selectById(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = selectBySql(sql, Integer.parseInt(maKH)); // Chuyển String MaKH sang int để khớp với DB
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * Phương thức nội bộ để thực thi truy vấn SQL và ánh xạ kết quả vào danh
     * sách KhachHang.
     *
     * @param sql Câu lệnh SQL.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Danh sách các đối tượng KhachHang.
     */
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(String.valueOf(rs.getInt("MaKH"))); // Đọc int từ DB, chuyển thành String cho entity
                entity.setTenKH(rs.getString("TenKH"));
                entity.setGioiTinh(rs.getString("GioiTinh"));
                entity.setSDT(Integer.parseInt(rs.getString("SDT"))); // Đọc String từ DB, chuyển thành int cho entity
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setTenDangNhap(rs.getString("TenDangNhap"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi truy vấn dữ liệu KhachHang: " + ex.getMessage(), ex);
        }
        return list;
    }
}