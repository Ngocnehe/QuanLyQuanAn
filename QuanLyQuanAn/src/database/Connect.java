package database;

import java.sql.*;

public class Connect {
    private Connection conn;

    // Kết nối đến cơ sở dữ liệu
    public void connectSQL() {
        try {
            String url = "jdbc:mysql://localhost:3306/qlqa"; // Đảm bảo db_name là đúng
            String user = "root"; // Đảm bảo username là đúng
            String password = ""; // Đảm bảo password là đúng
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password); // Tạo kết nối nếu chưa có
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Trả về kết nối cơ sở dữ liệu
    public Connection getConnection() {
        if (conn == null) {
            connectSQL(); // Kiểm tra và kết nối lại nếu chưa kết nối
        }
        return conn;
    }

    // Phương thức thực thi truy vấn và trả về kết quả
    public ResultSet LoadData(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    // Phương thức thực thi câu lệnh UPDATE (INSERT, UPDATE, DELETE)
    public void UpdateData(String sql) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
    }

    // Đóng kết nối khi không cần thiết
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}