package colaboletos;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Bus {
    public static final int CAPACIDAD = 20;
    public static final int MAX_POR_CEDULA = 5;

    private String ruta;
    private double precio;
    Queue<Pasajero> cola;
    ArrayList<Pasajero> abordados;

    public Bus(String ruta, double precio) {
        this.ruta = ruta;
        this.precio = precio;
        cola = new ArrayDeque<Pasajero>();
        abordados = new ArrayList<Pasajero>();
    }

    public String getRuta() {
        return ruta;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean isEmpty() {
        return cola.isEmpty();
    }

    // enqueue con todas las validaciones
    public void encolar(Pasajero p) throws Exception {
        if (p == null) {
            throw new Exception("El pasajero no puede ser null");
        }
        if (p.getCedula() == null || p.getCedula().trim().isEmpty()) {
            throw new Exception("La cedula no puede estar vacia");
        }
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new Exception("El nombre no puede estar vacio");
        }
        if (p.getCantidad() < 1 || p.getCantidad() > MAX_POR_CEDULA) {
            throw new Exception("Solo se pueden comprar de 1 a " + MAX_POR_CEDULA + " boletos");
        }

        int yaComprados = boletosPorCedula(p.getCedula());
        if (yaComprados + p.getCantidad() > MAX_POR_CEDULA) {
            int permitidos = MAX_POR_CEDULA - yaComprados;
            throw new Exception("La cedula " + p.getCedula() + " ya tiene " + yaComprados
                    + " boleto(s) en esta ruta. Solo puede comprar " + permitidos + " mas");
        }
        if (getVendidos() + p.getCantidad() > CAPACIDAD) {
            throw new Exception("No hay suficientes asientos. Disponibles: " + getDisponibles());
        }

        Pasajero anterior = buscarPorCedula(p.getCedula());
        if (anterior != null && !anterior.getNombre().equalsIgnoreCase(p.getNombre().trim())) {
            throw new Exception("La cedula " + p.getCedula() + " ya esta registrada a nombre de "
                    + anterior.getNombre());
        }

        // si ya esta esperando en la cola se le suman los boletos, si no se encola nuevo
        Pasajero enCola = buscarEnCola(p.getCedula());
        if (enCola != null) {
            enCola.setCantidad(enCola.getCantidad() + p.getCantidad());
        } else {
            cola.add(p);
        }
    }

    public Pasajero siguientePasajero() throws Exception {
        if (!isEmpty()) {
            return cola.peek();
        }
        throw new Exception("No hay pasajeros en espera");
    }


    public Pasajero desencolar() throws Exception {
        if (!isEmpty()) {
            return cola.poll();
        }
        throw new Exception("No hay pasajeros en espera");
    }

    public Pasajero embarcar() throws Exception {
        Pasajero p = desencolar();
        abordados.add(p);
        return p;
    }

    private Pasajero buscarEnCola(String cedula) {
        for (Pasajero p : cola) {
            if (p.getCedula().equals(cedula)) {
                return p;
            }
        }
        return null;
    }

    public Pasajero buscarPorCedula(String cedula) {
        Pasajero p = buscarEnCola(cedula);
        if (p != null) {
            return p;
        }
        for (Pasajero a : abordados) {
            if (a.getCedula().equals(cedula)) {
                return a;
            }
        }
        return null;
    }

    public int boletosPorCedula(String cedula) {
        int total = 0;
        for (Pasajero p : cola) {
            if (p.getCedula().equals(cedula)) {
                total += p.getCantidad();
            }
        }
        for (Pasajero a : abordados) {
            if (a.getCedula().equals(cedula)) {
                total += a.getCantidad();
            }
        }
        return total;
    }

    public int getVendidos() {
        int total = 0;
        for (Pasajero p : cola) {
            total += p.getCantidad();
        }
        for (Pasajero a : abordados) {
            total += a.getCantidad();
        }
        return total;
    }

    public int getDisponibles() {
        return CAPACIDAD - getVendidos();
    }

    public double getRecaudado() {
        double total = 0;
        for (Pasajero p : cola) {
            total += p.getPrecioTotal();
        }
        for (Pasajero a : abordados) {
            total += a.getPrecioTotal();
        }
        return total;
    }

    public String sacarCola() {
        StringBuilder sb = new StringBuilder();
        for (Pasajero p : cola) {
            sb.append(p);
            sb.append("\n");
        }
        return sb.toString();
    }
}
