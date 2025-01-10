package Interface;

import javax.swing.*;
import java.awt.*;

public class frmtrangchu extends JFrame {

    public frmtrangchu() {
        setTitle("Trang Chủ");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        buildGUI();
    }

    private void buildGUI() {
        // Tạo MenuBar
        JMenuBar menuBar = new JMenuBar();

        // Tạo JMenu Quản lý
        JMenu quanLyMenu = new JMenu("Quản lý");

        // Tạo các JMenuItem
        JMenuItem quanLyMonItem = new JMenuItem("Quản lý món");
        JMenuItem danhMucItem = new JMenuItem("Danh mục");
        JMenuItem nhanVienItem = new JMenuItem("Nhân viên");
        //JMenuItem banItem = new JMenuItem("Bàn");
        //JMenuItem datBanItem = new JMenuItem("Đặt bàn");
        JMenuItem doanhThuItem = new JMenuItem("Doanh thu");

        // Thêm hành động cho các JMenuItem
        quanLyMonItem.addActionListener(e -> showDialog(new frmMonAn()));
        danhMucItem.addActionListener(e -> showDialog(new frmDanhmuc()));
        nhanVienItem.addActionListener(e -> showDialog(new frmNhanVien()));
        //banItem.addActionListener(e -> showDialog(new frmBan()));
        //datBanItem.addActionListener(e -> showDialog(new frmDatBan()));
        doanhThuItem.addActionListener(e -> showDialog(new ThongKeDoanhThu()));

        // Thêm JMenuItem vào JMenu
        quanLyMenu.add(quanLyMonItem);
        quanLyMenu.add(danhMucItem);
        quanLyMenu.add(nhanVienItem);
        //quanLyMenu.add(banItem);
        //quanLyMenu.add(datBanItem);
        quanLyMenu.add(doanhThuItem);

        // Thêm JMenu vào MenuBar
        menuBar.add(quanLyMenu);

        // Thiết lập MenuBar cho JFrame
        setJMenuBar(menuBar);

        // Label chào mừng
        JLabel lblWelcome = new JLabel("Chào mừng bạn đến với trang chủ!", JLabel.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblWelcome, BorderLayout.CENTER);
    }

    private void showDialog(JFrame form) {
        // Thiết lập vị trí trung tâm và hiển thị form
        form.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        form.setLocationRelativeTo(this);
        form.setVisible(true);

        // Khi form con đóng, hiển thị lại form chính
        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                frmtrangchu.this.setVisible(true);
            }
        });

        // Ẩn form chính khi form con hiển thị
        this.setVisible(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frmtrangchu().setVisible(true));
    }
}
