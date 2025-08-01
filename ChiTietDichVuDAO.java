package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.ChiTietDichVu;
import poly.net.utils.JdbcHelper;

/**
 * Lớp Data Access Object (DAO) cho bảng ChiTietDichVu.
 * Cung cấp các phương thức để thao tác với dữ liệu chi tiết dịch vụ trong cơ sở dữ liệu.
 */
public class ChiTietDichVuDAO {

    /**
     * Thêm một chi tiết dịch vụ mới vào cơ sở dữ liệu.
     *
     * @param model Đối tượng ChiTietDichVu chứa thông tin chi tiết dịch vụ cần thêm.
     */
    public void insert(ChiTietDichVu model) {
        String sql = "INSERT INTO ChiTietDichVu (MaHD, MaDV, SoLuongBan) VALUES (?, ?, ?)";
        JdbcHelper.update(sql,
                model.getMaHD(),
                model.getMaDV(),
                model.getSoLuongBan());
    }

    /**
     * Cập nhật thông tin chi tiết dịch vụ trong cơ sở dữ liệu.
     *
     * @param model Đối tượng ChiTietDichVu chứa thông tin chi tiết dịch vụ cần cập nhật.
     */
    public void update(ChiTietDichVu model) {
        String sql = "UPDATE ChiTietDichVu SET SoLuongBan=? WHERE MaHD=? AND MaDV=?";
        JdbcHelper.update(sql,
                model.getSoLuongBan(),
                model.getMaHD(),
                model.getMaDV());
    }

    /**
     * Xóa một chi tiết dịch vụ khỏi cơ sở dữ liệu dựa trên MaHD và MaDV.
     *
     * @param maHD Mã hóa đơn.
     * @param maDV Mã dịch vụ.
     */
    public void delete(int maHD, int maDV) {
        String sql = "DELETE FROM ChiTietDichVu WHERE MaHD=? AND MaDV=?";
        JdbcHelper.update(sql, maHD, maDV);
    }

    /**
     * Truy vấn tất cả các chi tiết dịch vụ trong cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng ChiTietDichVu.
     */
    public List<ChiTietDichVu> selectAll() {
        String sql = "SELECT * FROM ChiTietDichVu";
        return selectBySql(sql);
    }

    /**
     * Truy vấn các chi tiết dịch vụ dựa trên mã hóa đơn.
     * Phương thức này sẽ join với bảng DichVu để lấy tên dịch vụ và giá dịch vụ.
     *
     * @param maHD Mã hóa đơn cần tìm.
     * @return Danh sách các đối tượng ChiTietDichVu liên quan đến hóa đơn đó.
     */
    public List<ChiTietDichVu> selectByMaHD(int maHD) {
        String sql = "SELECT ctdv.MaHD, ctdv.MaDV, ctdv.SoLuongBan, dv.TenDV, dv.GiaDV " +
                     "FROM ChiTietDichVu ctdv JOIN DichVu dv ON ctdv.MaDV = dv.MaDV " +
                     "WHERE ctdv.MaHD=?";
        return selectBySql(sql, maHD);
    }

    /**
     * Truy vấn một chi tiết dịch vụ cụ thể dựa trên MaHD và MaDV.
     *
     * @param maHD Mã hóa đơn.
     * @param maDV Mã dịch vụ.
     * @return Đối tượng ChiTietDichVu nếu tìm thấy, ngược lại trả về null.
     */
    public ChiTietDichVu selectByMaHDAndMaDV(int maHD, int maDV) {
        String sql = "SELECT ctdv.MaHD, ctdv.MaDV, ctdv.SoLuongBan, dv.TenDV, dv.GiaDV " +
                     "FROM ChiTietDichVu ctdv JOIN DichVu dv ON ctdv.MaDV = dv.MaDV " +
                     "WHERE ctdv.MaHD=? AND ctdv.MaDV=?";
        List<ChiTietDichVu> list = selectBySql(sql, maHD, maDV);
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * Phương thức nội bộ để thực thi truy vấn SQL và ánh xạ kết quả vào danh sách ChiTietDichVu.
     *
     * @param sql Câu lệnh SQL.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Danh sách các đối tượng ChiTietDichVu.
     */
    protected List<ChiTietDichVu> selectBySql(String sql, Object... args) {
        List<ChiTietDichVu> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietDichVu entity = new ChiTietDichVu();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaDV(rs.getInt("MaDV"));
                entity.setSoLuongBan(rs.getInt("SoLuongBan"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close(); // Đóng kết nối sau khi sử dụng ResultSet
        } catch (SQLException ex) {
            throw new RuntimeException("Lỗi khi truy vấn dữ liệu ChiTietDichVu: " + ex.getMessage(), ex);
        }
        return list;
    }
}
