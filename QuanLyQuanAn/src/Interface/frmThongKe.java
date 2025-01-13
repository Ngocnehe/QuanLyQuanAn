package Interface;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class frmThongKe extends JFrame implements ActionListener {
    private JComboBox<String> cbCriteria;
    private JButton btnGenerate;
    private JTextArea taResult;
    private Proccess.thongke thongKe;

    public frmThongKe() {
        thongKe = new Proccess.thongke(this);

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
        lblTitle.setOpaque(true); // Cho phép thay đổi màu nền
        lblTitle.setBackground(new Color(70, 130, 180)); // Đổi màu nền giống màu của frmNhanVien
        lblTitle.setForeground(Color.WHITE); // Đổi màu chữ thành màu trắng
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
            thongKe.generateReport(criteria);
        }
    }

    public JComboBox<String> getCbCriteria() {
        return cbCriteria;
    }

    public JTextArea getTaResult() {
        return taResult;
    }

    public static void main(String[] args) {
        new frmThongKe().setVisible(true);
    }
}
