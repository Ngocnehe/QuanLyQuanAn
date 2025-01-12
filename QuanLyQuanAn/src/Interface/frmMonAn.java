package Interface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		createUI(); // Tạo giao diện
		getComponentListeners(); // Thêm các sự kiện
		monan.loadDanhMuc(comboBoxDanhMuc, danhMucMap); // Load danh mục từ Process
		loadDataToTable(); // Load dữ liệu từ Process
	}

	private void createUI() {
		// Giao diện chính
		JLabel lblMonAn = new JLabel("Quản lý món ăn", SwingConstants.CENTER);
		contentPane.add(lblMonAn);

		JPanel panelInput = new JPanel(new GridLayout(5, 2));
		contentPane.add(panelInput);

		panelInput.add(new JLabel("ID Món ăn:"));
		txtIdMonAn = new JTextField();
		panelInput.add(txtIdMonAn);

		panelInput.add(new JLabel("Danh mục:"));
		comboBoxDanhMuc = new JComboBox<>();
		danhMucMap = new HashMap<>();
		panelInput.add(comboBoxDanhMuc);

		panelInput.add(new JLabel("Tên món:"));
		txtTenMon = new JTextField();
		panelInput.add(txtTenMon);

		panelInput.add(new JLabel("Giá:"));
		txtGia = new JTextField();
		panelInput.add(txtGia);

		panelInput.add(new JLabel("Mô tả:"));
		txtMoTa = new JTextField();
		panelInput.add(txtMoTa);

		JPanel panelButtons = new JPanel();
		contentPane.add(panelButtons);

		JButton btnThem = new JButton("Thêm");
		panelButtons.add(btnThem);

		JButton btnSua = new JButton("Sửa");
		panelButtons.add(btnSua);

		JButton btnXoa = new JButton("Xóa");
		panelButtons.add(btnXoa);

		JButton btnThoat = new JButton("Thoát");
		panelButtons.add(btnThoat);

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane);

		btnThem.addActionListener(e -> addMonAn());
		btnSua.addActionListener(e -> updateMonAn());
		btnXoa.addActionListener(e -> deleteMonAn());
		btnThoat.addActionListener(e -> System.exit(0));
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
