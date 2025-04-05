// VistaInicio.java
package vista;

import modelo.Usuario;
import modelo.Conversacion;
import modelo.Mensaje;
import sistema.MensajeriaP2P;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

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
    private DefaultListModel<Conversacion> listModelConversaciones;
    private JTextField mensajeField;
    private JButton enviarButton;
    private Conversacion conversacion;

    public VistaInicio() {
        // Initialize components
        mensajeField = new JTextField();
        enviarButton = new JButton("Enviar");

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

        this.listModelConversaciones = new DefaultListModel<>();
        this.listaConversaciones = new JList<>(listModelConversaciones);
        this.listaConversaciones.setVisibleRowCount(6);
        this.listaConversaciones.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Conversacion selectedConversacion = (Conversacion) listaConversaciones.getSelectedValue();
                if (selectedConversacion != null) {
                    actualizarPanelChat(selectedConversacion);
                }
            }
        });
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
        panel_botones.add(this.btnAgregarChat);

        this.btnAgregarContacto = new JButton("Agregar Usuario");
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
    public void setActionListener(ActionListener actionListener) {
        this.btnBuscar.addActionListener(actionListener);
        this.btnAgregarChat.addActionListener(actionListener);
        this.btnAgregarContacto.addActionListener(actionListener);
        this.btnEnviar.addActionListener(actionListener);
        this.btnLoguout.addActionListener(actionListener);
    }

    @Override
    public void setVisibleVentana(boolean estado) {
        this.setVisible(estado);
    }

    @Override
    public void limpiarcampos() {
        this.txtf_buscar.setText("");
        this.txtf_mensaje.setText("");
    }

    // Manejo de conversaciones

    public void actualizarPanelChat(Conversacion conversacion) {
        lista_chat.removeAll();
        Usuario usuarioAgendado = MensajeriaP2P.getInstance().existeUsuario(conversacion.getUsuario().getIp(), conversacion.getUsuario().getPuerto());
        for (Mensaje mensaje : conversacion.getMensajes()) {
            lista_chat.add("[" + mensaje.getTimestamp().getTime() + "]" + usuarioAgendado.getNickname() + ":" + mensaje.toString());
        }
        lista_chat.revalidate();
        lista_chat.repaint();
        setConversacion(conversacion);
        btnEnviar.setEnabled(conversacion.isActiva());
        txtf_mensaje.setEnabled(conversacion.isActiva());
    }

    public void actualizarListaConversaciones() {
        listModelConversaciones.clear();
        for (Conversacion conversacion : MensajeriaP2P.getInstance().getUser().getConversaciones()) {
            listModelConversaciones.addElement(conversacion);
        }
    }


    // Getters y Setters
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

    public Conversacion getConversacionActiva() {
        return this.conversacion;
    }

    public void setConversacion(Conversacion conversacion) {
        this.conversacion = conversacion;
    }


    // Modales

    public Usuario mostrarModalNuevaConversacion() {
        ArrayList<Usuario> contactosSinConversacion = MensajeriaP2P.getInstance().getUser().getContactosSinConversacion();
        Usuario[] opciones = contactosSinConversacion.toArray(new Usuario[0]);
        return (Usuario) JOptionPane.showInputDialog(
                this,
                "Seleccione un contacto para iniciar una nueva conversación:",
                "Nueva Conversación",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
    }

    public String[] mostrarModalAgregarContacto() {
        JTextField nombreField = new JTextField();
        JTextField ipField = new JTextField();
        JTextField puertoField = new JTextField();

        Object[] message = {
                "Nombre:", nombreField,
                "IP:", ipField,
                "Puerto:", puertoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Agregar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            return new String[]{nombreField.getText(), ipField.getText(), puertoField.getText()};
        } else {
            return null;
        }
    }

    public void mostrarModalError(String s) {
        JOptionPane.showMessageDialog(this, s, "Error", JOptionPane.ERROR_MESSAGE);
    }

}

