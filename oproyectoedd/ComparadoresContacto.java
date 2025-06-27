package oproyectoedd;

import java.util.Comparator;

public class ComparadoresContacto {

    // Ordena por nombre (atributo "nombre")
    public static Comparator<Contacto> porNombre = new Comparator<Contacto>() {
        @Override
        public int compare(Contacto c1, Contacto c2) {
            String n1 = c1.getAtributos().getOrDefault("nombre", "").toLowerCase();
            String n2 = c2.getAtributos().getOrDefault("nombre", "").toLowerCase();
            return n1.compareTo(n2);
        }
    };

    // Ordena por tipo de contacto ("persona" o "empresa")
    public static Comparator<Contacto> porTipo = new Comparator<Contacto>() {
        @Override
        public int compare(Contacto c1, Contacto c2) {
            return c1.getTipo().compareToIgnoreCase(c2.getTipo());
        }
    };

    // Ordena por país (atributo "pais")
    public static Comparator<Contacto> porPais = new Comparator<Contacto>() {
        @Override
        public int compare(Contacto c1, Contacto c2) {
            String p1 = c1.getAtributos().getOrDefault("pais", "").toLowerCase();
            String p2 = c2.getAtributos().getOrDefault("pais", "").toLowerCase();
            return p1.compareTo(p2);
        }
    };

    // Ordena por cantidad de atributos (de mayor a menor)
    public static Comparator<Contacto> porCantidadAtributos = new Comparator<Contacto>() {
        @Override
        public int compare(Contacto c1, Contacto c2) {
            return Integer.compare(c2.getAtributos().size(), c1.getAtributos().size());
        }
    };
}
    