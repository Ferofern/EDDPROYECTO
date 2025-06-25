package oproyectoedd;

import java.util.Comparator;

public class ComparadorPorNombre implements Comparator<Contacto> {
    @Override
    public int compare(Contacto c1, Contacto c2) {
        String nombre1 = c1.getAtributos().getOrDefault("nombre", "").toLowerCase();
        String nombre2 = c2.getAtributos().getOrDefault("nombre", "").toLowerCase();
        return nombre1.compareTo(nombre2);
    }
}
