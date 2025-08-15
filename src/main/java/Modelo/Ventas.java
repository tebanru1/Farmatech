
package Modelo;

public class Ventas {
    private int id;
    private String Cliente;
    private String Vendedor;
    private float Total;

    public Ventas() {
    }

    public Ventas(int id, String Cliente, String Vendedor, float Total) {
        this.id = id;
        this.Cliente = Cliente;
        this.Vendedor = Vendedor;
        this.Total = Total;
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
