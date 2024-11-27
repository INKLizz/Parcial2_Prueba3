/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba3_progra2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Laura Sabillon
 */
public class EmpleadoManager {

    private RandomAccessFile rcods, remps;

    /*
    Formato Codigo.emp
    int code
    Formato Empleado.emp
    String name
    double salary
    long fecha Contratacion 
    long fecha despido
     */

    public EmpleadoManager() {
        try {
            //1.- Asegurar que el folder se cree con el nombre company!! (que exista) carpeta principal
            File mf = new File("company");
            mf.mkdir();
            //2.- Instancia los archivos binarios dentro  "company"
            rcods = new RandomAccessFile("company/codigos.emp", "rw");
            remps = new RandomAccessFile("company/empleados.emp", "rw");
            initCodes();
        } catch (IOException e) {
            //3.- Inicializar el archivo de codigo si es nuevo

        }
    }

    private void initCodes() throws IOException {
        if (rcods.length() == 0) {
            //Puntero -> 0
            rcods.writeInt(1);
            //Puntero -> 4
        }
    }

    private int getCode() throws IOException {
        rcods.seek(0);
        //Puntero -> 0
        int code = rcods.readInt();
        //Puntero -> 4
        rcods.seek(0);
        rcods.writeInt(code + 1);
        return code;
    }

    private void initEmpleado() throws IOException {
        if (remps.length() == 0) {
            remps.writeUTF("");
            remps.writeDouble(1000);
            remps.writeLong(0);
        }
    }

    public void addEmployee(String nombre, double salario) throws IOException {
        //asegurar que el puntero este en el final del archivo.
        remps.seek(remps.length());
        int code = getCode();

        remps.writeInt(code);
        remps.writeUTF(nombre);
        remps.writeDouble(salario);
        remps.writeLong(Calendar.getInstance().getTimeInMillis());
        remps.writeLong(0);

        //asegurar crear una carpeta y archivo individuales
        createEmployeeFolder(code);
    }

    private String employeeFolder(int code) {
        return "company/empleado" + code;
    }

    private void createEmployeeFolder(int code) throws IOException {
        //crear folder de empleado
        File emp = new File(employeeFolder(code));
        emp.mkdir();
        //crear archivo de ventas
        createYearSaleFile(code);
    }

    private RandomAccessFile salesFileFor(int code) throws IOException {
        String dirPadre = employeeFolder(code);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String path = dirPadre + "/ventas" + year + ".emp";
        return new RandomAccessFile(path, "rw");
    }

    private void createYearSaleFile(int code) throws IOException {
        RandomAccessFile ryear = salesFileFor(code);
        if (ryear.length() == 0) {
            for (int mes = 0; mes < 12; mes++) {
                ryear.writeDouble(0);
                ryear.writeBoolean(false);
            }
        }
    }

    public void AgregarVenta(int code, double monto) throws IOException {
        if (isEmployeeActive(code)) {
            RandomAccessFile path = salesFileFor(code);
            int month = Calendar.getInstance().get(Calendar.MONTH);
            long position = month * 9;
            path.seek(position);
            double venta = path.readDouble();
            path.seek(position);
            path.writeDouble(venta + monto);
            path.readBoolean();
            path.close();
        }
    }
    
    public void payEmployee(int code) throws IOException {
        String path = employeeFolder(code) + "/recibos.emp";
        RandomAccessFile recibo = new RandomAccessFile(path, "rw");
        RandomAccessFile ryear = salesFileFor(code);
        
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        long position = month * 9;
        ryear.seek(position);
        double ventas = ryear.readDouble();
        boolean pagado = ryear.readBoolean();
        remps.seek(4);
        String nombre = remps.readUTF();
        
        if (isEmployeeActive(code) && !pagado) {
            recibo.writeLong(Calendar.getInstance().getTimeInMillis());
            double comission = ventas * 0.10;
            recibo.writeDouble(comission);
            recibo.writeDouble(ventas + 1000);
            double deduccion = 1000 * 0.035;
            recibo.writeDouble(deduccion);
            double sueldoNeto = 1000 - deduccion;
            recibo.writeInt(year);
            recibo.writeInt(month);
            ryear.seek(position + 8);
            ryear.writeBoolean(true);
            System.out.println("Codigo: " + code + "Empleado: " + nombre + "Mes: " + month + "Sueldo Neto: " + sueldoNeto);
        }
    }
    
    public void PrintEmployee(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int codigo = remps.readInt();
            String Nombre = remps.readUTF();
            double salario = remps.readDouble();
            Date date = new Date(remps.readLong());
            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
            String fechaForm = form.format(date);
            long terminacion = remps.readLong();
            String path = employeeFolder(code) + "/recibos.emp";
            RandomAccessFile file = salesFileFor(code);
            file.seek(0);
            while (file.getFilePointer() < file.length()){
                long FechaPago = file.readLong();
                double comision = file.readDouble();
                double sueldo = file.readDouble();
                double deduccion = file.readDouble();
                double neto = file.readDouble();
                
                
            }
            
            
            if (codigo == code && terminacion == 0) {
                System.out.println("|Codigo : " + codigo + " | Nombre : " + Nombre + " | Salario : Lps." + salario + " | Fecha de ingreso: " + fechaForm + " |");
            }
        }
        System.out.println("Empleado no existe!");
    }

    public void Lista() throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int code = remps.readInt();
            String Nombre = remps.readUTF();
            double salario = remps.readDouble();
            Date date = new Date(remps.readLong());
            SimpleDateFormat form = new SimpleDateFormat("dd-MM-yyyy");
            String fechaForm = form.format(date);
            long terminacion = remps.readLong();
            if (terminacion == 0) {
                System.out.println("|Codigo : " + code + " | Nombre : " + Nombre
                        + " | Salario : Lps." + salario + " | Fecha de ingreso: " + fechaForm + " |");
            }
        }
    }

    public boolean DespedirEmpleado(int code) throws IOException {
        if (isEmployeeActive(code)) {
            String nombre = remps.readUTF();
            remps.skipBytes(16);
            remps.writeLong(new Date().getTime());
            System.out.println("Despidiendo a : " + nombre);
            return true;
        }
        System.out.println("No existe este empleado!");
        return false;
    }

    private boolean isEmployeeActive(int code) throws IOException {
        remps.seek(0);
        while (remps.getFilePointer() < remps.length()) {
            int codigo = remps.readInt();
            long pos = remps.getFilePointer();
            remps.readUTF();
            remps.skipBytes(16);
            if (remps.readLong() == 0 && codigo == code) {
                remps.seek(pos);
                return true;
            }
        }
        return false;
    }
    
}
