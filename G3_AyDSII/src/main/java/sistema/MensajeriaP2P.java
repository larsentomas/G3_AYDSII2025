package sistema;
import java.util.Scanner;

public class MensajeriaP2P {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuración inicial
        System.out.print("Ingresa tu nickname: ");
        String nickname = scanner.nextLine();
        System.out.print("Ingresa el puerto de comunicación: ");
        int puerto = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        Usuario usuario = new Usuario(nickname, puerto);
        Servidor servidor = new Servidor(puerto);
        new Thread(servidor::iniciar).start();

        while (true) {
            System.out.println("1. Registrar Contacto");
            System.out.println("2. Enviar Mensaje");
            System.out.println("3. Listar Conversaciones Activas");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1 -> {
                    System.out.print("Alias del contacto: ");
                    String alias = scanner.nextLine();
                    System.out.print("IP del contacto: ");
                    String ip = scanner.nextLine();
                    System.out.print("Puerto del contacto: ");
                    int puertoContacto = scanner.nextInt();
                    scanner.nextLine();

                    Contacto contacto = new Contacto(alias, ip, puertoContacto);
                    usuario.agregarContacto(contacto);
                }
                case 2 -> {
                    System.out.print("IP del destinatario: ");
                    String ipDestino = scanner.nextLine();
                    System.out.print("Puerto del destinatario: ");
                    int puertoDestino = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Escribe tu mensaje: ");
                    String mensaje = scanner.nextLine();

                    Cliente cliente = new Cliente(ipDestino, puertoDestino);
                    cliente.enviarMensaje(nickname + ": " + mensaje);
                }
                case 3 -> usuario.getConversaciones().forEach(conversacion ->
                        System.out.println("Conversación activa con: " + conversacion.getContacto().getAlias()));
                default -> System.out.println("Opción inválida");
            }
        }
    }
}