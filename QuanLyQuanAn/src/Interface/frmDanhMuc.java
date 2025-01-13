package Interface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import Proccess.danhmuc;

public class frmDanhMuc extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtIdDanhMuc;
    private JTextField txtTenDanhMuc;
    private JTable table;
    private danhmuc danhMuc = new danhmuc(); // Khởi tạo đối tượng DanhMuc

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frmDanhMuc frame = new frmDanhMuc();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public frmDanhMuc() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450); // Tăng kích thước cửa sổ
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Thêm khoảng cách viền
        contentPane.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JLabel lblDanhMuc = new JLabel("Danh mục");
        lblDanhMuc.setHorizontalAlignment(SwingConstants.CENTER);
        lblDanhMuc.setFont(new Font("Arial", Font.BOLD, 26));
        lblDanhMuc.setForeground(new Color(70, 130, 180)); // Màu xanh dương đậm
        lblDanhMuc.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(lblDanhMuc);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(2, 2, 10, 10)); // 2 dòng, 2 cột với khoảng cách 10 pixel
        panelInput.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelInput);

        JLabel lblIdDanhMuc = new JLabel("ID Danh Mục:");
        lblIdDanhMuc.setFont(new Font("Arial", Font.BOLD, 14));
        panelInput.add(lblIdDanhMuc);

        txtIdDanhMuc = new JTextField();
        txtIdDanhMuc.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInput.add(txtIdDanhMuc);
        txtIdDanhMuc.setColumns(10);

        JLabel lblTenDanhMuc = new JLabel("Tên Danh Mục:");
        lblTenDanhMuc.setFont(new Font("Arial", Font.BOLD, 14));
        panelInput.add(lblTenDanhMuc);

        txtTenDanhMuc = new JTextField();
        txtTenDanhMuc.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInput.add(txtTenDanhMuc);
        txtTenDanhMuc.setColumns(10);

        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButtons.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelButtons);

        addButton(panelButtons, "Thêm", e -> addDanhMuc());
        addButton(panelButtons, "Sửa", e -> updateDanhMuc());
        addButton(panelButtons, "Xóa", e -> deleteDanhMuc());
        addButton(panelButtons, "Thoát", e -> System.exit(0));

        JPanel panelTable = new JPanel();
        panelTable.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelTable);

        table = new JTable();
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(200, 200, 200)); // Màu lưới bảng
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtIdDanhMuc.setText(table.getValueAt(row, 0).toString());
                    txtTenDanhMuc.setText(table.getValueAt(row, 1).toString());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(550, 300)); // Đặt kích thước cho bảng
        panelTable.add(scrollPane);

        loadData();
    }

    private void addButton(JPanel panel, String label, ActionListener action) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180)); // Màu xanh dương đậm
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        panel.add(button);
    }

    private void addDanhMuc() {
        String idDanhMuc = txtIdDanhMuc.getText();
        String tenDanhMuc = txtTenDanhMuc.getText();
        if (!idDanhMuc.isEmpty() && !tenDanhMuc.isEmpty()) {
            try {
                danhMuc.themDanhMuc(idDanhMuc, tenDanhMuc); // Thêm danh mục vào cơ sở dữ liệu
                loadData(); // Tải lại dữ liệu vào bảng
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "ID danh mục và tên danh mục không được để trống.");
        }
    }

    private void updateDanhMuc() {
        String idDanhMuc = txtIdDanhMuc.getText();
        String tenDanhMuc = txtTenDanhMuc.getText();
        if (!idDanhMuc.isEmpty() && !tenDanhMuc.isEmpty()) {
            try {
                danhMuc.suaDanhMuc(idDanhMuc, tenDanhMuc); // Sửa danh mục
                loadData(); // Tải lại dữ liệu vào bảng sau khi sửa
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật danh mục.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn danh mục để sửa.");
        }
    }

    private void deleteDanhMuc() {
        String idDanhMuc = txtIdDanhMuc.getText();
        if (!idDanhMuc.isEmpty()) {
            try {
                danhMuc.xoaDanhMuc(idDanhMuc); // Xóa danh mục
                loadData(); // Tải lại dữ liệu vào bảng sau khi xóa
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn danh mục để xóa.");
        }
    }

    private void loadData() {
        try {
            ResultSet rs = danhMuc.getAllDanhMuc(); // Lấy tất cả danh mục từ cơ sở dữ liệu
            table.setModel(buildTableModel(rs)); // Hiển thị dữ liệu vào bảng
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
