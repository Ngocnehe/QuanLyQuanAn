package Interface;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class frmNhanVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtIdNhanVien;
    private JTextField txtTenNV;
    private JTextField txtSoDienThoai;
    private JTextField txtEmail;
    private JTextField txtMatKhau;
    private JTextField txtCCCD;
    private JTextField txtVaiTro;
    private JTable table;

    // Kết nối cơ sở dữ liệu MySQL
    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/qlqa";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmNhanVien frame = new frmNhanVien();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmNhanVien() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JLabel lblNhanVien = new JLabel("Danh mục nhân viên");
        lblNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNhanVien);

        JPanel panelInput = new JPanel();
        contentPane.add(panelInput);
        panelInput.setLayout(new GridLayout(7, 2));

        JLabel lblIdNhanVien = new JLabel("ID Nhân viên:");
        panelInput.add(lblIdNhanVien);

        txtIdNhanVien = new JTextField();
        panelInput.add(txtIdNhanVien);
        txtIdNhanVien.setColumns(10);

        JLabel lblTenNV = new JLabel("Tên NV:");
        panelInput.add(lblTenNV);

        txtTenNV = new JTextField();
        panelInput.add(txtTenNV);
        txtTenNV.setColumns(10);

        JLabel lblSoDienThoai = new JLabel("Số điện thoại:");
        panelInput.add(lblSoDienThoai);

        txtSoDienThoai = new JTextField();
        panelInput.add(txtSoDienThoai);
        txtSoDienThoai.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        panelInput.add(lblEmail);

        txtEmail = new JTextField();
        panelInput.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        panelInput.add(lblMatKhau);

        txtMatKhau = new JTextField();
        panelInput.add(txtMatKhau);
        txtMatKhau.setColumns(10);

        JLabel lblCCCD = new JLabel("CCCD:");
        panelInput.add(lblCCCD);

        txtCCCD = new JTextField();
        panelInput.add(txtCCCD);
        txtCCCD.setColumns(10);

        JLabel lblVaiTro = new JLabel("Vai trò:");
        panelInput.add(lblVaiTro);

        txtVaiTro = new JTextField();
        panelInput.add(txtVaiTro);
        txtVaiTro.setColumns(10);

        JPanel panelButtons = new JPanel();
        contentPane.add(panelButtons);

        JButton btnThem = new JButton("Thêm");
        panelButtons.add(btnThem);

        JButton btnSua = new JButton("Sửa");
        panelButtons.add(btnSua);

        JButton btnXoa = new JButton("Xóa");
        panelButtons.add(btnXoa);

        JButton btnLuu = new JButton("Lưu");
        panelButtons.add(btnLuu);

        JButton btnThoat = new JButton("Thoát");
        panelButtons.add(btnThoat);

        JPanel panelTable = new JPanel();
        contentPane.add(panelTable);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        panelTable.add(scrollPane);

        // Sự kiện Button Thêm
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idNhanVien = txtIdNhanVien.getText();
                String tenNV = txtTenNV.getText();
                String soDienThoai = txtSoDienThoai.getText();
                String email = txtEmail.getText();
                String matKhau = txtMatKhau.getText();
                String cccd = txtCCCD.getText();
                String vaiTro = txtVaiTro.getText();
                if (!idNhanVien.isEmpty() && !tenNV.isEmpty() && !soDienThoai.isEmpty() && !email.isEmpty() && !matKhau.isEmpty() && !cccd.isEmpty() && !vaiTro.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO nhanvien (id_nhanvien, tennv, sodienthoai, email, matkhau, cccd, vaitro) VALUES (?, ?, ?, ?, ?, ?, ?)");
                        ps.setString(1, idNhanVien);
                        ps.setString(2, tenNV);
                        ps.setString(3, soDienThoai);
                        ps.setString(4, email);
                        ps.setString(5, matKhau);
                        ps.setString(6, cccd);
                        ps.setString(7, vaiTro);
                        ps.executeUpdate();
                        conn.close();
                        loadData(); // Tải lại dữ liệu vào bảng
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Các trường không được để trống.");
                }
            }
        });

        // Sự kiện Button Sửa
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idNhanVien = txtIdNhanVien.getText();
                String tenNV = txtTenNV.getText();
                String soDienThoai = txtSoDienThoai.getText();
                String email = txtEmail.getText();
                String matKhau = txtMatKhau.getText();
                String cccd = txtCCCD.getText();
                String vaiTro = txtVaiTro.getText();
                if (!idNhanVien.isEmpty() && !tenNV.isEmpty() && !soDienThoai.isEmpty() && !email.isEmpty() && !matKhau.isEmpty() && !cccd.isEmpty() && !vaiTro.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("UPDATE nhanvien SET tennv = ?, sodienthoai = ?, email = ?, matkhau = ?, cccd = ?, vaitro = ? WHERE id_nhanvien = ?");
                        ps.setString(1, tenNV);
                        ps.setString(2, soDienThoai);
                        ps.setString(3, email);
                        ps.setString(4, matKhau);
                        ps.setString(5, cccd);
                        ps.setString(6, vaiTro);
                        ps.setString(7, idNhanVien);
                        ps.executeUpdate();
                        conn.close();
                        loadData(); // Tải lại dữ liệu vào bảng sau khi sửa
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật nhân viên.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để sửa.");
                }
            }
        });

        // Sự kiện Button Xóa
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idNhanVien = txtIdNhanVien.getText();
                if (!idNhanVien.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("DELETE FROM nhanvien WHERE id_nhanvien = ?");
                        ps.setString(1, idNhanVien);
                        ps.executeUpdate();
                        conn.close();
                        loadData(); // Tải lại dữ liệu vào bảng sau khi xóa
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn nhân viên để xóa.");
                }
            }
        });

        // Sự kiện Button Lưu
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Chức năng lưu có thể được thêm vào đây nếu cần, ví dụ lưu vào file hoặc cơ sở dữ liệu
                JOptionPane.showMessageDialog(null, "Dữ liệu đã được lưu.");
            }
        });

        // Sự kiện Button Thoát
        btnThoat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Đóng ứng dụng khi nhấn "Thoát
                System.exit(0); // Đóng ứng dụng khi nhấn "Thoát"
            }
        });

        // Tải dữ liệu từ cơ sở dữ liệu
        loadData();

        // Sự kiện chọn dòng trong bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // Lấy dữ liệu từ dòng được chọn
                    String idNhanVien = table.getValueAt(row, 0).toString(); // Cột 0 là ID Nhân viên
                    String tenNV = table.getValueAt(row, 1).toString(); // Cột 1 là Tên NV
                    String soDienThoai = table.getValueAt(row, 2).toString(); // Cột 2 là Số điện thoại
                    String email = table.getValueAt(row, 3).toString(); // Cột 3 là Email
                    String matKhau = table.getValueAt(row, 4).toString(); // Cột 4 là Mật khẩu
                    String cccd = table.getValueAt(row, 5).toString(); // Cột 5 là CCCD
                    String vaiTro = table.getValueAt(row, 6).toString(); // Cột 6 là Vai trò

                    // Điền dữ liệu vào các text field
                    txtIdNhanVien.setText(idNhanVien);
                    txtTenNV.setText(tenNV);
                    txtSoDienThoai.setText(soDienThoai);
                    txtEmail.setText(email);
                    txtMatKhau.setText(matKhau);
                    txtCCCD.setText(cccd);
                    txtVaiTro.setText(vaiTro);
                }
            }
        });
    }

    private void loadData() {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM nhanvien");
            ResultSet rs = ps.executeQuery();
            table.setModel(buildTableModel(rs)); // Hiển thị dữ liệu vào bảng
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Phương thức xây dựng mô hình bảng từ ResultSet
    public static javax.swing.table.DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        java.sql.ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        java.util.Vector<String> columnNames = new java.util.Vector<String>();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        java.util.Vector<java.util.Vector<Object>> data = new java.util.Vector<java.util.Vector<Object>>();
        while (rs.next()) {
            java.util.Vector<Object> row = new java.util.Vector<Object>();
            for (int column = 1; column <= columnCount; column++) {
                row.add(rs.getObject(column));
            }
            data.add(row);
        }
        return new javax.swing.table.DefaultTableModel(data, columnNames);
    }
}
