package oproyectoedd;

import java.util.Scanner;

public class MenuConsola {
    private GestorContactos gestor;
    private Scanner scanner;

    public MenuConsola() {
        gestor = new GestorContactos();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
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
}
