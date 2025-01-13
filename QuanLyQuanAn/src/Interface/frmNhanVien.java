package Interface;

import Proccess.nhanvien;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class frmNhanVien extends JFrame {
    private JPanel contentPane;
    private JTextField txtIdNhanVien, txtTenNV, txtSoDienThoai, txtEmail, txtMatKhau, txtCCCD, txtVaiTro;
    private JTable table;
    private nhanvien nhanVien;

    public frmNhanVien() {
        nhanVien = new nhanvien();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPane.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Tiêu đề
        JLabel lblNhanVien = new JLabel("Danh Mục Nhân Viên");
        lblNhanVien.setHorizontalAlignment(SwingConstants.CENTER);
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 26));
        lblNhanVien.setForeground(new Color(70, 130, 180)); // Màu xanh dương đậm
        contentPane.add(lblNhanVien);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(7, 2, 10, 10)); // 7 dòng, 2 cột với khoảng cách 10 pixel
        panelInput.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelInput);

        // Các trường nhập liệu
        txtIdNhanVien = createInputField(panelInput, "ID Nhân viên:");
        txtTenNV = createInputField(panelInput, "Tên NV:");
        txtSoDienThoai = createInputField(panelInput, "Số điện thoại:");
        txtEmail = createInputField(panelInput, "Email:");
        txtMatKhau = createInputField(panelInput, "Mật khẩu:");
        txtCCCD = createInputField(panelInput, "CCCD:");
        txtVaiTro = createInputField(panelInput, "Vai trò:");

        // Các nút chức năng
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButtons.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelButtons);

        addButton(panelButtons, "Thêm", this::themNhanVien);
        addButton(panelButtons, "Sửa", this::suaNhanVien);
        addButton(panelButtons, "Xóa", this::xoaNhanVien);
        addButton(panelButtons, "Thoát", e -> System.exit(0));

        // Bảng hiển thị dữ liệu
        JPanel panelTable = new JPanel();
        contentPane.add(panelTable);
        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(200, 200, 200)); // Màu lưới bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                populateFieldsFromTable();
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 300)); // Đặt kích thước cho bảng
        panelTable.add(scrollPane);

        loadData();
    }

    private JTextField createInputField(JPanel panel, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl);
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(textField);
        return textField;
    }

    private void addButton(JPanel panel, String label, ActionListener action) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180)); // Màu xanh dương đậm
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        panel.add(button);
    }

    private void themNhanVien(ActionEvent e) {
        try {
            nhanVien.themNhanVien(txtIdNhanVien.getText(), txtTenNV.getText(), txtSoDienThoai.getText(),
                    txtEmail.getText(), txtMatKhau.getText(), txtCCCD.getText(), txtVaiTro.getText());
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm nhân viên: " + ex.getMessage());
        }
    }

    private void suaNhanVien(ActionEvent e) {
        try {
            nhanVien.suaNhanVien(txtIdNhanVien.getText(), txtTenNV.getText(), txtSoDienThoai.getText(),
                    txtEmail.getText(), txtMatKhau.getText(), txtCCCD.getText(), txtVaiTro.getText());
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa nhân viên: " + ex.getMessage());
        }
    }

    private void xoaNhanVien(ActionEvent e) {
        try {
            nhanVien.xoaNhanVien(txtIdNhanVien.getText());
            loadData();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa nhân viên: " + ex.getMessage());
        }
    }

    private void loadData() {
        try {
            ResultSet rs = nhanVien.getAllNhanVien();
            table.setModel(buildTableModel(rs));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + ex.getMessage());
        }
    }

    private void populateFieldsFromTable() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtIdNhanVien.setText(table.getValueAt(row, 0).toString());
            txtTenNV.setText(table.getValueAt(row, 1).toString());
            txtSoDienThoai.setText(table.getValueAt(row, 2).toString());
            txtEmail.setText(table.getValueAt(row, 3).toString());
            txtMatKhau.setText(table.getValueAt(row, 4).toString());
            txtCCCD.setText(table.getValueAt(row, 5).toString());
            txtVaiTro.setText(table.getValueAt(row, 6).toString());
        }
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }
            model.addRow(row);
        }
        return model;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frmNhanVien frame = new frmNhanVien();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
