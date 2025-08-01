package poly.net.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import poly.net.entity.MayTinh;
import poly.net.utils.JdbcHelper; // Import JdbcHelper

/**
 *
 * @author ASUS
 */
public class MayTinhDAO {

    public void insert(MayTinh model) {
        String sql = "INSERT INTO MayTinh (LoaiMay, CauHinh, GiaThueTheoGio, TenMay, TinhTrang) VALUES (?, ?, ?, ?, ?)";
        JdbcHelper.update(sql,
                model.getLoaiMay(),
                model.getCauHinh(),
                model.getGiaThueTheoGio(),
                model.getTenMay(),
                model.getTinhTrang());
    }

    public void update(MayTinh model) {
        String sql = "UPDATE MayTinh SET LoaiMay=?, CauHinh=?, GiaThueTheoGio=?, TenMay=?, TinhTrang=? WHERE MaMay=?";
        JdbcHelper.update(sql,
                model.getLoaiMay(),
                model.getCauHinh(),
                model.getGiaThueTheoGio(),
                model.getTenMay(),
                model.getTinhTrang(),
                model.getMaMay());
    }

    public void delete(Integer MaMay) {
        String sql = "DELETE FROM MayTinh WHERE MaMay=?";
        JdbcHelper.update(sql, MaMay);
    }

    public List<MayTinh> selectAll() {
        String sql = "SELECT * FROM MayTinh";
        return selectBySql(sql);
    }

    public MayTinh selectById(Integer MaMay) {
        String sql = "SELECT * FROM MayTinh WHERE MaMay=?";
        List<MayTinh> list = selectBySql(sql, MaMay);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    // Phương thức này có thể tìm kiếm theo trạng thái nếu cần
    public List<MayTinh> selectByStatus(String tinhTrang) {
        String sql = "SELECT * FROM MayTinh WHERE TinhTrang = ?";
        return selectBySql(sql, tinhTrang);
    }

    protected List<MayTinh> selectBySql(String sql, Object... args) {
        List<MayTinh> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                MayTinh entity = new MayTinh();
                entity.setMaMay(rs.getInt("MaMay"));
                entity.setLoaiMay(rs.getString("LoaiMay"));
                entity.setCauHinh(rs.getString("CauHinh"));
                entity.setGiaThueTheoGio(rs.getInt("GiaThueTheoGio"));
                entity.setTenMay(rs.getString("TenMay"));
                entity.setTinhTrang(rs.getString("TinhTrang"));
                list.add(entity);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}