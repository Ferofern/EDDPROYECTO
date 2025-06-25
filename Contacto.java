package oproyectoedd;
import java.util.*;

public class Contacto {
    private final String id; 
    private String tipo;
    private TreeMap<String, String> atributos;
    private List<String> telefonos;
    private List<Contacto> asociados;

    public Contacto(String id, String tipo, String telefonoInicial) {
        this.id = id;
        this.tipo = tipo;
        this.atributos = new TreeMap<>();
        this.telefonos = new ArrayList<>();
        this.telefonos.add(telefonoInicial);
        this.asociados = new ArrayList<>();
    }

    public String getId() { return id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public TreeMap<String, String> getAtributos() { return atributos; }

    public List<String> getTelefonos() { return telefonos; }

    public void addTelefono(String telefono) {
        if (telefono != null && !telefono.isEmpty() && !telefonos.contains(telefono)) {
            telefonos.add(telefono);
        }
    }

    public void removeTelefono(String telefono) {
        telefonos.remove(telefono);
    }

    public List<Contacto> getAsociados() { return asociados; }

    public void addAsociado(Contacto c) {
        asociados.add(c);
    }

    public void addAtributo(String clave, String valor) {
        atributos.put(clave, valor);
    }

    public void removeAtributo(String clave) {
        atributos.remove(clave);
    }

    public void mostrarAtributos() {
        Iterator<Map.Entry<String, String>> it = atributos.entrySet().iterator();
        System.out.println("Atributos:");
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
    }

    public void mostrarTelefonos() {
        Iterator<String> it = telefonos.iterator();
        System.out.print("Telefonos: ");
        while (it.hasNext()) {
            System.out.print(it.next());
            if (it.hasNext()) System.out.print(", ");
        }
        System.out.println();
    }
    
    public void mostrarAsociados() {
        Iterator<Contacto> it = asociados.iterator();
        System.out.println("Contactos asociados:");
        while (it.hasNext()) {
            Contacto c = it.next();
            System.out.println("  ID: " + c.getId() + ", Tipo: " + c.getTipo());
        }
    }
}
