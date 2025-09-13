package Controlador;
import Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarCliente(Cliente cl) {
        String sql = "INSERT INTO clientes(cedula, nombre, telefono, correo, direccion) VALUES (?,?,?,?,?)";
        try {
            con = cn.conectar();
            if (con == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.");
                return false;
            }
            ps = con.prepareStatement(sql);
            ps.setInt(1, cl.getCedula());
            ps.setString(2, cl.getNombre());
            ps.setLong(3, cl.getTelefono());
            ps.setString(4, cl.getCorreo());
            ps.setString(5, cl.getDireccion());
            ps.executeUpdate(); 
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close(); 
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    public List ListarCliente(){
        List<Cliente> ListaCl =new ArrayList();
        String sql="SELECT * FROM clientes";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                  Cliente cl=new Cliente();
                  cl.setId(rs.getInt("id"));
                  cl.setCedula(rs.getInt("cedula"));
                  cl.setNombre(rs.getString("nombre"));
                  cl.setTelefono(rs.getLong("telefono"));
                  cl.setCorreo(rs.getString("correo"));
                  cl.setDireccion(rs.getString("direccion"));
                  ListaCl.add(cl);
          
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaCl;
    }
    public boolean EliminarCliente(int id){
        String sql="DELETE FROM clientes WHERE id=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch (SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
    public boolean ModificarCliente(Cliente cl){
       String sql="UPDATE clientes SET cedula=?,nombre=?,telefono=?,correo=?,direccion=? WHERE id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setInt(1, cl.getCedula());
            ps.setString(2, cl.getNombre());
            ps.setLong(3, cl.getTelefono());
            ps.setString(4, cl.getCorreo());
            ps.setString(5, cl.getDireccion());
            ps.setInt(6, cl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.print(e.toString());
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
    public Cliente BuscarCliente(int cedula){
        Cliente cliente=new Cliente();
        String sql="SELECT * FROM clientes WHERE cedula=?";
        try {
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            ps.setInt(1, cedula);
            rs=ps.executeQuery();
            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setTelefono(rs.getLong("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setDireccion(rs.getString("direccion"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }return cliente;
   }
 public List<Cliente> buscarPorNombre(String nombre) {
    List<Cliente> lista = new ArrayList<>();
    String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";

    try  {
        con=cn.conectar();
        ps=con.prepareStatement(sql);
        ps.setString(1, nombre + "%"); 
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Cliente clien = new Cliente();
            clien.setCedula(rs.getInt("cedula"));
            clien.setNombre(rs.getString("nombre"));
            clien.setTelefono(rs.getLong("telefono"));
            clien.setCorreo(rs.getString("correo"));
            clien.setDireccion(rs.getString("direccion"));
            lista.add(clien);
        }
    } catch (SQLException e) {
        System.out.println(e.toString());
    }
    return lista;
}
}

