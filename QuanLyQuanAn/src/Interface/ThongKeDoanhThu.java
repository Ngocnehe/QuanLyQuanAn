package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class ThongKeDoanhThu extends JFrame implements ActionListener {
    private JComboBox<String> cbCriteria;
    private JButton btnGenerate;
    private JTextArea taResult;

    public ThongKeDoanhThu() {
        setTitle("Thống kê doanh thu");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildGUI();
    }

    private void buildGUI() {
        setLayout(new BorderLayout(10, 10)); // Thêm khoảng cách giữa các phần

        JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCriteria = new JLabel("Tiêu chí thống kê:");
        lblCriteria.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblCriteria, gbc);

        cbCriteria = new JComboBox<>(new String[]{"Theo ngày", "Theo tháng", "Theo nhân viên"});
        cbCriteria.setFont(new Font("Arial", Font.PLAIN, 14));
        cbCriteria.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        panel.add(cbCriteria, gbc);

        btnGenerate = new JButton("Tạo báo cáo");
        btnGenerate.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerate.setBackground(new Color(100, 149, 237)); // Màu nền nút
        btnGenerate.setForeground(Color.WHITE); // Màu chữ nút
        btnGenerate.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(btnGenerate, gbc);

        btnGenerate.addActionListener(this);

        add(panel, BorderLayout.CENTER);

        taResult = new JTextArea();
        taResult.setFont(new Font("Arial", Font.PLAIN, 14));
        taResult.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(taResult);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnGenerate) {
            String criteria = (String) cbCriteria.getSelectedItem();
            generateReport(criteria);
        }
    }

    private void generateReport(String criteria) {
        String query = "";
        if ("Theo ngày".equals(criteria)) {
            query = "SELECT DATE(ngaytao) AS date, SUM(tongtien) AS total_revenue FROM donhang GROUP BY DATE(ngaytao)";
        } else if ("Theo tháng".equals(criteria)) {
            query = "SELECT YEAR(ngaytao) AS year, MONTH(ngaytao) AS month, SUM(tongtien) AS total_revenue FROM donhang GROUP BY YEAR(ngaytao), MONTH(ngaytao)";
        } else if ("Theo nhân viên".equals(criteria)) {
            query = "SELECT nv.tennv AS employee, SUM(dh.tongtien) AS total_revenue FROM donhang dh JOIN nhanvien nv ON dh.id_nhanvien = nv.id_nhanvien GROUP BY nv.tennv";
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quanlyquanan", "root", "");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            taResult.setText("");
            while (rs.next()) {
                if ("Theo ngày".equals(criteria)) {
                    taResult.append("Ngày: " + rs.getString("date") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                } else if ("Theo tháng".equals(criteria)) {
                    taResult.append("Năm: " + rs.getInt("year") + " Tháng: " + rs.getInt("month") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                } else if ("Theo nhân viên".equals(criteria)) {
                    taResult.append("Nhân viên: " + rs.getString("employee") + " - Doanh thu: " + rs.getDouble("total_revenue") + "\n");
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn cơ sở dữ liệu.");
        }
    }

    public static void main(String[] args) {
        new ThongKeDoanhThu().setVisible(true);
    }
}
