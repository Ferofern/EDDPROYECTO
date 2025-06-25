package oproyectoedd;

public class OProyectoEDD {
    public static void main(String[] args) {
        GestorContactos gestor = new GestorContactos();

        Contacto c1 = gestor.crearContacto("persona", "0987654321");
        c1.addAtributo("nombre", "Felix");
        c1.addAtributo("apellido", "Romero");
        c1.addTelefono("0991234567");

        Contacto c2 = gestor.crearContacto("empresa", "022334455");
        c2.addAtributo("nombre", "Tech Corp");
        c2.addAtributo("pais", "Ecuador");

        System.out.println("Contactos ordenados por nombre:");
        gestor.mostrarContactosOrdenadosPorNombre();
    }
}

