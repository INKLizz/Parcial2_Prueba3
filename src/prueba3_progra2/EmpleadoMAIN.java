/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package prueba3_progra2;

/**
 *
 * @author Laura Sabillon
 */
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmpleadoMAIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // TODO code application logic here
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        EmpleadoManager manager = new EmpleadoManager();
        
        while (true) {
            System.out.println("\n\n|    -   MENU    -   |\n");
            System.out.println("1.- Agregar Empleado");
            System.out.println("2.- Lista Empleados No Despedidos");
            System.out.println("3.- Agregar Ventana al Empleado");
            System.out.println("4.- Pagar Empleado");
            System.out.println("5.- Despedir al Empelado");
            System.out.println("6.- Mostrar informacion de un empleado");
            System.out.println("7.- Salir");
            System.out.println("Escoja una opcion! (1,2,3,4,5,6 o 7)");
            try {
                int opcion = scan.nextInt();
                
                switch (opcion) {
                    case (1):
                        System.out.println("-       AGREGAR EMPLEADO        -");
                        System.out.println("Ingrese el nombre del empleado: ");
                        String nombre = scan.next();
                        System.out.println("Ingrese el salario del empleado: ");
                        Double salario = scan.nextDouble();
                        manager.addEmployee(nombre, salario);
                        break;
                    case (2):
                        System.out.println("-       LISTA DE EMPLEADOS NO DESPEDIDOS        -");
                        manager.Lista();
                        break;
                    case (3):
                        System.out.println("-       AGREGAR VENTANA AL EMPLEADO     -");
                        System.out.println("Escribir el codigo del empleado: ");
                        int code = scan.nextInt();
                        System.out.println("Escribir el monto a realizar: ");
                        double monto = scan.nextDouble();
                        manager.AgregarVenta(code, monto);
                        break;
                    case (4):
                        System.out.println("-       PAGAR EMPLEADO      -");
                        System.out.println("Ingresar el codigo del empleado: ");
                        int codigo = scan.nextInt();
                        manager.payEmployee(codigo);
                        break;
                    case (5):
                        System.out.println("-       DESPEDIR AL EMPLEADO        -");
                        System.out.println("Ingresar el codigo del empleado: ");
                        int cod = scan.nextInt();
                        manager.DespedirEmpleado(cod);
                        break;
                    case (6):
                        System.out.println("-       INFORMACION DEL EMPLEADO        -");
                        System.out.println("Escribir el codigo del empleado: ");
                        int codi = scan.nextInt();
                        manager.PrintEmployee(codi);
                        break;
                    case (7):
                        System.out.println("-       SALIR       -");
                        System.out.println("Saliendo del sistema....");
                        System.exit(0);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Favor ingrese una opcion valida!");
                scan.next();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
