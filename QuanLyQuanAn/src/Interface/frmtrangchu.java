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
        JLabel lblWelcome = new JLabel("Chào mừng bạn đến với trang chủ!", JLabel.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblWelcome, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new frmtrangchu().setVisible(true));
    }
}
