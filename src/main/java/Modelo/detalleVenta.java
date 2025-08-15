package Modelo;
public class detalleVenta {
    private int id;
    private int id_venta;
    private int cod_producto;
    private String nombre;
    private int cantidad;
    private float precio;
    private float precioTotal;

    public detalleVenta() {
    }

    public detalleVenta(int id, int id_venta, int cod_producto, String nombre, int cantidad, float precio, float precioTotal) {
        this.id = id;
        this.id_venta = id_venta;
        this.cod_producto = cod_producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.precioTotal = precioTotal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(int cod_producto) {
        this.cod_producto = cod_producto;
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

   
}
