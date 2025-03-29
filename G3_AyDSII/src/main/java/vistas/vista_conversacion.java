package vistas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

public class vista_conversacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtf_mensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					vista_conversacion frame = new vista_conversacion();
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
	public vista_conversacion() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_norte = new JPanel();
		contentPane.add(panel_norte, BorderLayout.NORTH);
		
		txtf_mensaje = new JTextField();
		panel_norte.add(txtf_mensaje);
		txtf_mensaje.setColumns(10);
		
		JButton btn_Enviar = new JButton("Enviar");
		panel_norte.add(btn_Enviar);
		
		JPanel panel_central = new JPanel();
		contentPane.add(panel_central, BorderLayout.CENTER);
		
		JList list = new JList();
		list.setVisibleRowCount(100);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel_central.add(list);
		
		JPanel panel_sur = new JPanel();
		contentPane.add(panel_sur, BorderLayout.SOUTH);
		
		JButton btn_ver_perfil = new JButton("Ver Perfil");
		panel_sur.add(btn_ver_perfil);
		
		JButton btnVolver = new JButton("Volver");
		panel_sur.add(btnVolver);
	}

}
