package poly.net.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.TaiKhoan;
import poly.net.utils.JdbcHelper;

/**
 *
 * @author pc
 */
public class TaiKhoanDAO {

    /**
     * Thêm một tài khoản mới vào cơ sở dữ liệu.
     *
     * @param model Đối tượng TaiKhoan chứa thông tin tài khoản cần thêm.
     */
    public void insert(TaiKhoan model) {
        String sql = "INSERT INTO TaiKhoan (TenDangNhap, MatKhau, Quyen, TrangThai) VALUES (?, ?, ?, ?)";
        JdbcHelper.update(sql,
                model.getTenDangNhap(),
                model.getMatKhau(),
                model.getQuyen(),
                model.isTrangThai());
    }

    /**
     * Cập nhật thông tin tài khoản trong cơ sở dữ liệu.
     *
     * @param model Đối tượng TaiKhoan chứa thông tin tài khoản cần cập nhật.
     */
    public void update(TaiKhoan model) {
        String sql = "UPDATE TaiKhoan SET MatKhau=?, Quyen=?, TrangThai=? WHERE TenDangNhap=?";
        JdbcHelper.update(sql,
                model.getMatKhau(),
                model.getQuyen(),
                model.isTrangThai(),
                model.getTenDangNhap());
    }

    /**
     * Xóa một tài khoản khỏi cơ sở dữ liệu dựa trên tên đăng nhập.
     *
     * @param tenDangNhap Tên đăng nhập của tài khoản cần xóa.
     */
    public void delete(String tenDangNhap) {
        String sql = "DELETE FROM TaiKhoan WHERE TenDangNhap=?";
        JdbcHelper.update(sql, tenDangNhap);
    }

    /**
     * Truy vấn tất cả các tài khoản trong cơ sở dữ liệu.
     *
     * @return Danh sách các đối tượng TaiKhoan.
     */
    public List<TaiKhoan> selectAll() {
        String sql = "SELECT * FROM TaiKhoan";
        return selectBySql(sql);
    }

    /**
     * Truy vấn một tài khoản dựa trên tên đăng nhập.
     *
     * @param tenDangNhap Tên đăng nhập cần tìm.
     * @return Đối tượng TaiKhoan nếu tìm thấy, ngược lại trả về null.
     */
    public TaiKhoan selectById(String tenDangNhap) {
        String sql = "SELECT * FROM TaiKhoan WHERE TenDangNhap=?";
        List<TaiKhoan> list = selectBySql(sql, tenDangNhap);
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * Phương thức nội bộ để thực thi truy vấn SQL và ánh xạ kết quả vào danh
     * sách TaiKhoan.
     *
     * @param sql Câu lệnh SQL.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Danh sách các đối tượng TaiKhoan.
     */
    protected List<TaiKhoan> selectBySql(String sql, Object... args) {
        List<TaiKhoan> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                TaiKhoan entity = new TaiKhoan();
                entity.setTenDangNhap(rs.getString("TenDangNhap"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setQuyen(rs.getString("Quyen"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
}