package oproyectoedd;

import java.util.*;

public class GestorContactos {
    private TreeMap<String, Contacto> contactos;
    private Queue<Integer> colaIds;

    public GestorContactos() {
        contactos = new TreeMap<>();
        colaIds = new LinkedList<>();
        for (int i = 1; i <= 1000; i++) {
            colaIds.add(i);
        }
    }

    public Contacto crearContacto(String tipo, String telefonoInicial) {
        if (telefonoInicial == null || telefonoInicial.length() < 2) {
            throw new IllegalArgumentException("El teléfono inicial debe tener al menos 2 dígitos.");
        }
        if (colaIds.isEmpty()) {
            throw new RuntimeException("No hay más IDs disponibles.");
        }
        int numeroCola = colaIds.poll();
        String ultimosDos = telefonoInicial.substring(telefonoInicial.length() - 2);
        String id = numeroCola + ultimosDos;
        Contacto c = new Contacto(id, tipo, telefonoInicial);
        contactos.put(id, c);
        return c;
    }

    public void eliminarContacto(String id) {
        if (contactos.containsKey(id)) {
            contactos.remove(id);
            try {
                String numeroStr = id.substring(0, id.length() - 2);
                int numero = Integer.parseInt(numeroStr);
                colaIds.add(numero);
            } catch (NumberFormatException e) {
                // No reciclar si no es válido
            }
        }
    }

    public Collection<Contacto> getTodos() {
        return contactos.values();
    }

    public Contacto getPorId(String id) {
        return contactos.get(id);
    }

    // Método para obtener lista ordenada por nombre usando ComparadorPorNombre
    public List<Contacto> getContactosOrdenadosPorNombre() {
        List<Contacto> lista = new ArrayList<>(contactos.values());
        lista.sort(new ComparadorPorNombre());
        return lista;
    }

    public void mostrarContactosOrdenadosPorNombre() {
        List<Contacto> lista = getContactosOrdenadosPorNombre();
        for (Contacto c : lista) {
            System.out.println("ID: " + c.getId());
            System.out.println("Nombre: " + c.getAtributos().getOrDefault("nombre", "N/A"));
            System.out.println("Tipo: " + c.getTipo());
            c.mostrarTelefonos();
            c.mostrarAtributos();
            System.out.println("--------------------------");
        }
    }
}


