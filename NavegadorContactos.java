package oproyectoedd;
import java.util.List;

public class NavegadorContactos {
    private List<Contacto> lista;
    private int actual;

    public NavegadorContactos(List<Contacto> contactos) {
        if (contactos == null || contactos.isEmpty()) {
            throw new IllegalArgumentException("La lista de contactos no puede estar vacia.");
        }
        this.lista = contactos;
        this.actual = 0;
    }

    public Contacto siguiente() {
        actual = (actual + 1) % lista.size();
        return lista.get(actual);
    }

    public Contacto anterior() {
        actual = (actual - 1 + lista.size()) % lista.size();
        return lista.get(actual);
    }

    public Contacto actual() {
        return lista.get(actual);
    }
}
