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

    public Contacto getPorId(String id) {
        return contactos.get(id);
    }

    public Contacto[] getTodos() {
        Collection<Contacto> valores = contactos.values();
        return valores.toArray(new Contacto[0]);
    }

    public Contacto[] getContactosOrdenados(Comparator<Contacto> comp) {
        Contacto[] lista = getTodos();
        Arrays.sort(lista, comp);
        return lista;
    }

    public void mostrarContactos(Contacto[] lista) {
        for (Contacto c : lista) {
            System.out.println("ID: " + c.getId());
            System.out.println("Nombre: " + c.getAtributos().getOrDefault("nombre", "N/A"));
            System.out.println("Tipo: " + c.getTipo());
            c.mostrarTelefonos();
            c.mostrarAtributos();
            c.mostrarFotos();
            c.mostrarAsociados();
            System.out.println("--------------------------");
        }
    }

    // Ejemplo de filtro por país
    public Contacto[] filtrarPorAtributo(String clave, String valor) {
        List<Contacto> resultado = new ArrayList<>();
        for (Contacto c : contactos.values()) {
            String val = c.getAtributos().get(clave);
            if (val != null && val.equalsIgnoreCase(valor)) {
                resultado.add(c);
            }
        }
        return resultado.toArray(new Contacto[0]);
    }

    public boolean existeId(String id) {
        return contactos.containsKey(id);
    }

    public void asociarContactos(String id1, String id2) {
        Contacto c1 = contactos.get(id1);
        Contacto c2 = contactos.get(id2);
        if (c1 != null && c2 != null) {
            c1.addAsociado(c2);
            c2.addAsociado(c1);
        }
    }
} 
