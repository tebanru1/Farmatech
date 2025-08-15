
package Controlador;

import Modelo.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public boolean RegistrarProveedor(Proveedor pro) {
        String sql = "INSERT INTO proveedores(nit, nombre, telefono, correo, direccion) VALUES (?,?,?,?,?)";
        try {
            con = cn.conectar();
            if (con == null) {
                JOptionPane.showMessageDialog(null, "No se pudo establecer conexión con la base de datos.");
                return false;
            }
            ps = con.prepareStatement(sql);
            ps.setInt(1, pro.getNit());
            ps.setString(2, pro.getNombre());
            ps.setLong(3, pro.getTelefono());
            ps.setString(4, pro.getCorreo());
            ps.setString(5, pro.getDireccion());
            ps.execute(); 
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
    public List ListarProveedores(){
        List<Proveedor> ListaPro =new ArrayList();
        String sql="SELECT * FROM proveedores";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                  Proveedor pro=new Proveedor();
                  pro.setId(rs.getInt("id"));
                  pro.setNit(rs.getInt("nit"));
                  pro.setNombre(rs.getString("nombre"));
                  pro.setTelefono(rs.getLong("telefono"));
                  pro.setCorreo(rs.getString("correo"));
                  pro.setDireccion(rs.getString("direccion"));
                  ListaPro.add(pro);
          
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaPro;
    }
    public boolean EliminarProveedor(int id){
        String sql="DELETE FROM proveedores WHERE id=?";
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
                ps.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
    public boolean ActualizarProveedor(Proveedor pro){
        String sql="UPDATE proveedores SET nit=?, nombre=?, telefono=?, correo=?, direccion=? WHERE id=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, pro.getNit());
            ps.setString(2, pro.getNombre());
            ps.setLong(3, pro.getTelefono());
            ps.setString(4, pro.getCorreo());
            ps.setString(5, pro.getDireccion());
            ps.setInt(6, pro.getId());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.out.println(e.toString());
            return false;
        }finally{
            try{
                ps.close();
            }catch(SQLException ex){
                System.out.println(ex.toString());
            }
        }
    }
}
