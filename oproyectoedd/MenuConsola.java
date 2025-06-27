package oproyectoedd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuConsola {
    private GestorContactos gestor;
    private Scanner scanner;
    private static final String ARCHIVO = "contactos.txt";

    public MenuConsola() {
        gestor = new GestorContactos();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        cargarDesdeArchivo();
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENU DE CONTACTOS ---");
            System.out.println("1. Crear contacto");
            System.out.println("2. Mostrar todos los contactos");
            System.out.println("3. Buscar contacto por ID");
            System.out.println("4. Eliminar contacto");
            System.out.println("5. Navegar contactos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1 -> crearContacto();
                case 2 -> mostrarContactos();
                case 3 -> buscarContacto();
                case 4 -> eliminarContacto();
                case 5 -> navegarContactos();
                case 6 -> {
                    guardarEnArchivo();
                    System.out.println("Saliendo...");
                    salir = true;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void crearContacto() {
        System.out.print("Tipo (persona/empresa): ");
        String tipo = scanner.nextLine();
        System.out.print("Teléfono inicial: ");
        String telefono = scanner.nextLine();

        try {
            Contacto c = gestor.crearContacto(tipo, telefono);

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            c.addAtributo("nombre", nombre);

            if (tipo.equalsIgnoreCase("persona")) {
                System.out.print("Apellido: ");
                String apellido = scanner.nextLine();
                c.addAtributo("apellido", apellido);
            } else if (tipo.equalsIgnoreCase("empresa")) {
                System.out.print("País: ");
                String pais = scanner.nextLine();
                c.addAtributo("pais", pais);
            }

            System.out.println("Contacto creado con ID: " + c.getId());
        } catch (Exception e) {
            System.out.println("Error al crear contacto: " + e.getMessage());
        }
    }

    private void mostrarContactos() {
        Contacto[] todos = gestor.getTodos();
        if (todos.length == 0) {
            System.out.println("No hay contactos para mostrar.");
            return;
        }
        gestor.mostrarContactos(todos);
    }

    private void buscarContacto() {
        System.out.print("Ingrese ID del contacto: ");
        String id = scanner.nextLine();
        Contacto c = gestor.getPorId(id);
        if (c == null) {
            System.out.println("Contacto no encontrado.");
            return;
        }
        gestor.mostrarContactos(new Contacto[]{c});
    }

    private void eliminarContacto() {
        System.out.print("Ingrese ID del contacto a eliminar: ");
        String id = scanner.nextLine();
        if (gestor.existeId(id)) {
            gestor.eliminarContacto(id);
            System.out.println("Contacto eliminado.");
        } else {
            System.out.println("ID no encontrado.");
        }
    }

    private void navegarContactos() {
        Contacto[] lista = gestor.getTodos();
        if (lista.length == 0) {
            System.out.println("No hay contactos para navegar.");
            return;
        }
        NavegadorContactos navegador = new NavegadorContactos(lista);
        boolean salir = false;
        while (!salir) {
            Contacto actual = navegador.actual();
            System.out.println("\nContacto actual:");
            gestor.mostrarContactos(new Contacto[]{actual});

            System.out.println("Opciones: [N]ext, [P]revious, [S]alir");
            String opcion = scanner.nextLine().toUpperCase();

            switch (opcion) {
                case "N" -> navegador.siguiente();
                case "P" -> navegador.anterior();
                case "S" -> salir = true;
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    private void cargarDesdeArchivo() {
        File file = new File(ARCHIVO);
        if (!file.exists()) {
            System.out.println("No se encontró archivo de datos, iniciando vacío.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String linea;
            // Mapa para almacenar temporalmente las asociaciones
            Map<String, String[]> mapaAsociaciones = new HashMap<>();

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 2) continue;

                String id = partes[0];
                String tipo = partes[1];

                Contacto c = new Contacto(id, tipo, "00000000");

                for (int i = 2; i < partes.length; i++) {
                    String parte = partes[i];
                    if (parte.startsWith("telefonos=")) {
                        String[] telfs = parte.substring(9).split(";");
                        for (String t : telfs) c.addTelefono(t);
                    } else if (parte.startsWith("fotos=")) {
                        String[] fotos = parte.substring(6).split(";");
                        for (String f : fotos) c.addFoto(f);
                    } else if (parte.startsWith("asociados=")) {
                        String[] idsAsociados = parte.substring(9).split(";");
                        mapaAsociaciones.put(id, idsAsociados);
                    } else if (parte.contains("=")) {
                        String[] kv = parte.split("=", 2);
                        c.addAtributo(kv[0], kv[1]);
                    }
                }

                gestor.agregarContactoDirectamente(id, c);
            }

            // Asociar contactos después de cargar todos
            for (Map.Entry<String, String[]> entry : mapaAsociaciones.entrySet()) {
                String id = entry.getKey();
                String[] asociadosIds = entry.getValue();
                for (String asociadoId : asociadosIds) {
                    if (gestor.existeId(id) && gestor.existeId(asociadoId)) {
                        gestor.asociarContactos(id, asociadoId);
                    }
                }
            }

            System.out.println("Datos cargados desde " + ARCHIVO);

        } catch (Exception e) {
            System.out.println("Error leyendo archivo: " + e.getMessage());
        }
    }


    private void guardarEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            Contacto[] todos = gestor.getTodos();
            for (Contacto c : todos) {
                // Guardar ID y tipo
                pw.print(c.getId() + "," + c.getTipo());

                // Guardar atributos
                for (var e : c.getAtributos().entrySet()) {
                    pw.print("," + e.getKey() + "=" + e.getValue());
                }

                // Aquí es donde reemplazas las partes para guardar teléfonos, fotos y asociados:

                // Guardar teléfonos
                pw.print(",telefonos=");
                for (int i = 0; i < c.getTotalTelefonos(); i++) {
                    pw.print(c.getTelefonosArray()[i]);
                    if (i < c.getTotalTelefonos() - 1) pw.print(";");
                }

                // Guardar fotos
                pw.print(",fotos=");
                for (int i = 0; i < c.getTotalFotos(); i++) {
                    pw.print(c.getFotosArray()[i]);
                    if (i < c.getTotalFotos() - 1) pw.print(";");
                }

                // Guardar asociados (solo IDs)
                pw.print(",asociados=");
                for (int i = 0; i < c.getTotalAsociados(); i++) {
                    pw.print(c.getAsociadosArray()[i].getId());
                    if (i < c.getTotalAsociados() - 1) pw.print(";");
                }

                pw.println();  // nueva línea para el siguiente contacto
            }
            System.out.println("Datos guardados en " + ARCHIVO);
        } catch (Exception e) {
            System.out.println("Error guardando archivo: " + e.getMessage());
        }
    }
}
