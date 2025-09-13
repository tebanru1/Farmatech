
package Modelo;

import java.sql.Date;

public class Ventas {
    private int id;
    private String Cliente;
    private String Vendedor;
    private float Total;
    private Date fecha;
    public Ventas() {
    }

    public Ventas(int id, String Cliente, String Vendedor, float Total, Date fecha) {
        this.id = id;
        this.Cliente = Cliente;
        this.Vendedor = Vendedor;
        this.Total = Total;
        this.fecha = fecha;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public String getVendedor() {
        return Vendedor;
    }

    public void setVendedor(String Vendedor) {
        this.Vendedor = Vendedor;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }
    
}
