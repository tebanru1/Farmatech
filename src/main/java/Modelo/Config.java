package Modelo;
public class Config {
    private int id;
    private int nit;
    private String nombre;
    private int tel;
    private String direccion;
    private String correo;

    public Config() {
    }

    public Config(int id,int nit, String nombre, int tel, String direccion, String correo) {
        this.id=id;
        this.nit = nit;
        this.nombre = nombre;
        this.tel = tel;
        this.direccion = direccion;
        this.correo = correo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
