package Controlador;
import Modelo.detalleVenta;
import Modelo.Ventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VentasDAO {
    conexion cn = new conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;
    public int RegistrarVenta(Ventas v){
       String sql="INSERT INTO ventas (cliente,vendedor,total) VALUES (?,?,?)";
       try{
           con=cn.conectar();
           ps=con.prepareStatement(sql);
           ps.setString(1, v.getCliente());
           ps.setString(2, v.getVendedor());
           ps.setFloat(3, v.getTotal());
           ps.execute();
       }catch(SQLException e){
           System.out.println(e.toString());
       }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
       return r;
    }
    public int RegistrarDetalle(detalleVenta dv){
        String sql="INSERT INTO detalleventa (cod_producto,nombre,cantidad,precio,precioTotal,id_venta) VALUES (?,?,?,?,?,?)";
        try {
           con=cn.conectar();
           ps=con.prepareStatement(sql);
           ps.setInt(1, dv.getCod_producto());
           ps.setString(2, dv.getNombre());
           ps.setInt(3, dv.getCantidad());
           ps.setFloat(4, dv.getPrecio());
           ps.setFloat(5, dv.getPrecioTotal());
           ps.setInt(6, dv.getId_venta());
           ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }
    public int idventa(){
        int id=0;
        String sql="SELECT MAX(id)FROM ventas";
        try {
           con=cn.conectar();
           ps=con.prepareStatement(sql);
           rs=ps.executeQuery();
            if (rs.next()) {
                id=rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }return id;
    }
    public boolean ActualizarStock(int cant,int id ){
        String sql="UPDATE productos SET stock=? WHERE id=?";
        try {
           con=cn.conectar();
           ps=con.prepareStatement(sql);
           ps.setInt(1, cant);
           ps.setInt(2,id);
           ps.execute();
           return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    public List ListarVentas(){
        List<Ventas> ListaVen =new ArrayList();
        String sql="SELECT * FROM ventas";
        try{
            con=cn.conectar();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                  Ventas Vent=new Ventas();
                  Vent.setId(rs.getInt("id"));
                  Vent.setCliente(rs.getString("cliente"));
                  Vent.setVendedor(rs.getString("vendedor"));
                  Vent.setTotal(rs.getFloat("total"));
                  ListaVen.add(Vent);
          
            }
        }catch(SQLException e){
            System.out.println(e.toString());
        }
        return ListaVen;
    }
}
