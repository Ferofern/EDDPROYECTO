package oproyectoedd;

import java.util.Map;
import java.util.TreeMap;

public class Contacto {
    private final String id;
    private String tipo;
    private TreeMap<String, String> atributos;

    private String[] telefonos = new String[10];
    private int totalTelefonos = 0;

    private String[] fotos = new String[10];
    private int totalFotos = 0;
    private int indiceFoto = 0;

    private Contacto[] asociados = new Contacto[10];
    private int totalAsociados = 0;

    public Contacto(String id, String tipo, String telefonoInicial) {
        this.id = id;
        this.tipo = tipo;
        this.atributos = new TreeMap<>();
        addTelefono(telefonoInicial);
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public TreeMap<String, String> getAtributos() { return atributos; }

    // Teléfonos
    public void addTelefono(String telefono) {
        if (telefono != null && !telefono.isEmpty() && totalTelefonos < telefonos.length) {
            for (int i = 0; i < totalTelefonos; i++) {
                if (telefonos[i].equals(telefono)) return;
            }
            telefonos[totalTelefonos++] = telefono;
        }
    }

    public void removeTelefono(String telefono) {
        for (int i = 0; i < totalTelefonos; i++) {
            if (telefonos[i].equals(telefono)) {
                for (int j = i; j < totalTelefonos - 1; j++) {
                    telefonos[j] = telefonos[j + 1];
                }
                telefonos[--totalTelefonos] = null;
                break;
            }
        }
    }

    public void mostrarTelefonos() {
        System.out.print("Teléfonos: ");
        for (int i = 0; i < totalTelefonos; i++) {
            System.out.print(telefonos[i]);
            if (i < totalTelefonos - 1) System.out.print(", ");
        }
        System.out.println();
    }

    // Fotos
    public void addFoto(String ruta) {
        if (totalFotos < fotos.length) {
            fotos[totalFotos++] = ruta;
        }
    }

    public String siguienteFoto() {
        if (totalFotos == 0) return "Sin fotos";
        indiceFoto = (indiceFoto + 1) % totalFotos;
        return fotos[indiceFoto];
    }

    public String anteriorFoto() {
        if (totalFotos == 0) return "Sin fotos";
        indiceFoto = (indiceFoto - 1 + totalFotos) % totalFotos;
        return fotos[indiceFoto];
    }

    public void removeFoto(String ruta) {
        for (int i = 0; i < totalFotos; i++) {
            if (fotos[i].equals(ruta)) {
                for (int j = i; j < totalFotos - 1; j++) {
                    fotos[j] = fotos[j + 1];
                }
                fotos[--totalFotos] = null;
                break;
            }
        }
    }

    public void mostrarFotos() {
        System.out.println("Fotos:");
        for (int i = 0; i < totalFotos; i++) {
            System.out.println("  [" + (i+1) + "] " + fotos[i]);
        }
    }

    // Asociados
    public void addAsociado(Contacto c) {
        if (totalAsociados < asociados.length) {
            asociados[totalAsociados++] = c;
        }
    }

    public void mostrarAsociados() {
        System.out.println("Contactos asociados:");
        for (int i = 0; i < totalAsociados; i++) {
            System.out.println("  ID: " + asociados[i].getId() + ", Tipo: " + asociados[i].getTipo());
        }
    }

    // Atributos
    public void addAtributo(String clave, String valor) {
        atributos.put(clave, valor);
    }

    public void removeAtributo(String clave) {
        atributos.remove(clave);
    }

    public void mostrarAtributos() {
        System.out.println("Atributos:");
        for (Map.Entry<String, String> entry : atributos.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
