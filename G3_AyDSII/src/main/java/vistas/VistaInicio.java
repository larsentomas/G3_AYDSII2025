package vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import java.awt.List;
import java.awt.Color;
import java.awt.TextField;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaInicio extends JFrame implements IVistaInicio {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtf_buscar;
    private JButton btnBuscar;
    private JButton btnEnviar;
    private JButton btnAgregarChat;
    private JButton btnAgregarContacto;
    private JButton btnLoguout;
    private TextField txtf_mensaje;
    private List lista_chat;
    private JList listaConversaciones;

    public VistaInicio() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel_central = new JPanel();
        contentPane.add(panel_central, BorderLayout.CENTER);
        panel_central.setLayout(new BorderLayout(0, 0));

        JPanel panel_Conversaciones = new JPanel();
        panel_central.add(panel_Conversaciones);
        panel_Conversaciones.setLayout(new BorderLayout(0, 0));

        this.listaConversaciones = new JList();
        this.listaConversaciones.setVisibleRowCount(6);
        panel_Conversaciones.add(this.listaConversaciones);

        JPanel panel_chat = new JPanel();
        panel_central.add(panel_chat, BorderLayout.EAST);
        panel_chat.setLayout(new BorderLayout(0, 0));

        JPanel chat = new JPanel();
        panel_chat.add(chat, BorderLayout.CENTER);
        chat.setLayout(new BorderLayout(0, 0));

        this.lista_chat = new List();
        this.lista_chat.setBackground(new Color(255, 255, 255));
        chat.add(this.lista_chat);

        JPanel acciones = new JPanel();
        panel_chat.add(acciones, BorderLayout.SOUTH);

        this.txtf_mensaje = new TextField();
        this.txtf_mensaje.setColumns(30);
        acciones.add(this.txtf_mensaje);

        this.btnEnviar = new JButton("Enviar");
        acciones.add(this.btnEnviar);

        JPanel panel_botones = new JPanel();
        contentPane.add(panel_botones, BorderLayout.SOUTH);
        panel_botones.setLayout(new GridLayout(2, 3, 0, 0));

        JLabel lblNewLabel = new JLabel("");
        panel_botones.add(lblNewLabel);

        this.btnAgregarChat = new JButton("Nueva conversacion");
        this.btnAgregarChat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_botones.add(this.btnAgregarChat);

        this.btnAgregarContacto = new JButton("Agregar Contacto");
        panel_botones.add(this.btnAgregarContacto);

        this.btnLoguout = new JButton("Cerrar Sesión");
        panel_botones.add(this.btnLoguout);

        JPanel panel_norte = new JPanel();
        contentPane.add(panel_norte, BorderLayout.NORTH);

        this.txtf_buscar = new JTextField();
        this.txtf_buscar.setText("a quien buscas?");
        panel_norte.add(this.txtf_buscar);
        this.txtf_buscar.setColumns(10);

        this.btnBuscar = new JButton("buscar");
        panel_norte.add(this.btnBuscar);
    }

    @Override
    public void setActionListener(ActionListener actionListener){
        this.btnBuscar.addActionListener(actionListener);
    };
    @Override
    public void setVisibleVentana(boolean estado){
        this.setVisible(estado);
    };

    public JButton getBtnNuevoContacto() {
        return btnAgregarContacto;
    }

    public JButton getBtnNuevaConversacion() {
        return btnAgregarChat;
    }

    public JButton getEnviarMensaje() {
        return btnEnviar;
    }

    public String getMensaje() {
        return this.txtf_mensaje.getText();
    }

    public void limpiarcampos() {
        this.txtf_buscar.setText("");
        this.txtf_mensaje.setText("");
    }
}

