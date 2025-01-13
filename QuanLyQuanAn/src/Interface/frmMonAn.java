package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class frmMonAn extends JFrame {
    private JPanel contentPane;
    private JTextField txtIdMonAn;
    private JTextField txtTenMon;
    private JTextField txtGia;
    private JTextField txtMoTa;
    private JTable table;
    private JComboBox<String> comboBoxDanhMuc;
    private HashMap<String, String> danhMucMap;

    private Proccess.monan monan = new Proccess.monan(); // Sử dụng lớp Process

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                frmMonAn frame = new frmMonAn();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public frmMonAn() {
        setTitle("Quản lý món ăn");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600); // Chỉnh lại kích thước cho phù hợp với frmNhanVien
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10)); // Thêm khoảng cách viền
        contentPane.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        createUI(); // Tạo giao diện
        monan.loadDanhMuc(comboBoxDanhMuc, danhMucMap); // Load danh mục từ Process
        loadDataToTable(); // Load dữ liệu từ Process
    }

    private void createUI() {
        // Tiêu đề
        JLabel lblMonAn = new JLabel("Quản lý món ăn", SwingConstants.CENTER);
        lblMonAn.setFont(new Font("Arial", Font.BOLD, 26));
        lblMonAn.setForeground(new Color(70, 130, 180)); // Màu xanh dương đậm
        lblMonAn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(lblMonAn);

        JPanel panelInput = new JPanel();
        panelInput.setLayout(new GridLayout(5, 2, 10, 10)); // 5 dòng, 2 cột với khoảng cách 10 pixel
        panelInput.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelInput);

        // Các trường nhập liệu
        txtIdMonAn = createInputField(panelInput, "ID Món ăn:");
        comboBoxDanhMuc = new JComboBox<>();
        danhMucMap = new HashMap<>();
        panelInput.add(new JLabel("Danh mục:"));
        panelInput.add(comboBoxDanhMuc);
        txtTenMon = createInputField(panelInput, "Tên món:");
        txtGia = createInputField(panelInput, "Giá:");
        txtMoTa = createInputField(panelInput, "Mô tả:");

        // Các nút chức năng
        JPanel panelButtons = new JPanel();
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelButtons.setBackground(new Color(240, 248, 255)); // Màu nền nhạt
        contentPane.add(panelButtons);

        addButton(panelButtons, "Thêm", e -> addMonAn());
        addButton(panelButtons, "Sửa", e -> updateMonAn());
        addButton(panelButtons, "Xóa", e -> deleteMonAn());
        addButton(panelButtons, "Thoát", e -> System.exit(0));

        // Bảng hiển thị dữ liệu
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
                    String[] data = monan.getRowData(table, row);
                    txtIdMonAn.setText(data[0]);
                    comboBoxDanhMuc.setSelectedItem(data[1]);
                    txtTenMon.setText(data[2]);
                    txtGia.setText(data[3]);
                    txtMoTa.setText(data[4]);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(750, 300)); // Đặt kích thước cho bảng
        contentPane.add(scrollPane);
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

    private void loadDataToTable() {
        monan.loadData(table);
    }

    private void addMonAn() {
        monan.addMonAn(txtIdMonAn.getText(), txtTenMon.getText(), txtGia.getText(), txtMoTa.getText(),
                danhMucMap.get(comboBoxDanhMuc.getSelectedItem().toString()));
        loadDataToTable();
    }

    private void updateMonAn() {
        monan.updateMonAn(txtIdMonAn.getText(), txtTenMon.getText(), txtGia.getText(), txtMoTa.getText(),
                danhMucMap.get(comboBoxDanhMuc.getSelectedItem().toString()));
        loadDataToTable();
    }

    private void deleteMonAn() {
        monan.deleteMonAn(txtIdMonAn.getText());
        loadDataToTable();
    }
}
