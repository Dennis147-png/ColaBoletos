package colaboletos;

public class Pasajero {
    private String cedula;
    private String nombre;
    private int cantidad;
    private String ruta;
    private double precio;

    public Pasajero() {

    }

    public Pasajero(String cedula, String nombre, int cantidad, String ruta, double precio) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.ruta = ruta;
        this.precio = precio;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // el total se calcula, asi si el pasajero compra mas boletos se actualiza solo
    public double getPrecioTotal() {
        return cantidad * precio;
    }

    @Override
    public String toString() {
        return "Ruta: " + ruta + " | Boletos: " + cantidad + " | Pasajero: " + nombre
                + " (CI " + cedula + ") | Total: $" + String.format("%.2f", getPrecioTotal());
    }
}
