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
import java.util.HashMap;

public class frmMonAn extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtIdMonAn;
    private JTextField txtTenMon;
    private JTextField txtGia;
    private JTextField txtMoTa;
    private JTable table;
    private JComboBox<String> comboBoxDanhMuc;
    private HashMap<String, String> danhMucMap; // Map để lưu tên danh mục và id_danhmuc

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
                    frmMonAn frame = new frmMonAn();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmMonAn() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JLabel lblMonAn = new JLabel("Quản lý món ăn");
        lblMonAn.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblMonAn);

        JPanel panelInput = new JPanel();
        contentPane.add(panelInput);
        panelInput.setLayout(new GridLayout(5, 2));

        JLabel lblIdMonAn = new JLabel("ID Món ăn:");
        panelInput.add(lblIdMonAn);

        txtIdMonAn = new JTextField();
        panelInput.add(txtIdMonAn);
        txtIdMonAn.setColumns(10);

        JLabel lblIdDanhMuc = new JLabel("Danh mục:");
        panelInput.add(lblIdDanhMuc);

        comboBoxDanhMuc = new JComboBox<>();
        panelInput.add(comboBoxDanhMuc);
        danhMucMap = new HashMap<>();
        loadDanhMuc(); // Tải danh sách danh mục vào comboBox

        JLabel lblTenMon = new JLabel("Tên món:");
        panelInput.add(lblTenMon);

        txtTenMon = new JTextField();
        panelInput.add(txtTenMon);
        txtTenMon.setColumns(10);

        JLabel lblGia = new JLabel("Giá:");
        panelInput.add(lblGia);

        txtGia = new JTextField();
        panelInput.add(txtGia);
        txtGia.setColumns(10);

        JLabel lblMoTa = new JLabel("Mô tả:");
        panelInput.add(lblMoTa);

        txtMoTa = new JTextField();
        panelInput.add(txtMoTa);
        txtMoTa.setColumns(10);

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
                String idMonAn = txtIdMonAn.getText();
                String tenDanhMuc = comboBoxDanhMuc.getSelectedItem().toString();
                String idDanhMuc = danhMucMap.get(tenDanhMuc);
                String tenMon = txtTenMon.getText();
                String gia = txtGia.getText();
                String moTa = txtMoTa.getText();

                // Kiểm tra giá trị idDanhMuc
                System.out.println("idDanhMuc: " + idDanhMuc);

                if (!idMonAn.isEmpty() && !idDanhMuc.isEmpty() && !tenMon.isEmpty() && !gia.isEmpty() && !moTa.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("INSERT INTO monan (id_monan, id_danhmuc, tenmon, gia, mota) VALUES (?, ?, ?, ?, ?)");
                        ps.setString(1, idMonAn);
                        ps.setString(2, idDanhMuc);
                        ps.setString(3, tenMon);
                        ps.setString(4, gia);
                        ps.setString(5, moTa);
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
                String idMonAn = txtIdMonAn.getText();
                String tenDanhMuc = comboBoxDanhMuc.getSelectedItem().toString();
                String idDanhMuc = danhMucMap.get(tenDanhMuc);
                String tenMon = txtTenMon.getText();
                String gia = txtGia.getText();
                String moTa = txtMoTa.getText();
                if (!idMonAn.isEmpty() && !idDanhMuc.isEmpty() && !tenMon.isEmpty() && !gia.isEmpty() && !moTa.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("UPDATE monan SET id_danhmuc = ?, tenmon = ?, gia = ?, mota = ? WHERE id_monan = ?");
                        ps.setString(1, idDanhMuc);
                        ps.setString(2, tenMon);
                        ps.setString(3, gia);
                        ps.setString(4, moTa);
                        ps.setString(5, idMonAn);
                        ps.executeUpdate();
                        conn.close();
                        loadData(); // Tải lại dữ liệu vào bảng sau khi sửa
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật món ăn.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn để sửa.");
                }
            }
        });

        // Sự kiện Button Xóa
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idMonAn = txtIdMonAn.getText();
                if (!idMonAn.isEmpty()) {
                    try {
                        Connection conn = getConnection();
                        PreparedStatement ps = conn.prepareStatement("DELETE FROM monan WHERE id_monan = ?");
                        ps.setString(1, idMonAn);
                        ps.executeUpdate();
                        conn.close();
                        loadData(); // Tải lại dữ liệu vào bảng sau khi xóa
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn để xóa.");
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
                    String idMonAn = table.getValueAt(row, 0).toString(); // Cột 0 là ID Món ăn
                    String idDanhMuc = table.getValueAt(row, 1).toString(); // Cột 1 là ID Danh mục
                    String tenMon = table.getValueAt(row, 2).toString(); // Cột 2 là Tên món
                    String gia = table.getValueAt(row, 3).toString(); // Cột 3 là Giá
                    String moTa = table.getValueAt(row, 4).toString(); // Cột 4 là Mô tả

                    // Điền dữ liệu vào các text field
                    txtIdMonAn.setText(idMonAn);
                    comboBoxDanhMuc.setSelectedItem(getTenDanhMuc(idDanhMuc)); // Thiết lập giá trị của comboBox
                    txtTenMon.setText(tenMon);
                    txtGia.setText(gia);
                    txtMoTa.setText(moTa);
                }
            }
        });
    }

    private void loadData() {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM monan");
            ResultSet rs = ps.executeQuery();
            table.setModel(buildTableModel(rs)); // Hiển thị dữ liệu vào bảng
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDanhMuc() {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id_danhmuc, tendanhmuc FROM danhmuc");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String tenDanhMuc = rs.getString("tendanhmuc");
                String idDanhMuc = rs.getString("id_danhmuc");
                comboBoxDanhMuc.addItem(tenDanhMuc); // Chỉ thêm tên danh mục vào comboBox
                danhMucMap.put(tenDanhMuc, idDanhMuc); // Lưu id_danhmuc trong map
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private String getTenDanhMuc(String idDanhMuc) {
        try {
            for (String tendanhmuc : danhMucMap.keySet()) {
                if (danhMucMap.get(tendanhmuc).equals(idDanhMuc)) {
                    return tendanhmuc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
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
