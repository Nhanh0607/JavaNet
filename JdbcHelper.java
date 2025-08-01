package poly.net.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcHelper {
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost:1433;databaseName=tamhoangnet;encrypt=false";
    private static String user = "sa";
    private static String pass = "123456";

    /*
     * Nạp driver
     */
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            // Ném RuntimeException với thông báo rõ ràng hơn
            throw new RuntimeException("Không thể nạp Driver JDBC: " + ex.getMessage(), ex);
        }
    }

    /**
     * Mở một kết nối mới đến cơ sở dữ liệu.
     * @return Đối tượng Connection.
     * @throws SQLException nếu có lỗi kết nối cơ sở dữ liệu.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, user, pass);
    }

    /**
     * Xây dựng PreparedStatement.
     * Phương thức này nhận một Connection đã có để tạo PreparedStatement.
     *
     * @param con Kết nối cơ sở dữ liệu.
     * @param sql Câu lệnh SQL có thể chứa tham số (dấu '?').
     * @param args Danh sách các giá trị được cung cấp cho các tham số.
     * @return PreparedStatement đã được tạo và thiết lập tham số.
     * @throws SQLException lỗi sai cú pháp SQL hoặc thiết lập tham số.
     */
    public static PreparedStatement getStmt(Connection con, String sql, Object... args) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]); // JDBC parameters are 1-indexed
        }
        return stmt;
    }

    /**
     * Thực hiện câu lệnh SQL cập nhật (INSERT, UPDATE, DELETE) hoặc thủ tục lưu không trả về kết quả.
     * Phương thức này sẽ tự động mở và đóng kết nối.
     *
     * @param sql Câu lệnh SQL chưa có thể chứa tham số. Nó có thể là một lời gọi thủ tục lưu.
     * @param args Danh sách các giá trị được cung cấp cho các tham số trong câu lệnh sql.
     * @return Số lượng bản ghi bị ảnh hưởng.
     */
    public static int update(String sql, Object... args) {
        try (Connection con = getConnection(); // Mở kết nối và đảm bảo nó được đóng tự động
             PreparedStatement stmt = getStmt(con, sql, args)) { // Tạo statement từ kết nối đã mở
            return stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi thực thi cập nhật: " + e.getMessage());
            e.printStackTrace(); // In stack trace để gỡ lỗi chi tiết
            throw new RuntimeException("Lỗi cập nhật dữ liệu: " + e.getMessage(), e); // Ném RuntimeException để thông báo lỗi
        }
    }

    /**
     * Thực hiện câu lệnh SQL truy vấn (SELECT) hoặc thủ tục lưu truy vấn dữ liệu.
     * Phương thức này trả về ResultSet; trách nhiệm đóng ResultSet, PreparedStatement và Connection thuộc về người gọi.
     *
     * @param sql Câu lệnh SQL có thể chứa tham số.
     * @param args Danh sách các giá trị được cung cấp cho các tham số.
     * @return ResultSet chứa kết quả truy vấn.
     * @throws SQLException nếu có lỗi trong quá trình truy vấn.
     */
    public static ResultSet query(String sql, Object... args) throws SQLException {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(); // Mở kết nối
            stmt = getStmt(con, sql, args); // Tạo statement từ kết nối
            return stmt.executeQuery(); // Trả về ResultSet
        } catch (SQLException e) {
            // Đóng tài nguyên trong trường hợp có lỗi trước khi trả về ResultSet
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) { /* Bỏ qua lỗi khi đóng */ }
            try {
                if (con != null) con.close();
            } catch (SQLException ex) { /* Bỏ qua lỗi khi đóng */ }
            System.err.println("Lỗi SQL khi thực thi truy vấn: " + e.getMessage());
            e.printStackTrace();
            throw e; // Ném lại ngoại lệ để lớp gọi (ví dụ selectBySql) có thể xử lý
        }
    }

    /**
     * Thực hiện câu lệnh SQL truy vấn và trả về giá trị đơn lẻ từ cột đầu tiên.
     * Phương thức này sẽ tự động mở và đóng tất cả tài nguyên JDBC.
     *
     * @param sql Câu lệnh SQL truy vấn.
     * @param args Các đối số cho câu lệnh SQL.
     * @return Giá trị Object từ cột đầu tiên của hàng đầu tiên, hoặc null nếu không có kết quả.
     */
    public static Object value(String sql, Object... args) {
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            con = getConnection(); // Mở kết nối
            stmt = getStmt(con, sql, args); // Tạo statement
            rs = stmt.executeQuery(); // Thực thi truy vấn
            if (rs.next()) {
                return rs.getObject(1); // SỬA LỖI: Chỉ mục cột trong JDBC ResultSet bắt đầu từ 1, không phải 0.
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL khi lấy giá trị đơn lẻ: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Lỗi truy vấn giá trị: " + e.getMessage(), e);
        } finally {
            // Đảm bảo đóng tất cả tài nguyên
            try {
                if (rs != null) rs.close();
            } catch (SQLException ex) { System.err.println("Lỗi khi đóng ResultSet: " + ex.getMessage()); }
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) { System.err.println("Lỗi khi đóng PreparedStatement: " + ex.getMessage()); }
            try {
                if (con != null) con.close();
            } catch (SQLException ex) { System.err.println("Lỗi khi đóng Connection: " + ex.getMessage()); }
        }
    }
}
