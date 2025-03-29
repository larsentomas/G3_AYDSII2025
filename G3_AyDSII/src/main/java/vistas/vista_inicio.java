package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JList;

public class vista_inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtf_buscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vista_inicio frame = new vista_inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public vista_inicio() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_conversaciones = new JPanel();
		contentPane.add(panel_conversaciones, BorderLayout.CENTER);
		panel_conversaciones.setLayout(new GridLayout(10, 1, 0, 0));
		
		JList list = new JList();
		panel_conversaciones.add(list);
		
		JPanel panel_botones = new JPanel();
		contentPane.add(panel_botones, BorderLayout.SOUTH);
		panel_botones.setLayout(new GridLayout(2, 3, 0, 0));
		
		JLabel lblNewLabel = new JLabel("");
		panel_botones.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		panel_botones.add(lblNewLabel_1);
		
		JButton btn_agregar_chat = new JButton("+");
		panel_botones.add(btn_agregar_chat);
		
		JButton btn_Agregar_contacto = new JButton("Agregar Contacto");
		panel_botones.add(btn_Agregar_contacto);
		
		JButton btn_ver_Perfil = new JButton("Ver perfil");
		panel_botones.add(btn_ver_Perfil);
		
		JButton btnLoguout = new JButton("Cerrar Sesión");
		panel_botones.add(btnLoguout);
		
		JPanel panel_norte = new JPanel();
		contentPane.add(panel_norte, BorderLayout.NORTH);
		
		txtf_buscar = new JTextField();
		txtf_buscar.setText("a quien buscas?");
		panel_norte.add(txtf_buscar);
		txtf_buscar.setColumns(10);
		
		JButton btnBuscar = new JButton("buscar");
		panel_norte.add(btnBuscar);
	}

}
